package com.example.calendar.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.add.AddScreen
import com.example.calendar.ui.add.AddViewModel
import com.example.calendar.ui.edit.EditScreen
import com.example.calendar.ui.edit.EditViewModel
import com.example.calendar.ui.start.StartScreen
import com.example.calendar.ui.start.StartViewModel
import com.example.calendar.ui.tasks.TasksScreen
import com.example.calendar.ui.tasks.TasksViewModel

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Start.name) {

        composable(route = NavigationTree.Start.name, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700)
            )
        }) {
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