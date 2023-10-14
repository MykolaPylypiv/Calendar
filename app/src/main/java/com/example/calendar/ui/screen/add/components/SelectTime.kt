package com.example.calendar.ui.screen.add.components

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
import com.example.calendar.ui.screen.add.AddViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SelectTime(context: Context, viewModel: AddViewModel, languages: Languages) {
    val stateDialog = remember { mutableStateOf(false) }
    val stateHourDialog = remember { mutableStateOf(false) }
    val stateMinutesDialog = remember { mutableStateOf(false) }

    val hour = remember { mutableStateOf(viewModel.calendar.hour.toInt()) }
    val minute = remember { mutableStateOf(viewModel.calendar.minute.toInt()) }

    StartRow(
        onClick = { stateDialog.value = true }, firstText = languages.time, secondText = viewModel.time.value
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
                            minute.value = if (minute.value >= 55) 0 else minute.value + 5
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
                            minute.value = if (minute.value < 5) 55 else minute.value - 5
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow(languages = languages, onClick = {
                    viewModel.time.value = "${hour.value}.${minute.value}"
                    viewModel.newTask.time = "${hour.value}.${minute.value}"
                    stateDialog.value = false
                })

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