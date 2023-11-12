package com.example.calendar.ui.add.components

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.app.Languages
import com.example.calendar.ui.add.AddViewModel

@Composable
fun DateTitle(viewModel: AddViewModel, languages: Languages, borderColor: Color) {
    val icon = viewModel.icon(viewModel.stateDateDialog.value)

    StartRow(
        onClick = {
            viewModel.stateDateDialog.value = !viewModel.stateDateDialog.value
        },
        firstText = languages.date,
        secondText = viewModel.date.value,
        borderColor = borderColor,
        icon = icon
    )

    SelectDate(languages = languages, viewModel = viewModel)
}

@Composable
fun SelectTime(
    context: Context, viewModel: AddViewModel, languages: Languages, borderColor: Color
) {
    val icon = viewModel.icon(viewModel.stateTimeDialog.value)

    StartRow(
        onClick = { viewModel.stateTimeDialog.value = !viewModel.stateTimeDialog.value },
        firstText = languages.time,
        secondText = viewModel.time.value,
        borderColor = borderColor,
        icon = icon
    )

    StateTimeDialog(languages = languages, viewModel = viewModel, context = context)
}

@Composable
fun SelectDate(languages: Languages, viewModel: AddViewModel) {
    AnimatedVisibility(
        visible = viewModel.stateDateDialog.value,
        enter = scaleIn() + expandVertically() + fadeIn(),
        exit = scaleOut() + shrinkVertically() + fadeOut(),
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(0.2F)),
            modifier = Modifier
                .padding(20.dp)
                .clip(RoundedCornerShape(20))
                .border(1.dp, Color.DarkGray, RoundedCornerShape(20))
        ) {
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
                    dayNumber = viewModel.dayNumber.intValue,
                    month = viewModel.month.intValue,
                    year = viewModel.year.intValue
                )

                viewModel.stateDateDialog.value = false
            })

            Spacer(modifier = Modifier.height(4.dp))
        }
    }

    StateDaysDialog(viewModel = viewModel)

    StateMonthDialog(viewModel = viewModel)

    StateYearsDialog(viewModel = viewModel)
}

@Composable
fun StateTimeDialog(viewModel: AddViewModel, languages: Languages, context: Context) {
    AnimatedVisibility(
        visible = viewModel.stateTimeDialog.value,
        enter = scaleIn() + expandVertically() + fadeIn(),
        exit = scaleOut() + shrinkVertically() + fadeOut(),
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(0.2F)),
            modifier = Modifier
                .padding(20.dp)
                .clip(RoundedCornerShape(20))
                .border(1.dp, Color.DarkGray, RoundedCornerShape(20))
        ) {
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

                        AnimatedContent(
                            targetState = viewModel.hour.intValue, label = ""
                        ) { targetCount ->
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
                            viewModel.minute.intValue =
                                viewModel.minuteUp(viewModel.minute.intValue)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedContent(
                            targetState = viewModel.minute.intValue, label = ""
                        ) { targetCount ->
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
                            viewModel.minute.intValue =
                                viewModel.minuteDown(viewModel.minute.intValue)
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                }

                Spacer(modifier = Modifier.height(25.dp))

                AcceptRow(languages = languages, onClick = {
                    viewModel.acceptTime(
                        hour = viewModel.hour.intValue, minute = viewModel.minute.intValue
                    )
                    viewModel.stateTimeDialog.value = false
                })

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    StateHourDialog(viewModel = viewModel)

    StateMinuteDialog(viewModel = viewModel, languages = languages, context = context)
}