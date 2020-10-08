package com.nexosoluciones.mobileTechWeek2020.ws.reqres

import com.google.gson.annotations.SerializedName
import java.util.*

class ResponseDTO {
    @SerializedName("error")
    var isError : Boolean = false
    @SerializedName("mensaje")
    var message : String? = null
    @SerializedName("userid")
    var userId : String? = null
    @SerializedName("token")
    var token : String? = null

    @SerializedName("eventos")
    var listEvent : ArrayList<EventDTO>? = null

    @SerializedName("cursos")
    var listCourse : ArrayList<CourseDTO>? = null
}