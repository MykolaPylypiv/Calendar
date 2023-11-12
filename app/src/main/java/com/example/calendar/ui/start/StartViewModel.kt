package com.example.calendar.ui.start

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.calendar.data.repository.Repository
import com.example.calendar.data.repository.TaskRepository
import com.example.calendar.domain.Calendar
import com.example.calendar.domain.model.Month
import com.example.calendar.domain.model.TextButtonParams
import com.example.calendar.navigation.NavigationTree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    val calendar: Calendar,
    private val constants: Repository
) : ViewModel() {
    val year = mutableStateOf(calendar.year)
    val month = mutableStateOf(selectMonth(year = year.value, index = calendar.monthNumber - 1))

    val nameMonth = mutableStateOf(month.value.name)

    val pointer = mutableIntStateOf(7 + calendar.day.toInt() % 7 - calendar.dayOfWeek.toInt())
    val newCount = mutableIntStateOf(0)

    var monthIndex = calendar.monthNumber - 1
    private var pointerNextMonth = 0
    private var preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
    private var preMonthStartDays = preMonthDays - (6 - pointer.intValue)

    fun nowDayClick(navController: NavController) {
        if (monthIndex != calendar.monthNumber - 1 || year.value != calendar.year) {
            navController.navigate(NavigationTree.Start.name)
        }
    }

    fun textTable(count: Int, day: String): TextButtonParams {
        val days = month.value.days
        val dayNumber = calendar.daysWeek.indexOf(day)

        var text = ""
        var color = Color.White

        if (count in 1..days) { // 0 < count < days

            if (count + dayNumber <= days) {
                text = (count + dayNumber).toString()
                color = Color.White
                newCount.intValue += 1
            } else {
                text = (dayNumber - newCount.intValue + 1).toString()
                color = Color.LightGray
            }

            pointerNextMonth = 7 - newCount.intValue
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
            constants.selectDate.value = "${text}.${monthIndex + 1}.${year.value}"
        }
    }

    fun isEmptyFirstRow(count: Int) = !(count in 1..month.value.days || count <= 0 && count != -6)

    fun isToday(count: Int, day: String): Boolean {
        return (calendar.day.toInt() == count + calendar.daysWeek.indexOf(day) && monthIndex == calendar.monthNumber - 1 && calendar.year == year.value)
    }

    fun next() {
        if (monthIndex == 11) {
            year.value = (year.value.toInt() + 1).toString()
            changeMonth(-11)
            preMonthDays = selectMonth(year = year.value, index = 11).days
        } else {
            changeMonth(1)
            preMonthDays = selectMonth(year = year.value, index = monthIndex - 1).days
        }

        pointer.intValue = pointerNextMonth
        preMonthStartDays = preMonthDays - (6 - pointer.intValue)
    }

    fun back() {
        if (monthIndex == 0) {
            year.value = (year.value.toInt() - 1).toString()
            changeMonth(11)
            preMonthDays = selectMonth(year = year.value, index = 0).days
        } else {
            changeMonth(-1)
        }

        val term = if (pointer.intValue + month.value.days > 35) 42 else 35
        pointer.intValue = -(term - pointer.intValue - month.value.days - 7)
        preMonthStartDays = preMonthDays - 6 + pointer.intValue
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