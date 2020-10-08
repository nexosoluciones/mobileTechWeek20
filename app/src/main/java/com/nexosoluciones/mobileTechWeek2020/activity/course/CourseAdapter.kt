package com.nexosoluciones.mobileTechWeek2020.activity.course

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nexosoluciones.mobileTechWeek2020.R
import com.nexosoluciones.mobileTechWeek2020.models.Course
import com.nexosoluciones.mobileTechWeek2020.utils.DateTools
import kotlinx.android.synthetic.main.item_course.view.*

class CourseAdapter (
    private val listCourse : ArrayList<Course>
): RecyclerView.Adapter<CourseAdapter.ViewHolder>(){

    var onItemClick: ((Course) -> Unit)? = null

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_course, parent, false))
    }

    override fun getItemCount(): Int {
        return listCourse.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = listCourse[position]

        holder.tvName.text = course.name
        holder.tvDate.text = "${DateTools.shortDate(course.date)}"
        holder.tvDuration.text = "${course.duration} Min"
        holder.tvSpeaker.text = course.speaker
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val tvName : TextView = view.tvName
        val tvDate : TextView = view.tvDate
        val tvDuration : TextView = view.tvDuration
        val tvSpeaker : TextView = view.tvSpeaker
    }
}