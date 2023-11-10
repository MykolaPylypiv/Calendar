package com.example.calendar.ui.add.components

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.calendar.app.Languages
import com.example.calendar.ui.add.AddViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StateDateDialog(languages: Languages, viewModel: AddViewModel) {
    if (viewModel.stateDateDialog.value) {
        Dialog(onDismissRequest = { viewModel.stateDateDialog.value = false }) {
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
                            viewModel.dayNumber.intValue = viewModel.dayNumberUp(
                                dayNumber = viewModel.dayNumber.intValue,
                                month = viewModel.month.intValue,
                                year = viewModel.year.intValue
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = viewModel.dayNumber.intValue, label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount.toString(),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.stateDaysDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            viewModel.dayNumber.intValue = viewModel.dayNumberDown(
                                dayNumber = viewModel.dayNumber.intValue,
                                month = viewModel.month.intValue,
                                year = viewModel.year.intValue
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
                            viewModel.month.intValue = viewModel.monthUp(viewModel.month.intValue)

                            viewModel.dayNumber.intValue = viewModel.dayNumberOverflow(
                                dayNumber = viewModel.dayNumber.intValue,
                                month = viewModel.month.intValue,
                                year = viewModel.year.intValue
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = viewModel.months[viewModel.month.intValue], label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount.name,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.stateMonthsDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            viewModel.month.intValue = viewModel.monthDown(viewModel.month.intValue)

                            viewModel.dayNumber.intValue = viewModel.dayNumberOverflow(
                                dayNumber = viewModel.dayNumber.intValue,
                                month = viewModel.month.intValue,
                                year = viewModel.year.intValue
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
                            viewModel.year.intValue = viewModel.year.intValue + 1
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = viewModel.year.intValue, label = ""
                        ) { targetCount ->
                            Text(
                                text = targetCount.toString(),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.stateYearsDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            viewModel.year.intValue = viewModel.yearDown(year = viewModel.year.intValue)
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow(languages = languages, onClick = {
                    viewModel.acceptDate(
                        dayNumber = viewModel.dayNumber.intValue, month = viewModel.month.intValue, year = viewModel.year.intValue
                    )

                    viewModel.stateDateDialog.value = false
                })

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }

    StateDaysDialog(viewModel = viewModel)

    StateMonthDialog(viewModel = viewModel)

    StateYearsDialog(viewModel = viewModel)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StateTimeDialog(viewModel: AddViewModel, languages: Languages, context: Context) {
    if (viewModel.stateTimeDialog.value) {
        Dialog(onDismissRequest = { viewModel.stateTimeDialog.value = false }) {
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
                            viewModel.hour.intValue = viewModel.hourUp(viewModel.hour.intValue)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(targetState = viewModel.hour.intValue, label = "") { targetCount ->
                            Text(
                                text = targetCount.toString(),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.stateHourDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            viewModel.hour.intValue = viewModel.hourDown(viewModel.hour.intValue)
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
                            viewModel.minute.intValue = viewModel.minuteUp(viewModel.minute.intValue)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(targetState = viewModel.minute.intValue, label = "") { targetCount ->
                            Text(
                                text = targetCount.toString(),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.stateMinutesDialog.value = true },
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButtonDown {
                            viewModel.minute.intValue = viewModel.minuteDown(viewModel.minute.intValue)
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow(languages = languages, onClick = {
                    viewModel.acceptTime(hour = viewModel.hour.intValue, minute = viewModel.minute.intValue)
                    viewModel.stateTimeDialog.value = false
                })

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    StateHourDialog(viewModel = viewModel)

    StateMinuteDialog(viewModel = viewModel, languages = languages, context = context)
}

@Composable
fun StateDaysDialog(viewModel: AddViewModel) {
    if (viewModel.stateDaysDialog.value) {
        Dialog(onDismissRequest = { viewModel.stateDaysDialog.value = false }) {
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
                        val daysInMonth = viewModel.selectMonth(viewModel.month.intValue, viewModel.year.intValue).days

                        for (i in 1..daysInMonth) {
                            item {
                                Text(
                                    text = i.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            viewModel.stateDaysDialog.value = false
                                            viewModel.dayNumber.intValue = i
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
}

@Composable
fun StateMonthDialog(viewModel: AddViewModel) {
    if (viewModel.stateMonthsDialog.value) {
        Dialog(onDismissRequest = { viewModel.stateMonthsDialog.value = false }) {
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
                                            viewModel.stateMonthsDialog.value = false
                                            viewModel.month.intValue = viewModel.months.indexOf(it)
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
}

@Composable
fun StateYearsDialog(viewModel: AddViewModel) {
    if (viewModel.stateYearsDialog.value) {
        Dialog(onDismissRequest = { viewModel.stateYearsDialog.value = false }) {
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
                                            viewModel.stateYearsDialog.value = false
                                            viewModel.year.intValue = i
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
fun StateHourDialog(viewModel: AddViewModel) {
    if (viewModel.stateHourDialog.value) {
        Dialog(onDismissRequest = { viewModel.stateHourDialog.value = false }) {
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
                                            viewModel.stateHourDialog.value = false
                                            viewModel.hour.intValue = it
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateMinuteDialog(viewModel: AddViewModel, context: Context, languages: Languages) {
    if (viewModel.stateMinutesDialog.value) {
        Dialog(onDismissRequest = { viewModel.stateMinutesDialog.value = false }) {
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
                                value = value.value, onValueChange = {
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
                                            viewModel.minute.intValue = it.toInt()
                                        }
                                    } catch (e: NumberFormatException) {
                                        Toast.makeText(
                                            context, languages.toastEnterNumber, Toast.LENGTH_SHORT
                                        ).show()
                                        value.value = ""
                                    }
                                }, singleLine = true, colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.DarkGray, cursorColor = Color.Gray
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
                                            viewModel.stateMinutesDialog.value = false
                                            viewModel.minute.intValue = it
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