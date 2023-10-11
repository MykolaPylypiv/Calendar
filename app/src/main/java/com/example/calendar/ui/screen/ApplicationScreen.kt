package com.example.calendar.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.screen.add.AddScreen
import com.example.calendar.ui.screen.add.AddViewModel
import com.example.calendar.ui.screen.start.StartScreen
import com.example.calendar.ui.screen.start.StartViewModel
import com.example.calendar.ui.screen.tasks.TasksScreen
import com.example.calendar.ui.screen.tasks.TasksViewModel

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Start.name) {

        composable(NavigationTree.Start.name) {
            val startViewModel = hiltViewModel<StartViewModel>()
            StartScreen(viewModel = startViewModel, navController = navController)
        }
        composable(NavigationTree.Tasks.name) {
            val tasksViewModel = hiltViewModel<TasksViewModel>()
            TasksScreen(viewModel = tasksViewModel, navController = navController)
        }
        composable(NavigationTree.Add.name) {
            val addViewModel = hiltViewModel<AddViewModel>()
            AddScreen(viewModel = addViewModel, navController = navController)
        }
    }
}