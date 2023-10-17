package com.example.calendar.ui.screen.edit

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.calendar.app.Languages
import com.example.calendar.data.repository.TaskRepository
import com.example.calendar.domain.Calendar
import com.example.calendar.data.repository.Repository
import com.example.calendar.domain.model.Task
import com.example.calendar.navigation.NavigationTree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    val languages: Languages, val calendar: Calendar, private val repository: TaskRepository
) : ViewModel() {

    val task = Repository.selectTask.value

    fun update(task: Task, context: Context, navController: NavController) {
        if (task.name.isEmpty()) {
            Toast.makeText(
                context, languages.toastEnterName, Toast.LENGTH_SHORT
            ).show()
        } else {
            navController.navigate(NavigationTree.Tasks.name)
            viewModelScope.launch(Dispatchers.IO) {
                repository.update(task)
            }
        }
    }

}