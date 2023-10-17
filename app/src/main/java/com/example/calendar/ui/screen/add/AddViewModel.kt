package com.example.calendar.ui.screen.add

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.calendar.app.Languages
import com.example.calendar.data.repository.TaskRepository
import com.example.calendar.domain.Calendar
import com.example.calendar.domain.model.Month
import com.example.calendar.domain.model.Task
import com.example.calendar.navigation.NavigationTree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    val calendar: Calendar, val languages: Languages, private val repository: TaskRepository
) : ViewModel() {
    val hours =
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)

    val minutes = listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55)

    val listRepeat = listOf(
        languages.oneTime,
        languages.everyDay,
        languages.everyWeek,
        languages.everyTwoWeek,
        languages.everyMonth,
        languages.everyYear
    )

    val months = calendar.listOfMonth

    val newTask = Task(
        repeat = languages.oneTime,
        time = "${calendar.hour}:${calendar.minute}",
        date = "${calendar.listOfMonth[calendar.monthNumber - 1].name} ${calendar.day}, ${calendar.year}"
    )

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
        } else calendar.listOfMonth[index]
    }

    val date = mutableStateOf("${calendar.day}.${calendar.monthNumber}.${calendar.year}")

    val time = mutableStateOf("${calendar.hour}.${calendar.minute}")

    //Гавнокод

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
        val nowYear = calendar.year.toInt()

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

        newTask.date = "${months[month].name} ${dayNumber}, $year"
    }

    fun acceptTime(hour: Int, minute: Int) {
        time.value = if (minute < 10) "${hour}:0${minute}" else "${hour}:${minute}"

        if (minute < 10) {
            time.value = "0${time.value}"
        }

        newTask.time = time.value
    }

    init {
        acceptTime(hour = calendar.hour.toInt(),minute = calendar.minute.toInt())
    }
}