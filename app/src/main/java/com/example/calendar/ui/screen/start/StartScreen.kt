package com.example.calendar.ui.screen.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
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
fun StartScreen(navController: NavController, viewModel: StartViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Spacer(modifier = Modifier.height(15.dp))

        StartTopBody(viewModel = viewModel)

        Spacer(modifier = Modifier.height(25.dp))

        WeekdayRow(viewModel = viewModel)

        Spacer(modifier = Modifier.height(30.dp))

        TableMonth(viewModel = viewModel, navController = navController)
    }
}

//      Edit back fun if count == - 7 Error

@Composable
fun StartTopBody(viewModel: StartViewModel) {
    val color = Color.White

    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
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
    var count = -(6 - viewModel.pointer.value)

    while (count < viewModel.month.value.days) {
        var newCount = 0

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.calendar.daysWeek.forEach { day ->

                val days = viewModel.month.value.days
                val dayNumber = viewModel.calendar.daysWeek.indexOf(day)

                var modifier =
                    if (viewModel.calendar.day.toInt() == count + 7 - (7 - dayNumber) && viewModel.monthIndex == viewModel.calendar.monthNumber - 1) {
                        Modifier
                            .weight(1F)
                            .height(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xffff984f))
                    } else {
                        Modifier
                            .weight(1F)
                            .height(60.dp)
                    }

                val text = mutableStateOf("")

                val textColor = mutableStateOf(Color.White)

                if (count in 1..days) { // 0 < count < days

                    if (count + dayNumber <= days) {
                        text.value = (count + 7 - (7 - dayNumber)).toString()
                        textColor.value = Color.White
                        newCount += 1
                    } else {
                        text.value = (dayNumber - newCount + 1).toString()
                        textColor.value = Color.LightGray
                    }

                    viewModel.pointerNextMonth.value = 7 - newCount
                } else if (count < 0 && count != -6) { // count < 0

                    if (viewModel.preMonthStartDays + dayNumber <= viewModel.preMonthDays) {
                        text.value = (viewModel.preMonthStartDays + dayNumber).toString()
                        textColor.value = Color.LightGray
                    } else {
                        text.value = (count + 7 - (7 - dayNumber)).toString()
                        textColor.value = Color.White
                    }
                } else modifier = Modifier.height(0.dp)

                TextButton(
                    onClick = { navController.navigate(NavigationTree.Tasks.name) },
                    modifier = modifier
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