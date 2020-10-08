package com.nexosoluciones.mobileTechWeek2020.activity.course

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexosoluciones.mobileTechWeek2020.R
import com.nexosoluciones.mobileTechWeek2020.databinding.ActivityCourseBinding
import com.nexosoluciones.mobileTechWeek2020.db.AppDatabase
import com.nexosoluciones.mobileTechWeek2020.models.Course
import com.nexosoluciones.mobileTechWeek2020.models.Event
import com.nexosoluciones.mobileTechWeek2020.utils.Constants
import com.nexosoluciones.mobileTechWeek2020.utils.Style

class CourseActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCourseBinding

    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Style.getStyleTheme(this))

        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setDistanceToTriggerSync(Constants.DISTANCE_TO_TRIGGER_SYNC)
        binding.swipeRefresh.isEnabled = false

        event = intent.getSerializableExtra(Constants.KEY_EVENT) as Event

        val dataBase = AppDatabase.invoke(this@CourseActivity)

        AsyncTask.execute { kotlin.run {
            val listCourse = dataBase.getCourseDAO().getCourseByEventId(eventId = event.id) as ArrayList<Course>?
            listCourse?.let {
                setupView(listCourse = it)
            }
        } }
    }

    private fun setupView(listCourse : ArrayList<Course>){
        val courseAdapter = CourseAdapter(listCourse)

        val layoutManager = LinearLayoutManager(this@CourseActivity, LinearLayoutManager.VERTICAL, false)

        binding.rvCourse.addItemDecoration(DividerItemDecoration(this@CourseActivity, LinearLayoutManager.VERTICAL))
        binding.rvCourse.layoutManager = layoutManager
        binding.rvCourse.adapter = courseAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //goToEventActivity()
    }

//    private fun goToEventActivity(){
//        val intent = Intent(this@CourseActivity, EventActivity::class.java)
//        intent.putExtra(Constants.KEY_EVENT,event)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//    }
}