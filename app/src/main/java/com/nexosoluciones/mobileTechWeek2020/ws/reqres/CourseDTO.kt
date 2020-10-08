package com.nexosoluciones.mobileTechWeek2020.ws.reqres

import com.nexosoluciones.mobileTechWeek2020.models.Course
import com.nexosoluciones.mobileTechWeek2020.utils.DateTools
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseDTO(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("nombre") val name: String? = null,
    @SerializedName("dia_hora") val date: String? = null,
    @SerializedName("descripcion") val description: String? = null,
    @SerializedName("duracion") val duration: Int = 0,
    @SerializedName("disertante") val speaker: String? = null,
    @SerializedName("evento_id")  val eventId: Long = 0
) : Serializable {

    val getModel: Course
        get() {
            val course = Course()
            course.id = id
            course.name = name
            course.description = description
            course.date = date?.let { DateTools.courseDateToDate(it)?.time } ?: run { 0L }
            course.duration = duration
            course.speaker = speaker
            course.eventId = eventId
            return course
        }
}
