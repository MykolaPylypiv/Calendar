package com.example.calendar.domain

import android.annotation.SuppressLint
import com.example.calendar.domain.model.Month
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class Calendar @Inject constructor() {
    val daysWeek = listOf("Mn", "Ts", "Wd", "Th", "Fr", "St", "Sn")

    val listOfMonth = listOf(
        Month(name = "January", days = 31),
        Month(name = "February", days = 28),
        Month(name = "March", days = 31),
        Month(name = "April", days = 30),
        Month(name = "May", days = 31),
        Month(name = "June", days = 30),
        Month(name = "July", days = 31),
        Month(name = "August", days = 31),
        Month(name = "September", days = 30),
        Month(name = "October", days = 31),
        Month(name = "November", days = 30),
        Month(name = "December", days = 31)
    )

    @SuppressLint("SimpleDateFormat")
    val nowDate = SimpleDateFormat("u/dd/M/yyyy").format(Date()).split("/")

    val dayOfWeek = nowDate[0]
    val day = nowDate[1]
    val monthNumber = nowDate[2].toInt()
    val year = nowDate[3]
}