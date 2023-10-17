package com.example.calendar.ui.screen.start

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.calendar.app.date
import com.example.calendar.domain.Calendar
import com.example.calendar.domain.model.Month
import com.example.calendar.navigation.NavigationTree
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(val calendar: Calendar) : ViewModel() {
    var monthIndex = calendar.monthNumber - 1

    val year = mutableStateOf(calendar.year)

    val month = mutableStateOf(selectMonth(year = year.value, index = monthIndex))
    val nameMonth = mutableStateOf(month.value.name)

    val pointer = mutableIntStateOf(calendar.day.toInt() % 7 - calendar.dayOfWeek.toInt())
    val pointerNextMonth = mutableIntStateOf(0)

    var preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
    var preMonthStartDays = preMonthDays - (6 - pointer.intValue)

    fun next() {
        if (monthIndex == 11) {
            year.value = (year.value.toInt() + 1).toString()
            changeMonth(-11)
            preMonthDays = selectMonth(year = year.value, index = 11).days
        } else {
            changeMonth(1)
            preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
        }

        pointer.intValue = pointerNextMonth.intValue
        preMonthStartDays = preMonthDays - (6 - pointer.intValue)
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

        val term = if (pointer.intValue + month.value.days > 35) 42 else 35
        pointer.intValue = - (term - pointer.intValue - month.value.days - 7)
        preMonthStartDays = preMonthDays - 6 + pointer.intValue
    }

    var newCount = 0

    fun textTable(count: Int, day:String): TextButtonParams {
        val days = month.value.days
        val dayNumber = calendar.daysWeek.indexOf(day)

        var text = ""
        var color = Color.White

        if (count in 1..days) { // 0 < count < days

            if (count + dayNumber <= days) {
                text = (count + dayNumber).toString()
                color = Color.White
                newCount += 1
            } else {
                text = (dayNumber - newCount + 1).toString()
                color = Color.LightGray
            }

            pointerNextMonth.intValue = 7 - newCount
        } else if (count <= 0 && count != -6) { // count < 0

            if (preMonthStartDays + dayNumber <= preMonthDays) {
                text = (preMonthStartDays + dayNumber).toString()
                color = Color.LightGray
            } else {
                text = (count + 7 - (7 - dayNumber)).toString()
                color = Color.White
            }
        }

        return TextButtonParams(text = text, color = color)
    }

    fun dateClick(color: Color, text: String, navController: NavController) {
        if (color == Color.White) {
            navController.navigate(NavigationTree.Tasks.name)
            date.value =
                "${text}.${monthIndex + 1}.${year.value}"
        }
    }

    fun isEmptyFirstRow(count: Int) = !(count in 1..month.value.days || count <= 0 && count != -6)

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

data class TextButtonParams(
    val text: String,
    val color: Color
)