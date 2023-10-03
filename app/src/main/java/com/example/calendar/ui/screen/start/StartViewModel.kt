package com.example.calendar.ui.screen.start

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor() : ViewModel() {
    val calendar = Calendar()

    var monthIndex = calendar.monthNumber - 1

    val year = mutableStateOf(calendar.year)

    val month = mutableStateOf(selectMonth(year = year.value, index = monthIndex))
    val nameMonth = mutableStateOf(month.value.name)

    val pointer = mutableStateOf(calendar.day.toInt() % 7 - calendar.dayOfWeek.toInt())
    val pointerNextMonth = mutableStateOf(0)

    var preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
    var preMonthStartDays = preMonthDays - (6 - pointer.value)

    fun next() {
        pointer.value = pointerNextMonth.value

        if (monthIndex == 11) {
            year.value = (year.value.toInt() + 1).toString()
            changeMonth(-11)
        } else {
            changeMonth(1)
        }
    }

    fun back() {
        if (monthIndex == 0) {
            changeMonth(11)
        } else {
            changeMonth(-1)
        }
    }

    private fun changeMonth(index: Int) {
        monthIndex += index
        month.value = selectMonth(year = year.value, index = monthIndex)
        nameMonth.value = month.value.name
        if (index == -11) {
            preMonthDays = selectMonth(year = year.value, index = 11).days
        } else if (index == 11) {
            preMonthDays = selectMonth(year = year.value, index = 0).days
        } else {
            preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
        }

        preMonthStartDays = preMonthDays - (6 - pointer.value)
    }
    //monthIndex
    //pointer
    //month
    //nameMonth
    //preMonthDays
    //preMonthStartDays
    //monthDays

    private fun selectMonth(year: String, index: Int): Month {
        return if (year.toInt() % 4 == 0 && index == 1) {
            Month(name = "February", days = 29)
        } else calendar.listOfMonth[index]
    }
}

data class Month(val name: String, val days: Int)

class Calendar {
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