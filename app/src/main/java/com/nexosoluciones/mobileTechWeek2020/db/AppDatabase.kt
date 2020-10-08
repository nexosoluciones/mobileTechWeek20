package com.nexosoluciones.mobileTechWeek2020.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexosoluciones.mobileTechWeek2020.db.entity.CourseDAO
import com.nexosoluciones.mobileTechWeek2020.db.entity.EventDAO
import com.nexosoluciones.mobileTechWeek2020.models.Course
import com.nexosoluciones.mobileTechWeek2020.models.Event

@Database(entities = [Course::class, Event::class], version =  1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCourseDAO(): CourseDAO
    abstract fun getEventDAO(): EventDAO

    companion object {
        private const val DB_NAME = "Bootcamp"

        @Volatile private var instance: AppDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, DB_NAME)
            .build()
    }
}