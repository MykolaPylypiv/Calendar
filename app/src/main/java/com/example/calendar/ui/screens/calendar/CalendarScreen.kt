package com.example.calendar.ui.screens.calendar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.screens.calendar.composables.TableMonth
import com.example.calendar.ui.screens.calendar.composables.WeekdayRow

// Екран на якому знаходиться календар
@Composable
fun CalendarScreen(navController: NavController, viewModel: CalendarViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(15.dp))

        CalendarTopBody(viewModel = viewModel)

        Spacer(modifier = Modifier.height(25.dp))

        WeekdayRow(viewModel = viewModel)

        Spacer(modifier = Modifier.height(30.dp))

        TableMonth(viewModel = viewModel, navController = navController)

        Spacer(modifier = Modifier.weight(1F))

        CalendarLowLayer(
            navController = navController, viewModel = viewModel, day = viewModel.dateTime.day
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CalendarTopBody(viewModel: CalendarViewModel) {
    val primaryColor = Color.White

    val transitionSpec =
        (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())

    var visible by remember { mutableStateOf(true)  }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            viewModel.previous()
            visible = !visible
        }, modifier = Modifier.weight(1F)) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = primaryColor
            )
        }

        AnimatedContent(
            transitionSpec = { transitionSpec }, targetState = viewModel.nameMonth.value, label = ""
        ) { targetCount ->
            Text(
                text = targetCount + " " + viewModel.year.value,
                fontSize = 24.sp,
                color = primaryColor,
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center
            )
        }

        IconButton(onClick = {
            viewModel.next()
            visible = !visible
        }, modifier = Modifier.weight(1F)) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = primaryColor
            )
        }
    }
}

@Composable
fun CalendarLowLayer(
    navController: NavController,
    day: String,
    viewModel: CalendarViewModel,
) {
    Row {
        Spacer(modifier = Modifier.weight(1F))

        AnimatedVisibility(visible = viewModel.visibleNowDayClick.value) {
            TextButton(
                onClick = {
                    viewModel.nowDayClick(navController = navController)
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = Color(0xff23405e).copy(0.85F)
                ),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(68.dp),
            ) {
                Text(text = day, fontSize = 24.sp, maxLines = 1)
            }
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