package com.example.calendar.ui.screen.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.app.languages
import com.example.calendar.navigation.NavigationTree

@Composable
fun TasksScreen(navController: NavController, viewModel: TasksViewModel) {
    val dp = 12.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.2F))
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))

            TopBodyLayer(navController = navController)

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("Зробити дз", "21:40")
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("КК", "16:50")
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("Піти в універ", "21:20")
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("Написати програму", "21:30")
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("Зробити дз", "21:40")
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("КК", "16:50")
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("Піти в універ", "21:20")
        }

        item {
            Spacer(modifier = Modifier.height(dp))

            TaskItem("Написати програму", "21:30")
        }
    }
}

@Composable
fun TopBodyLayer(navController: NavController) {
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
            text = languages.taskTextTopBodyLayer,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 22.sp
        )
    }
}

@Composable
fun TaskItem(task: String, time: String) {

    Row(
        modifier = Modifier
            .height(86.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(CircleShape)
            .background(Color.White.copy(0.2F)), verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(28.dp))

        Column(
            modifier = Modifier.weight(1F), verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = task,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = time,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}