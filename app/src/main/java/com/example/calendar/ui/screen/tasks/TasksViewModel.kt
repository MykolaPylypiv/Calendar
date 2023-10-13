package com.example.calendar.ui.screen.tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.data.repository.TaskRepository
import com.example.calendar.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    val tasks: State<List<Task>> = _tasks

    init {
        loadTask()
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
            _tasks.value = repository.tasks()
        }
    }

    private fun loadTask() {
        viewModelScope.launch {
            _tasks.value = repository.tasks()
        }
    }
}