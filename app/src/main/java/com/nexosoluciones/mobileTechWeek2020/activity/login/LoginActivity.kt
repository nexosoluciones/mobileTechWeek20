package com.nexosoluciones.mobileTechWeek2020.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nexosoluciones.mobileTechWeek2020.R
import com.nexosoluciones.mobileTechWeek2020.activity.event.EventActivity
import com.nexosoluciones.mobileTechWeek2020.activity.register.RegisterActivity
import com.nexosoluciones.mobileTechWeek2020.databinding.ActivityLoginBinding
import com.nexosoluciones.mobileTechWeek2020.support.AppPreferences
import com.nexosoluciones.mobileTechWeek2020.utils.Constants
import com.nexosoluciones.mobileTechWeek2020.utils.Style
import com.nexosoluciones.mobileTechWeek2020.ws.api.RetrofitClient
import com.nexosoluciones.mobileTechWeek2020.ws.reqres.ResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Style.getStyleTheme(this))

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {
            this@LoginActivity.startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setDistanceToTriggerSync(Constants.DISTANCE_TO_TRIGGER_SYNC)
        binding.swipeRefresh.isEnabled = false

        binding.tvTitle.setOnClickListener {
            binding.etEmail.setText("jack@correo.com")
            binding.etPassword.setText("123456")
        }

        binding.btnLogin.setOnClickListener(View.OnClickListener {

            binding.swipeRefresh.isRefreshing = true

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty()) {
                binding.tiEmail.error = "Correo electrónico requerido"
                binding.etEmail.requestFocus()
                return@OnClickListener
            }
            if (password.isEmpty()) {
                binding.tiPassword.error = "Contraseña requerido"
                binding.etPassword.requestFocus()
                return@OnClickListener
            }

            binding.btnLogin!!.isEnabled = false
            login(email, password)
        })
    }

    private fun goToEvent(email : String, password: String, token : String){
        val appPreferences = AppPreferences(this@LoginActivity)
        appPreferences.setUserEmail(email)
        appPreferences.setPassword(password)
        appPreferences.setToken(token)

        val intent = Intent(this@LoginActivity, EventActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
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
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                            binding.btnLogin.isEnabled = true

                        } else {
                            goToEvent(email, password, body.token!!)
                        }

                    }?: run {
                        val message: String? = response.errorBody().toString()
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                        binding.btnLogin.isEnabled = true
                    }
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                    binding.swipeRefresh.isRefreshing = false
                    binding.btnLogin.isEnabled = true
                }
            })
    }
}