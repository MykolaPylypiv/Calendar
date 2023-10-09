package com.example.calendar.ui.screen.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.app.imageBackground
import com.example.calendar.navigation.NavigationTree

@Composable
fun TasksScreen(navController: NavController, viewModel: TasksViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.2F)),
    ) {
        TopBodyLayer(navController = navController)

        Spacer(modifier = Modifier.weight(0.04F))

        LazyColumn(modifier = Modifier.weight(1F)) {
            item { TaskItem("Зробити дз", "21.40") }

            item { TaskItem("КК", "16.50") }

            item { TaskItem("Піти в універ", "21.20") }

            item { TaskItem("Написати програму", "21.30") }

            item { TaskItem("Зробити дз", "21.40") }

            item { TaskItem("КК", "16.50") }

            item { TaskItem("Піти в універ", "21.20") }

            item { TaskItem("Написати програму", "21.30") }
        }

        AddButton(navController = navController)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun TopBodyLayer(navController: NavController) {
    Row(horizontalArrangement = Arrangement.Center) {
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
            text = "Here are your tasks for the day",
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 22.sp
        )
    }
}

@Composable
fun TaskItem(task: String, time: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(CircleShape)
            .border(2.dp, Color.DarkGray, CircleShape)
            .background(Color.Transparent)
            .height(64.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = task,
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = time,
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AddButton(navController: NavController) {
    Row {
        Spacer(modifier = Modifier.weight(1F))

        IconButton(
            onClick = { navController.navigate(NavigationTree.Add.name) },
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, Color.DarkGray, CircleShape)
                .size(64.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}