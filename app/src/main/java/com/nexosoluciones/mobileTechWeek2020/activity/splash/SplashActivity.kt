package com.nexosoluciones.mobileTechWeek2020.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nexosoluciones.mobileTechWeek2020.activity.event.EventActivity
import com.nexosoluciones.mobileTechWeek2020.activity.login.LoginActivity
import com.nexosoluciones.mobileTechWeek2020.databinding.ActivityLoginBinding
import com.nexosoluciones.mobileTechWeek2020.databinding.ActivitySplashBinding
import com.nexosoluciones.mobileTechWeek2020.support.AppPreferences
import com.nexosoluciones.mobileTechWeek2020.utils.Style
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Style.getStyleTheme(this))

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appPreferences = AppPreferences(this)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                appPreferences.getToken()?.let {
                    val intent = Intent(this@SplashActivity, EventActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                }?:run{
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                }
            }
        }, 4000)
    }
}
