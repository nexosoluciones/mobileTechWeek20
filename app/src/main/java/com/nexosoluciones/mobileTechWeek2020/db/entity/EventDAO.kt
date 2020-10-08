package com.nexosoluciones.mobileTechWeek2020.db.entity

import androidx.room.*
import com.nexosoluciones.mobileTechWeek2020.models.Event

@Dao
interface EventDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Delete
    fun delete(event: Event)

    @Query("DELETE FROM Event")
    fun deleteAll()

    @Query("SELECT * FROM Event")
    fun getAll(): List<Event>?
}