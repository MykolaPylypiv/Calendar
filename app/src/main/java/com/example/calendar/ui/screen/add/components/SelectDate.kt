package com.example.calendar.ui.screen.add.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
import com.example.calendar.ui.screen.add.AddViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectDate(viewModel: AddViewModel) {
    val stateDialog = remember { mutableStateOf(false) }
    val stateDaysDialog = remember { mutableStateOf(false) }
    val stateMonthsDialog = remember { mutableStateOf(false) }

    val dayNumber = remember { mutableStateOf(viewModel.calendar.day.toInt()) }
    val month = remember { mutableStateOf(viewModel.calendar.monthNumber - 1) }

    StartRow(
        onClick = { stateDialog.value = true },
        firstText = languages.date,
        secondText = viewModel.date.value
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

                        IconButtonUp {
                            dayNumber.value =
                                if (dayNumber.value == viewModel.selectMonth(month.value).days) {
                                    1
                                } else dayNumber.value + 1
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = dayNumber.value.toString(), label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { stateDaysDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            val daysInMonth = viewModel.selectMonth(month.value).days

                            dayNumber.value =
                                if (dayNumber.value == 1) {
                                    daysInMonth
                                } else dayNumber.value - 1
                        }

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

                            val daysInMonth = viewModel.selectMonth(month.value).days
                            if (daysInMonth < dayNumber.value) dayNumber.value = 1
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
                                    .clickable { stateMonthsDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            month.value = if (month.value == 0) 11 else month.value - 1

                            val daysInMonth = viewModel.selectMonth(month.value).days
                            if (daysInMonth < dayNumber.value) dayNumber.value = 1
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow {
                    viewModel.date.value = "${dayNumber.value}.${month.value + 1}"
                    viewModel.newTask.date = "${dayNumber.value}.${month.value + 1}"
                    stateDialog.value = false
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (stateDaysDialog.value) {
        Dialog(onDismissRequest = { stateDaysDialog.value = false }) {
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
                        val daysInMonth = viewModel.selectMonth(month.value).days

                        for (i in 1..daysInMonth) {
                            item {
                                Text(
                                    text = i.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            stateDaysDialog.value = false
                                            dayNumber.value = i
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

    if (stateMonthsDialog.value) {
        Dialog(onDismissRequest = { stateMonthsDialog.value = false }) {
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
                                            stateMonthsDialog.value = false
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