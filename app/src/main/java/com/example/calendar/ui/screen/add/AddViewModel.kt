package com.example.calendar.ui.screen.add

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.app.languages
import com.example.calendar.data.db.room.TasksDao
import com.example.calendar.domain.Calendar
import com.example.calendar.domain.model.Month
import com.example.calendar.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    val calendar: Calendar, private val tasksDao: TasksDao
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

    val newTask = Task()

    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksDao.insert(task)
        }
    }

    fun selectMonth(index: Int): Month {
        val year = calendar.year

        return if (year.toInt() % 4 == 0 && index == 1) {
            Month(name = "February", days = 29)
        } else calendar.listOfMonth[index]
    }

    val date = mutableStateOf("${calendar.day}.${calendar.monthNumber}")

    val time = mutableStateOf("${calendar.hour}.${calendar.minute}")
}