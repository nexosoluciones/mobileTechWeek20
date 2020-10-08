package com.nexosoluciones.mobileTechWeek2020.ws.api

import android.content.Context
import com.nexosoluciones.mobileTechWeek2020.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient constructor(context: Context){

    private var retrofitClient: Retrofit? = null

    init {
        retrofitClient = initConnection(context)
    }

    // Initializing Retrofit
    private fun initConnection(context: Context) : Retrofit{

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).build()

        // Creating the instance of a Builder
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.URL_BASE))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiService = retrofitClient!!.create(ApiService::class.java)
}