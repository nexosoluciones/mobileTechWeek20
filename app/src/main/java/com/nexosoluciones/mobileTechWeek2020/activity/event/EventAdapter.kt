package com.nexosoluciones.mobileTechWeek2020.activity.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nexosoluciones.mobileTechWeek2020.R
import com.nexosoluciones.mobileTechWeek2020.models.Event
import com.nexosoluciones.mobileTechWeek2020.utils.DateTools
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter (private val listEvent : ArrayList<Event>): RecyclerView.Adapter<EventAdapter.ViewHolder>(){

    var onItemClick: ((Event) -> Unit)? = null

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = listEvent[position]

        holder.tvName.text = event.name
        holder.tvDate.text = "Desde el ${DateTools.shortDate(event.started)} al ${DateTools.shortDate(event.ended)}"
        holder.tvPlace.text = event.place

        holder.llContainer.setOnClickListener {
            onItemClick?.invoke(event)
        }
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val llContainer : LinearLayout = view.llContainer
        val tvName : TextView = view.tvName
        val tvDate : TextView = view.tvDate
        val tvPlace : TextView = view.tvPlace
    }
}