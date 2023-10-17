package com.example.calendar.ui.screen.start

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calendar.domain.Calendar
import com.example.calendar.domain.model.Month
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(val calendar: Calendar) : ViewModel() {
    private var monthIndex = calendar.monthNumber - 1

    val year = mutableStateOf(calendar.year)

    val month = mutableStateOf(selectMonth(year = year.value, index = monthIndex))
    val nameMonth = mutableStateOf(month.value.name)

    val pointer = mutableStateOf(calendar.day.toInt() % 7 - calendar.dayOfWeek.toInt())
    val pointerNextMonth = mutableStateOf(0)

    var preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
    var preMonthStartDays = preMonthDays - (6 - pointer.value)

    fun next() {
        if (monthIndex == 11) {
            year.value = (year.value.toInt() + 1).toString()
            changeMonth(-11)
            preMonthDays = selectMonth(year = year.value, index = 11).days
        } else {
            changeMonth(1)
            preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
        }

        pointer.value = pointerNextMonth.value
        preMonthStartDays = preMonthDays - (6 - pointer.value)
    }

    fun back() {
        if (monthIndex == 0) {
            year.value = (year.value.toInt() - 1).toString()
            changeMonth(11)
            preMonthDays = selectMonth(year = year.value, index = 0).days
        } else {
            preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
            changeMonth(-1)
        }

        val term = if (pointer.value + month.value.days > 35) 42 else 35
        pointer.value = - (term - pointer.value - month.value.days - 7)
        preMonthStartDays = preMonthDays - 6 + pointer.value
    }

    fun isToday(count: Int, day: String): Boolean {
        return (calendar.day.toInt() == count + calendar.daysWeek.indexOf(day) &&
                monthIndex == calendar.monthNumber - 1 &&
                calendar.year == year.value)
    }

    private fun changeMonth(index: Int) {
        monthIndex += index
        month.value = selectMonth(year = year.value, index = monthIndex)
        nameMonth.value = month.value.name
    }

    private fun selectMonth(year: String, index: Int): Month {
        return if (year.toInt() % 4 == 0 && index == 1) {
            Month(name = "February", days = 29)
        } else calendar.listOfMonth[index]
    }
}