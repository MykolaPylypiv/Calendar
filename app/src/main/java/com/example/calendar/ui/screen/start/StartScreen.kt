package com.example.calendar.ui.screen.start

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.calendar.R

val column = 6
val row = 7

@Composable
fun StartScreen(navController: NavController, viewModel: StartViewModel) {
    val color = Color.White

    Box(modifier = with(Modifier) {
        fillMaxSize().paint(
            painterResource(id = R.drawable.atmosphere_gradient),
            contentScale = ContentScale.FillBounds
        )
    }) {
        Column {

            Spacer(modifier = Modifier.height(15.dp))

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

            Spacer(modifier = Modifier.height(25.dp))

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

            Spacer(modifier = Modifier.height(30.dp))

            var count = -(6 - viewModel.pointer.value)

            while (count < viewModel.month.value.days) {
                var newCount = 0

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.calendar.daysWeek.forEach { day ->

                        val days = viewModel.month.value.days
                        val dayNumber = viewModel.calendar.daysWeek.indexOf(day)

                        val modifier =
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

                        if (count > 0 && count <= days) { // 0 < count < days

                            if (count + dayNumber <= days) {
                                TextButton(onClick = { /*TODO*/ }, modifier = modifier) {
                                    Text(
                                        text = (count + 7 - (7 - dayNumber)).toString(),
                                        fontSize = 20.sp,
                                        color = color,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                newCount += 1
                            } else {
                                TextButton(onClick = { /*TODO*/ }, modifier = modifier) {
                                    Text(
                                        text = (dayNumber - newCount + 1).toString(),
                                        fontSize = 20.sp,
                                        color = Color.LightGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            viewModel.pointerNextMonth.value = 7 - newCount
                        } else if (count < 0 && count != -6) { // count < 0
                            if (viewModel.preMonthStartDays + dayNumber <= viewModel.preMonthDays) {
                                TextButton(onClick = { /*TODO*/ }, modifier = modifier) {
                                    Text(
                                        text = (viewModel.preMonthStartDays + dayNumber).toString(),
                                        fontSize = 20.sp,
                                        color = Color.LightGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            } else {
                                TextButton(onClick = { /*TODO*/ }, modifier = modifier) {

                                    Text(
                                        text = (count + 7 - (7 - dayNumber)).toString(),
                                        fontSize = 20.sp,
                                        color = color,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
                count += 7
            }
        }
    }
}