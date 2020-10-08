package com.nexosoluciones.mobileTechWeek2020.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Event : Serializable {

    @PrimaryKey
    var id : Long = 0

    var name: String? = null
    var description: String? = null
    var started: Long = 0
    var ended: Long = 0
    var place: String? = null
}