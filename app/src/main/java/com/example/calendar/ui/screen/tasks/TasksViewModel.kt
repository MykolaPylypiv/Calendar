package com.example.calendar.ui.screen.tasks

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.app.Languages
import com.example.calendar.app.date
import com.example.calendar.data.repository.TaskRepository
import com.example.calendar.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    val languages: Languages, private val repository: TaskRepository
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
        visible.animateTo(targetValue = 0f, animationSpec = tween(400))

        loadTask()

        visible.animateTo(targetValue = 1f, animationSpec = tween(10))
    }

    private fun loadTask() {
        val tasks: MutableList<Task> = mutableListOf()

        viewModelScope.launch {
            for (item in repository.tasks(date = date.value)) {
                item.time = item.time.replace(":", ".")

                if (item.repeat == "One time") {
                    tasks.add(item)
                } else if (item.repeat == "Every day") {
                    tasks.add(item)
                } else if (item.repeat == "Every year") {
                    val selectDate = date.value.substring(0, date.value.length - 5)
                    val taskDate = item.date.substring(0, item.date.length - 5)

                    if (selectDate == taskDate) {
                        tasks.add(item)
                    }
                } else if (item.repeat == "Every month") {
                    val selectDay =
                        date.value.substring(date.value.length - 8, date.value.length - 6)
                    val taskDay = item.date.substring(item.date.length - 8, item.date.length - 6)

                    if (selectDay == taskDay) {
                        tasks.add(item)
                    }
                }
            }
            tasks.sortBy { it.time.toFloat() }

            _tasks.value = tasks
        }
    }
}