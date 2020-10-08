package com.nexosoluciones.mobileTechWeek2020.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Course {

    @PrimaryKey
    var id : Long = 0

    var name: String? = null
    var date : Long = 0
    var description: String? = null
    var duration: Int = 0
    var speaker: String? = null
    var eventId: Long = 0
}