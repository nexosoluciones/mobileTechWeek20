package com.nexosoluciones.mobileTechWeek2020.db.entity

import androidx.room.*
import com.nexosoluciones.mobileTechWeek2020.models.Course

@Dao
interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(course: Course)

    @Update
    fun update(course: Course)

    @Delete
    fun delete(course: Course)

    @Query("DELETE FROM Course")
    fun deleteAll()

    @Query("SELECT * FROM Course")
    fun getAll(): List<Course>?

    @Query("SELECT * FROM Course WHERE eventId == :eventId")
    fun getCourseByEventId(eventId : Long): List<Course>?
}