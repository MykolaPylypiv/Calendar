package com.example.calendar.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.screens.add.AddScreen
import com.example.calendar.ui.screens.add.AddViewModel
import com.example.calendar.ui.screens.edit.EditScreen
import com.example.calendar.ui.screens.edit.EditViewModel
import com.example.calendar.ui.screens.start.StartScreen
import com.example.calendar.ui.screens.start.StartViewModel
import com.example.calendar.ui.screens.tasks.TasksScreen
import com.example.calendar.ui.screens.tasks.TasksViewModel

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Start.name) {

        composable(route = NavigationTree.Start.name) {
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
        composable(NavigationTree.Edit.name) {
            val editViewModel = hiltViewModel<EditViewModel>()
            EditScreen(viewModel = editViewModel, navController = navController)
        }
    }
}