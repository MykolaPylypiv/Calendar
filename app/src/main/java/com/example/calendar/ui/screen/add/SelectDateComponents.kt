package com.example.calendar.ui.screen.add

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.calendar.app.languages

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectDate(viewModel: AddViewModel) {
    val stateDialog = remember { mutableStateOf(false) }
    val stateMonthDialog = remember { mutableStateOf(false) }
    val stateDayDialog = remember { mutableStateOf(false) }

    val day = remember { mutableStateOf(0) }
    val month = remember { mutableStateOf(0) }

    StartRow(
        onClick = { stateDialog.value = true }, firstText = languages.date, secondText = "09.10"
    )

    if (stateDialog.value) {
        Dialog(onDismissRequest = { stateDialog.value = false }) {
            Card {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(15.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = languages.day,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        IconButtonUp { day.value = if (day.value == 6) 0 else day.value + 1 }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = viewModel.days[day.value], label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { stateMonthDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown { day.value = if (day.value == 0) 6 else day.value - 1 }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = languages.month,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        IconButtonUp {
                            month.value = if (month.value == 11) 0 else month.value + 1
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = viewModel.months[month.value], label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount.name,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { stateDayDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            month.value = if (month.value == 0) 11 else month.value - 1
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow { stateDialog.value = false }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (stateMonthDialog.value) {
        Dialog(onDismissRequest = { stateMonthDialog.value = false }) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(10))
                ) {
                    LazyColumn(
                        modifier = Modifier.background(Color.Gray.copy(0.2F)),
                        horizontalAlignment = Alignment.Start
                    ) {
                        viewModel.days.forEach {
                            item {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            stateMonthDialog.value = false
                                            day.value = viewModel.days.indexOf(it)
                                        },
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1F))
                }
            }
        }
    }

    if (stateDayDialog.value) {
        Dialog(onDismissRequest = { stateDayDialog.value = false }) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1F))

                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(10))
                ) {
                    LazyColumn(
                        modifier = Modifier.background(Color.Gray.copy(0.2F)),
                        horizontalAlignment = Alignment.Start
                    ) {
                        viewModel.months.forEach {
                            item {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            stateDayDialog.value = false
                                            month.value = viewModel.months.indexOf(it)
                                        },
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SelectTime(context: Context, viewModel: AddViewModel) {
    val stateDialog = remember { mutableStateOf(false) }
    val stateHourDialog = remember { mutableStateOf(false) }
    val stateMinutesDialog = remember { mutableStateOf(false) }

    val hour = remember { mutableStateOf(0) }
    val minute = remember { mutableStateOf(0) }

    StartRow(
        onClick = { stateDialog.value = true }, firstText = languages.time, secondText = "23:21"
    )

    if (stateDialog.value) {
        Dialog(onDismissRequest = { stateDialog.value = false }) {
            Card {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(15.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = languages.hour,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        IconButtonUp {
                            hour.value = if (hour.value == 23) 0 else hour.value + 1
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(targetState = hour.value, label = "") { targetCount ->
                            Text(
                                text = targetCount.toString(),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { stateHourDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            hour.value = if (hour.value == 0) 23 else hour.value - 1
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = languages.minute,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        IconButtonUp {
                            minute.value = if (minute.value == 55) 0 else minute.value + 5
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(targetState = minute.value, label = "") { targetCount ->
                            Text(
                                text = targetCount.toString(),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { stateMinutesDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            minute.value = if (minute.value == 0) 55 else minute.value - 5
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow { stateDialog.value = false }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (stateHourDialog.value) {
        Dialog(onDismissRequest = { stateHourDialog.value = false }) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(10))
                ) {
                    LazyColumn(
                        modifier = Modifier.background(Color.Gray.copy(0.2F)),
                        horizontalAlignment = Alignment.Start
                    ) {
                        viewModel.hours.forEach {
                            item {
                                Text(
                                    text = it.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            stateHourDialog.value = false
                                            hour.value = it
                                        },
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1F))
                }
            }
        }
    }

    if (stateMinutesDialog.value) {
        Dialog(onDismissRequest = { stateMinutesDialog.value = false }) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1F))

                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(10))
                ) {
                    LazyColumn(
                        modifier = Modifier.background(Color.Gray.copy(0.2F)),
                        horizontalAlignment = Alignment.Start
                    ) {
                        item {
                            val value = remember { mutableStateOf("") }

                            TextField(
                                value = value.value,
                                onValueChange = {
                                    try {
                                        if (it.toInt() > 59 || it.toInt() < 0) {
                                            Toast.makeText(
                                                context,
                                                languages.toastEnterNumberBetweenRange,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            value.value = ""
                                        } else {
                                            value.value = it
                                            minute.value = it.toInt()
                                        }
                                    } catch (e: NumberFormatException) {
                                        Toast.makeText(
                                            context,
                                            languages.toastEnterNumber,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        value.value = ""
                                    }
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.DarkGray,
                                    cursorColor = Color.Gray
                                )
                            )
                        }

                        viewModel.minutes.forEach {
                            item {
                                Text(
                                    text = it.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            stateMinutesDialog.value = false
                                            minute.value = it
                                        },
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IconButtonUp(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Up"
        )
    }
}

@Composable
fun IconButtonDown(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Down"
        )
    }
}

@Composable
fun AcceptRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = languages.accept,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Right,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Accept",
        )
    }
}

@Composable
fun StartRow(onClick: () -> Unit, firstText: String, secondText: String) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
            .border(1.dp, borderColor, CircleShape)
            .clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = firstText,
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = secondText,
            modifier = Modifier
                .height(50.dp)
                .padding(13.dp),
            color = Color.DarkGray
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Open",
            tint = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(8.dp))
    }
}