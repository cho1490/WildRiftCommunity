package com.example.wildriftcommunity.util

import android.annotation.SuppressLint
import java.util.*

@SuppressLint("SimpleDateFormat")
fun timeConverter(time:  String): String {
    val calendar: Calendar = Calendar.getInstance()

    val now: Long = calendar.timeInMillis
    var gap = now - time.toLong()

    //        초       분   시
    //        1000     60   60
    gap = (gap / 1000)
    val hour = gap / 3600
    gap %= 3600
    val min = gap / 60
    val sec = gap % 60

    when {
        hour > 720 -> {
            return (hour / 720).toString() + "달 전"
        }
        hour > 24 -> {
            return  (hour / 24).toString() + "일 전"
        }
        hour > 0 -> {
            return hour.toString() + "시간 전"
        }
        min > 0 -> {
            return min.toString() + "분 전"
        }
        sec > 0 -> {
            return sec.toString() + "초 전"
        }
        sec > -1 -> {
            return "방금 전"
        }
        else -> {
            return "오래 전"
        }
    }
}