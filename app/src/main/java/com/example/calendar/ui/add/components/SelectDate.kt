package com.example.calendar.ui.add.components

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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.calendar.app.Languages
import com.example.calendar.ui.add.AddViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectDate(viewModel: AddViewModel, languages: Languages, borderColor: Color) {
    val stateDialog = remember { mutableStateOf(false) }
    val stateDaysDialog = remember { mutableStateOf(false) }
    val stateMonthsDialog = remember { mutableStateOf(false) }
    val stateYearsDialog = remember { mutableStateOf(false) }

    val dayNumber = remember { mutableIntStateOf(viewModel.calendar.day.toInt()) }
    val month = remember { mutableIntStateOf(viewModel.calendar.monthNumber - 1) }
    val year = remember { mutableIntStateOf(viewModel.calendar.year.toInt()) }

    StartRow(
        onClick = { stateDialog.value = true },
        firstText = languages.date,
        secondText = viewModel.date.value,
        borderColor = borderColor,
    )

    if (stateDialog.value) {
        Dialog(onDismissRequest = { stateDialog.value = false }) {
            Card {
                Row(modifier = Modifier.fillMaxWidth()) {

                    Column(
                        modifier = Modifier.weight(0.7f),
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
                            dayNumber.intValue = viewModel.dayNumberUp(
                                dayNumber = dayNumber.intValue,
                                month = month.intValue,
                                year = year.intValue
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = dayNumber.intValue, label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount.toString(),
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
                            dayNumber.intValue = viewModel.dayNumberDown(
                                dayNumber = dayNumber.intValue,
                                month = month.intValue,
                                year = year.intValue
                            )
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
                            month.intValue = viewModel.monthUp(month.intValue)

                            dayNumber.intValue = viewModel.dayNumberOverflow(
                                dayNumber = dayNumber.intValue,
                                month = month.intValue,
                                year = year.intValue
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = viewModel.months[month.intValue], label = ""
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
                            month.intValue = viewModel.monthDown(month.intValue)

                            dayNumber.intValue = viewModel.dayNumberOverflow(
                                dayNumber = dayNumber.intValue,
                                month = month.intValue,
                                year = year.intValue
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Column(
                        modifier = Modifier.weight(0.7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = languages.year,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        IconButtonUp {
                            year.intValue = year.intValue + 1
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = year.intValue, label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount.toString(),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { stateYearsDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            year.intValue = viewModel.yearDown(year = year.intValue)
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow(languages = languages, onClick = {
                    viewModel.acceptDate(
                        dayNumber = dayNumber.intValue, month = month.intValue, year = year.intValue
                    )

                    stateDialog.value = false
                })

                Spacer(modifier = Modifier.height(4.dp))
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
                        val daysInMonth = viewModel.selectMonth(month.intValue, year.intValue).days

                        for (i in 1..daysInMonth) {
                            item {
                                Text(
                                    text = i.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            stateDaysDialog.value = false
                                            dayNumber.intValue = i
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
                                            month.intValue = viewModel.months.indexOf(it)
                                        },
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1F))
            }
        }
    }
    if (stateYearsDialog.value) {
        Dialog(onDismissRequest = { stateYearsDialog.value = false }) {
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
                        val firstYear = viewModel.calendar.year.toInt()
                        val lastYear = viewModel.calendar.year.toInt() + 30

                        for (i in firstYear..lastYear) {
                            item {
                                Text(
                                    text = i.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            stateYearsDialog.value = false
                                            year.intValue = i
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