package com.example.calendar.ui.screen.add

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.calendar.app.Languages
import com.example.calendar.data.db.room.TasksDao
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
        time = "${calendar.hour}.${calendar.minute}",
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

    fun selectMonth(index: Int): Month {
        val year = calendar.year

        return if (year.toInt() % 4 == 0 && index == 1) {
            Month(name = "February", days = 29)
        } else calendar.listOfMonth[index]
    }

    val date = mutableStateOf("${calendar.day}.${calendar.monthNumber}.${calendar.year}")

    val time = mutableStateOf("${calendar.hour}.${calendar.minute}")
}