package com.example.calendar.ui.start

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.navigation.NavigationTree

@Composable
fun StartScreen(navController: NavController, viewModel: StartViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(15.dp))

        StartTopBody(viewModel = viewModel)

        Spacer(modifier = Modifier.height(25.dp))

        WeekdayRow(viewModel = viewModel)

        Spacer(modifier = Modifier.height(30.dp))

        TableMonth(viewModel = viewModel, navController = navController)

        Spacer(modifier = Modifier.weight(1F))

        StartLowLayer(navController = navController, day = viewModel.calendar.day)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun StartTopBody(viewModel: StartViewModel) {
    val color = Color.White

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        val modifier = Modifier.weight(1F)

        IconButton(onClick = { viewModel.back() }, modifier = modifier) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = color
            )
        }

        Text(
            text = viewModel.nameMonth.value + " " + viewModel.year.value,
            fontSize = 24.sp,
            color = color,
            modifier = modifier,
            textAlign = TextAlign.Center
        )

        IconButton(onClick = { viewModel.next() }, modifier = modifier) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = color
            )
        }
    }
}

@Composable
fun WeekdayRow(viewModel: StartViewModel) {
    val color = Color.White

    Row(modifier = Modifier.fillMaxWidth()) {
        viewModel.calendar.daysWeek.forEach { text ->

            Text(
                text = text,
                fontSize = 20.sp,
                color = color,
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TableMonth(viewModel: StartViewModel, navController: NavController) {
    var count = -(6 - viewModel.pointer.intValue)

    while (count < 7 - viewModel.pointer.intValue + viewModel.month.value.days) {
        viewModel.newCount.intValue = 0

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.calendar.daysWeek.forEach { day ->
                val text = mutableStateOf("")
                val textColor = mutableStateOf(Color.White)

                val modifier = if (viewModel.isToday(count = count, day = day)) Modifier
                    .weight(1F)
                    .clip(CircleShape)
                    .height(58.dp)
                    .background(Color(0xffff984f))
                else if (viewModel.isEmptyFirstRow(count = count)) Modifier.height(0.dp)
                else Modifier
                    .weight(1F)
                    .height(60.dp)

                val textButtonParams = viewModel.textTable(count = count, day = day)

                text.value = textButtonParams.text
                textColor.value = textButtonParams.color

                TextButton(
                    onClick = {
                        viewModel.dateClick(
                            color = textColor.value,
                            text = text.value,
                            navController = navController
                        )
                    }, modifier = modifier
                ) {

                    Text(
                        text = text.value,
                        fontSize = 20.sp,
                        color = textColor.value,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        count += 7
    }
}

@Composable
fun StartLowLayer(navController: NavController, day: String) {
    Row {
        Spacer(modifier = Modifier.weight(1F))

        TextButton(
            onClick = { navController.navigate(NavigationTree.Start.name) },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = Color(0xff23405e).copy(0.85F)
            ),
            modifier = Modifier
                .clip(CircleShape)
                .size(68.dp),
        ) {
            Text(text = day, fontSize = 24.sp, maxLines = 1)
        }

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            onClick = { navController.navigate(NavigationTree.Add.name) },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Gray.copy(0.5f))
                .border(2.dp, Color.Gray.copy(0.2F), CircleShape)
                .size(68.dp),
        ) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}