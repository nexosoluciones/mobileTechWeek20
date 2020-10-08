package com.nexosoluciones.mobileTechWeek2020.utils

import android.content.Context
import android.content.res.Configuration
import com.nexosoluciones.mobileTechWeek2020.R

class Style {
    companion object{

        fun getStyleTheme(context: Context) : Int{
            return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    R.style.LightTheme
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    R.style.DarkTheme
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED ->{
                    R.style.LightTheme
                }
                else ->{
                    R.style.LightTheme
                }
            }
        }
    }
}