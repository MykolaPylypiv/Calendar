package com.example.calendar.ui.screens.add

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.calendar.data.repository.TaskRepository
import com.example.calendar.domain.DateTime
import com.example.calendar.domain.model.Month
import com.example.calendar.domain.model.Task
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.theme.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    val dateTime: DateTime, val languages: Languages, private val repository: TaskRepository
) : ViewModel() {
    val hours =
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)

    val minutes = listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55)

    val listRepeat = listOf(
        languages.oneTime, languages.everyDay, languages.everyMonth, languages.everyYear
    )

    val months = dateTime.listOfMonth

    val newTask = Task(
        repeat = languages.oneTime,
        time = "${dateTime.hour}:${dateTime.minute}",
        date = "${dateTime.day}.${dateTime.monthNumber}.${dateTime.year}"
    )

    val stateDateDialog = mutableStateOf(false)
    val stateDaysDialog = mutableStateOf(false)
    val stateMonthsDialog = mutableStateOf(false)
    val stateYearsDialog = mutableStateOf(false)

    val stateTimeDialog = mutableStateOf(false)
    val stateMinutesDialog = mutableStateOf(false)
    val stateHourDialog = mutableStateOf(false)

    val year = mutableIntStateOf(dateTime.year.toInt())
    val month = mutableIntStateOf(dateTime.monthNumber - 1)
    val dayNumber = mutableIntStateOf(dateTime.day.toInt())

    val hour = mutableIntStateOf(dateTime.hour.toInt())
    val minute = mutableIntStateOf(dateTime.minute.toInt())

    val date = mutableStateOf("${dateTime.day}.${dateTime.monthNumber}.${dateTime.year}")
    val time = mutableStateOf("${dateTime.hour}:${dateTime.minute}")

    init {
        acceptTime(hour = dateTime.hour.toInt(), minute = dateTime.minute.toInt())
    }

    fun icon(value: Boolean) =
        if (value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight

    fun insert(task: Task, context: Context, navController: NavController) {
        if (task.name.isEmpty()) {
            Toast.makeText(
                context, languages.toastEnterName, Toast.LENGTH_SHORT
            ).show()
        } else {
            navController.navigate(NavigationTree.Start.name)
            viewModelScope.launch(Dispatchers.IO) {
                repository.insert(task)
            }
        }
    }

    fun selectMonth(index: Int, year: Int): Month {
        return if (year % 4 == 0 && index == 1) {
            Month(name = "February", days = 29)
        } else dateTime.listOfMonth[index]
    }

    fun minuteUp(minute: Int): Int = if (minute >= 55) 0 else minute + 5

    fun minuteDown(minute: Int): Int = if (minute < 5) 55 else minute - 5

    fun hourUp(hour: Int): Int = if (hour == 23) 0 else hour + 1

    fun hourDown(hour: Int): Int = if (hour == 0) 23 else hour - 1
    fun dayNumberUp(dayNumber: Int, month: Int, year: Int): Int =
        if (dayNumber == selectMonth(month, year).days) 1 else dayNumber + 1

    fun dayNumberDown(dayNumber: Int, month: Int, year: Int): Int {
        val daysInMonth = selectMonth(month, year).days

        return if (dayNumber == 1) {
            daysInMonth
        } else dayNumber - 1
    }

    fun monthUp(month: Int): Int = if (month == 11) 0 else month + 1

    fun monthDown(month: Int): Int = if (month == 0) 11 else month - 1

    fun yearDown(year: Int): Int {
        val nowYear = dateTime.year.toInt()

        return if (year == nowYear) nowYear else year - 1
    }

    fun dayNumberOverflow(dayNumber: Int, month: Int, year: Int): Int {
        val daysInMonth = selectMonth(month, year).days

        return if (daysInMonth < dayNumber) 1 else dayNumber
    }

    fun acceptDate(dayNumber: Int, month: Int, year: Int) {
        date.value = if (month < 9) "${dayNumber}.0${month + 1}.${year}"
        else "${dayNumber}.${month + 1}.${year}"

        if (dayNumber < 10) date.value = "0${date.value}"

        newTask.date = "${dayNumber}.${month + 1}.${year}"
    }

    fun acceptTime(hour: Int, minute: Int) {
        time.value = if (minute < 10) "${hour}:0${minute}" else "${hour}:${minute}"

        if (hour < 10) {
            time.value = "0${time.value}"
        }

        newTask.time = time.value
    }
}