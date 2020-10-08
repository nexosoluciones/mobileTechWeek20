package com.nexosoluciones.mobileTechWeek2020.activity.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nexosoluciones.mobileTechWeek2020.R
import com.nexosoluciones.mobileTechWeek2020.activity.event.EventActivity
import com.nexosoluciones.mobileTechWeek2020.databinding.ActivityRegisterBinding
import com.nexosoluciones.mobileTechWeek2020.support.AppPreferences
import com.nexosoluciones.mobileTechWeek2020.utils.Constants
import com.nexosoluciones.mobileTechWeek2020.utils.Style
import com.nexosoluciones.mobileTechWeek2020.ws.api.RetrofitClient
import com.nexosoluciones.mobileTechWeek2020.ws.reqres.ResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Style.getStyleTheme(this))

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setDistanceToTriggerSync(Constants.DISTANCE_TO_TRIGGER_SYNC)
        binding.swipeRefresh.isEnabled = false

        binding.btnRegister.setOnClickListener {
            binding.swipeRefresh.isRefreshing = true

            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            if (validInput(name, surname, email, password, repeatPassword)) {
                binding.btnRegister.isEnabled = false
                register(name, surname, email, password)
            }
        }
    }

    private fun register(
        name : String,
        surname: String,
        email: String,
        password: String
    ) {
        RetrofitClient(context = this).getApiService()
            .register(name = name, surname = surname, password = password, email = email)
            .enqueue(object : Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    response.body()?.let { body ->
                        if (body.isError) {
                            binding.swipeRefresh.isRefreshing = false
                            val message: String? = response.body()!!.message
                            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                            binding.btnRegister.isEnabled = true

                        } else {
                            login(email, password)
                        }

                    }?: run {
                        binding.swipeRefresh.isRefreshing = false
                        val message: String? = response.errorBody().toString()
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                        binding.btnRegister.isEnabled = true
                    }
                }
                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                    binding.swipeRefresh.isRefreshing = false
                    binding.btnRegister.isEnabled = true
                }
            })
    }

    private fun login(email: String, password: String) {
        RetrofitClient(context = this).getApiService()
            .login(email, password)
            .enqueue(object : Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    binding.swipeRefresh.isRefreshing = false
                    response.body()?.let { body ->
                        if (body.isError) {

                            val message: String? = response.body()!!.message
                            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                            binding.btnRegister.isEnabled = true

                        } else {

                            val appPreferences = AppPreferences(this@RegisterActivity)
                            appPreferences.setUserEmail(email)
                            appPreferences.setPassword(password)
                            appPreferences.setToken(body.token)

                            val intent = Intent(this@RegisterActivity, EventActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                    }?: run {
                        val message: String? = response.errorBody().toString()
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                        binding.btnRegister.isEnabled = true
                    }
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                    binding.swipeRefresh.isRefreshing = false
                    binding.btnRegister.isEnabled = true
                }
            })
    }

    private fun validInput(
        name: String,
        surname: String,
        email: String,
        password: String,
        repeatPassword: String
    ): Boolean {
        if (name.isEmpty()) {
            binding.tiName.error = "Nombre requerido"
            binding.etName.requestFocus()
            return false
        }
        if (surname.isEmpty()) {
            binding.tiSurname.error = "Apellido requerido"
            binding.etSurname.requestFocus()
            return false
        }
        if (email.isEmpty()) {
            binding.tiEmail.error = "Correo electrónico requerido"
            binding.etEmail.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            binding.tiPassword.error = "Contraseña requerido"
            binding.etPassword.requestFocus()
            return false
        }
        if (repeatPassword.isEmpty()) {
            binding.tiRepeatPassword.error = "Repita su contraseña"
            binding.etRepeatPassword.requestFocus()
            return false
        }
        if (repeatPassword != password) {
            binding.tiRepeatPassword.error = "Las contraseñas no coinciden"
            binding.etRepeatPassword.requestFocus()
            return false
        }
        return true
    }
}
