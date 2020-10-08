package com.nexosoluciones.mobileTechWeek2020.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateTools {

    companion object{

        private const val formatter1 = "dd ' de ' MMMM"

        private const val formatter2 = "dd ' de ' MMMM 'de' yyyy"

        private const val formatter3 = "dd/MM/yyyy"

        private const val formatter4 = "yyyy-MM-dd"

        private const val formatter5 = "yyyy-MM-dd HH:mm"

        fun getToday() : Date {
            return Calendar.getInstance().time
        }

        @SuppressLint("SimpleDateFormat")
        fun shortDate(date: Date): String {
            val simpleDateFormat = SimpleDateFormat(formatter1)
            return simpleDateFormat.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun shortDate(date: Long): String {
            val simpleDateFormat = SimpleDateFormat(formatter1)
            return simpleDateFormat.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun getDateFormatter5(date: Long): String {
            val simpleDateFormat = SimpleDateFormat(formatter5)
            return simpleDateFormat.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun courseDateToDate(date: String?): Date? {
            return if(!date.isNullOrEmpty()) {
                SimpleDateFormat(formatter5).parse(date)!!
            }else{
                return null
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun eventDateToDate(date: String?): Date? {
            return if(!date.isNullOrEmpty()) {
                SimpleDateFormat(formatter4).parse(date)!!
            }else{
                return null
            }
        }

    }

}