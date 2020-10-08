package com.nexosoluciones.mobileTechWeek2020.ws.reqres

import com.nexosoluciones.mobileTechWeek2020.models.Event
import com.nexosoluciones.mobileTechWeek2020.utils.DateTools
import com.google.gson.annotations.SerializedName

data class EventDTO (
    @SerializedName("id") var id: Long = 0,
    @SerializedName("nombre") var name: String? = null,
    @SerializedName("descripcion") var description: String? = null,
    @SerializedName("fecha_inicio") var started: String? = null,
    @SerializedName("fecha_fin") var ended: String? = null,
    @SerializedName("lugar") var place: String? = null
){

    val eventModel : Event
        get() {
            val event = Event()
            event.id = id
            event.description = description
            event.place = place
            event.name = name
            event.started = started?.let { DateTools.eventDateToDate(it)?.time } ?: run { 0L }
            event.ended = ended?.let { DateTools.eventDateToDate(it)?.time } ?: run { 0L }
            return event
        }
}
