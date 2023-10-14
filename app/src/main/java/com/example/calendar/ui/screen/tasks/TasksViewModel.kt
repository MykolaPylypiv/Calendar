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
            for (i in repository.tasks(date = date.value)) {
                if (i.repeat == "Every day") {
                    tasks.add(i)
                } else if (i.repeat == "Every year") {
                    val selectDate = date.value.substring(0, date.value.length - 5)
                    val taskDate = i.date.substring(0, i.date.length - 5)

                    if (selectDate == taskDate) {
                        tasks.add(i)
                    }
                } else if (i.repeat == "Every month") {
                    val selectDay =
                        date.value.substring(date.value.length - 8, date.value.length - 6)
                    val taskDay = i.date.substring(i.date.length - 8, i.date.length - 6)

                    if (selectDay == taskDay) {
                        tasks.add(i)
                    }
                }
            }

            _tasks.value = tasks
        }
    }
}