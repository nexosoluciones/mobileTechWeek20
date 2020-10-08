package com.nexosoluciones.mobileTechWeek2020.support

import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.BuildConfig

class AppPreferences constructor( private val context: Context){

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    private var editor: SharedPreferences.Editor? = null

    init {
        editor = sharedPreferences.edit()
    }

    companion object {
        private const val KEY_EMAIL = "user_email"
        private const val KEY_PASSWORD = "user_password"
        private const val KEY_TOKEN = "token"
    }

    fun getToken(): String? {
        return get(KEY_TOKEN)
    }

    fun setToken(token: String?) {
        set(KEY_TOKEN, token)
    }

    fun getUserEmail(): String? {
        return get(KEY_EMAIL)
    }

    fun setUserEmail(email: String?) {
        set(KEY_EMAIL, email)
    }

    fun getUserPassword(): String? {
        return get(KEY_PASSWORD)
    }

    fun setPassword(password: String?) {
        set(KEY_PASSWORD, password)
    }


    private fun save() {
        try {
            editor!!.commit()
        } catch (ex: NullPointerException) {
        }
    }

    private fun get(key: String?): String? {
        try {
            if (sharedPreferences!!.contains(key)) {
                return sharedPreferences!!.getString(key, null)
            }
        } catch (ex: NullPointerException) {
        }
        return null
    }

    private fun set(key: String?, value: String?) {
        try {
            editor!!.putString(key, value)
            save()
        } catch (ex: NullPointerException) {
        }
    }

    fun remove(key: String?): String? {
        var value: String? = null
        try {
            if (sharedPreferences!!.contains(key)) {
                value = sharedPreferences!!.getString(key, null)
                editor!!.remove(key)
                save()
            }
        } catch (ex: NullPointerException) {
        }
        return value
    }

    fun clearAll() {
        editor!!.clear()
        editor!!.commit()
    }
}