package com.nexosoluciones.mobileTechWeek2020.ws.api

import com.nexosoluciones.mobileTechWeek2020.utils.Constants
import com.nexosoluciones.mobileTechWeek2020.ws.reqres.ResponseDTO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST(Constants.URL_LOGIN)
    fun login(
        @Field("email") email: String?,
        @Field("contrasenia") password: String?
    ) : Call<ResponseDTO>

    @FormUrlEncoded
    @POST(Constants.URL_REGISTER)
    fun register(
        @Field("nombre") name: String?,
        @Field("apellido") surname: String?,
        @Field("email") email: String?,
        @Field("contrasenia") password: String?
    ): Call<ResponseDTO>


    @GET(Constants.URL_EVENT)
    fun getEvent(): Call<ResponseDTO>

    @GET(Constants.URL_COURSE)
    fun getCourse(): Call<ResponseDTO>
}