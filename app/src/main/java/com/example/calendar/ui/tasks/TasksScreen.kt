package com.example.calendar.ui.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.app.Languages
import com.example.calendar.domain.model.Task
import com.example.calendar.navigation.NavigationTree
import kotlinx.coroutines.launch

@Composable
fun TasksScreen(navController: NavController, viewModel: TasksViewModel) {
    val languages = viewModel.languages

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.2F))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(8.dp))

            TopBodyLayer(
                navController = navController, languages = languages, viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        TaskList(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun TopBodyLayer(navController: NavController, languages: Languages, viewModel: TasksViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.Transparent)
    ) {
        IconButton(onClick = { navController.navigate(NavigationTree.Start.name) }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(24.dp),
            )
        }

        Text(
            text = languages.taskTextTopBodyLayer + viewModel.topBodyText(),
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 22.sp
        )
    }
}

@Composable
fun TaskList(viewModel: TasksViewModel, navController: NavController) {
    val dp = 12.dp
    val density = LocalDensity.current

    if (viewModel.tasks.value.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            viewModel.tasks.value.forEach { task ->
                item {
                    var visible by remember { mutableStateOf(false) }

                    Spacer(modifier = Modifier.height(dp))

                    AnimatedVisibility(visible = visible, enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ), exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
                        TaskItem(
                            task = task, viewModel = viewModel, navController = navController
                        )
                    }

                    visible = true
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .height(500.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Empty", color = Color.Black, fontSize = 30.sp, textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task, viewModel: TasksViewModel, navController: NavController
) {
    val visibility = remember {
        Animatable(1f)
    }

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .height(86.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(CircleShape)
            .alpha(visibility.value)
            .background(Color.White.copy(0.2F)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(28.dp))

        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .clickable {
                    navController.navigate(NavigationTree.Edit.name)
                    viewModel.constants.selectTask.value = task
                }, verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = task.name,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.weight(1F)) {
                Text(
                    text = task.time,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = task.repeat,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.Start
                )
            }
        }

        IconButton(onClick = {
            scope.launch {
                viewModel.deleteTask(task, visibility)
            }
        }) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}