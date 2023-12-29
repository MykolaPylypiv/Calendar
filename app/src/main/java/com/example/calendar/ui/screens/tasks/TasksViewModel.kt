package com.example.calendar.ui.screens.tasks

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.ui.theme.Languages
import com.example.calendar.data.repository.Repository
import com.example.calendar.data.repository.TaskRepository
import com.example.calendar.domain.DateTime
import com.example.calendar.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    val languages: Languages,
    val constants: Repository,
    private val repository: TaskRepository,
    private val dateTime: DateTime
) : ViewModel() {

    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    val tasks: State<List<Task>> = _tasks

    init {
        loadTask()
    }

    suspend fun deleteTask(
        task: Task, visible: Animatable<Float, AnimationVector1D>
    ) {
        repository.delete(task)
        visible.animateTo(targetValue = 0f, animationSpec = TweenSpec(400, 0, FastOutSlowInEasing))

        loadTask()

        visible.animateTo(targetValue = 1f, animationSpec = TweenSpec(400, 0, FastOutSlowInEasing))
    }

    fun topBodyText(): String {
        val dateList = Repository.selectDate.value.split(".")

        val day = dateList[0]
        val month = dateTime.listOfMonth[dateList[1].toInt() - 1].name
        val year = dateList[2]

        return "$day $month, $year"
    }

    private fun loadTask() {
        val tasks: MutableList<Task> = mutableListOf()

        viewModelScope.launch {
            val dateList = Repository.selectDate.value.split(".")

            for (item in repository.tasks(date = Repository.selectDate.value)) {
                val taskDateList = item.date.split(".")

                when (item.repeat) {
                    "One time", "Every day" -> tasks.add(item)

                    "Every year" -> {
                        val selectDate = dateList[0] + dateList[1]
                        val taskDate = taskDateList[0] + taskDateList[1]

                        if (selectDate == taskDate) tasks.add(item)
                    }

                    "Every month" -> {
                        val selectDay = dateList[0]
                        val taskDay = taskDateList[0]

                        if (selectDay == taskDay) tasks.add(item)
                    }
                }
            }

            tasks.sortBy { it.time.replace(":", ".").toFloat() }

            _tasks.value = tasks
        }
    }
}