package com.nexosoluciones.mobileTechWeek2020.activity.event

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexosoluciones.mobileTechWeek2020.R
import com.nexosoluciones.mobileTechWeek2020.activity.course.CourseActivity
import com.nexosoluciones.mobileTechWeek2020.databinding.ActivityEventBinding
import com.nexosoluciones.mobileTechWeek2020.db.AppDatabase
import com.nexosoluciones.mobileTechWeek2020.models.Event
import com.nexosoluciones.mobileTechWeek2020.utils.Constants
import com.nexosoluciones.mobileTechWeek2020.utils.Style
import com.nexosoluciones.mobileTechWeek2020.ws.api.RetrofitClient
import com.nexosoluciones.mobileTechWeek2020.ws.reqres.CourseDTO
import com.nexosoluciones.mobileTechWeek2020.ws.reqres.EventDTO
import com.nexosoluciones.mobileTechWeek2020.ws.reqres.ResponseDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class EventActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Style.getStyleTheme(this))

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setDistanceToTriggerSync(Constants.DISTANCE_TO_TRIGGER_SYNC)
        binding.swipeRefresh.isEnabled = false

        getEvent()
    }

    private fun setupView(listEvent : ArrayList<Event>){
        val eventAdapter = EventAdapter(listEvent)
        eventAdapter.onItemClick = {event ->  goToCourseActivity(event) }

        val layoutManager = LinearLayoutManager(this@EventActivity, LinearLayoutManager.VERTICAL, false)

        binding.rvEvent.addItemDecoration(DividerItemDecoration(this@EventActivity, LinearLayoutManager.VERTICAL))
        binding.rvEvent.layoutManager = layoutManager
        binding.rvEvent.adapter = eventAdapter
    }

    private fun goToCourseActivity(event: Event){
        val intent = Intent(this@EventActivity, CourseActivity::class.java)
        intent.putExtra(Constants.KEY_EVENT,event)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun saveEvent(listEventDTO : ArrayList<EventDTO>){
        val dataBase = AppDatabase.invoke(this@EventActivity)
        val listEvent : ArrayList<Event> = ArrayList()

        listEventDTO.forEach { eventDTO ->
            val eventModel = eventDTO.eventModel

            listEvent.add(eventModel)
            GlobalScope.async {
                dataBase.getEventDAO().insert(event = eventModel)
            }
        }

        setupView(listEvent)
    }

    private fun saveCourse(listCourseDTO : ArrayList<CourseDTO>){
        val dataBase = AppDatabase.invoke(this@EventActivity)

        listCourseDTO.forEach { courseDTO ->
            val courseModel = courseDTO.getModel

            GlobalScope.async {
                dataBase.getCourseDAO().insert(course = courseModel)
            }

        }
    }

    private fun getEvent() {
        RetrofitClient(context = this).getApiService()
            .getEvent()
            .enqueue(object : Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    response.body()?.let { body ->
                        if (body.isError) {
                            binding.swipeRefresh.isRefreshing = false
                            val message: String? = response.body()!!.message
                            Toast.makeText(this@EventActivity, message, Toast.LENGTH_LONG).show()
                        } else {
                            body.listEvent?.let {
                                saveEvent(it)
                                getCourse()
                            }
                        }
                    }?: run {
                        binding.swipeRefresh.isRefreshing = false
                        val message: String? = response.errorBody().toString()
                        Toast.makeText(this@EventActivity, message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                    binding.swipeRefresh.isRefreshing = false
                }
            })
    }

    private fun getCourse() {
        RetrofitClient(context = this).getApiService()
            .getCourse()
            .enqueue(object : Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    response.body()?.let { body ->
                        if (body.isError) {
                            binding.swipeRefresh.isRefreshing = false
                            val message: String? = response.body()!!.message
                            Toast.makeText(this@EventActivity, message, Toast.LENGTH_LONG).show()
                        } else {
                            body.listCourse?.let {
                                saveCourse(it)
                            }
                        }
                    }?: run {
                        binding.swipeRefresh.isRefreshing = false
                        val message: String? = response.errorBody().toString()
                        Toast.makeText(this@EventActivity, message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                    binding.swipeRefresh.isRefreshing = false
                }
            })
    }
}
