package com.example.calendar.ui.screen.add

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.calendar.domain.model.Month
import com.example.calendar.navigation.NavigationTree

@Composable
fun AddScreen(navController: NavController, viewModel: AddViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.2F)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBodyLayer(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        EventTextField()

        Spacer(modifier = Modifier.height(35.dp))

        RepeatTime()

        Spacer(modifier = Modifier.height(35.dp))

        SelectDate()

        Spacer(modifier = Modifier.height(35.dp))

        SelectTime()

        Spacer(modifier = Modifier.weight(1F))

        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 56.dp, end = 56.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(0.2F))
        ) {
            Text(text = "Accept", color = Color.Black, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun TopBodyLayer(navController: NavController) {
    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { navController.navigate(NavigationTree.Start.name) }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(24.dp),
            )
        }

        Text(
            text = "Add a new event",
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 22.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTextField() {
    var task by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        shape = CircleShape,
        value = task,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "emailIcon",
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        onValueChange = {
            task = it
        },
        label = { Text(text = "Enter your event") },
        placeholder = { Text(text = "Enter your event") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLeadingIconColor = Color.DarkGray,
            focusedBorderColor = Color.Black,
            focusedLeadingIconColor = Color.Black,
            placeholderColor = Color.DarkGray,
            focusedLabelColor = Color.Black,
            cursorColor = Color.DarkGray,
            unfocusedBorderColor = Color.Gray.copy(0.2F),
            containerColor = Color.Gray.copy(0.2F)
        ),
    )
}

@Composable
fun RepeatTime() {
    val stateDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
            .clickable { stateDialog.value = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = "Date",
            tint = Color.DarkGray,
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            text = "Repeat",
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = "One time",
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

    if (stateDialog.value) {
        Dialog(onDismissRequest = { stateDialog.value = false }) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(5))
                    .background(Color.White)
                    .fillMaxWidth()
                    .border(2.dp, Color.Gray),
                verticalArrangement = Arrangement.Bottom
            ) {
                item {
                    RepeatDialogItem(text = "One time", onClick = { stateDialog.value = false })
                }

                item {
                    RepeatDialogItem(text = "Every day", onClick = { stateDialog.value = false })
                }

                item {
                    RepeatDialogItem(text = "Every week", onClick = { stateDialog.value = false })
                }

                item {
                    RepeatDialogItem(text = "Every two weeks",
                        onClick = { stateDialog.value = false })
                }

                item {
                    RepeatDialogItem(text = "Every month", onClick = { stateDialog.value = false })
                }

                item {
                    RepeatDialogItem(text = "Every year", onClick = { stateDialog.value = false })
                }
            }
        }
    }
}

@Composable
fun RepeatDialogItem(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxSize()
                .height(48.dp)
                .wrapContentSize(Alignment.Center),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectTime() {
    val stateDialog = remember { mutableStateOf(false) }
    val stateHourDialog = remember { mutableStateOf(false) }
    val stateMinutesDialog = remember { mutableStateOf(false) }

    val hour = remember { mutableStateOf(0) }
    val minute = remember { mutableStateOf(0) }

    val hours =
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)

    val minutes = listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55)

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
            .clickable { stateDialog.value = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Time",
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = "23:21", modifier = Modifier
                .height(50.dp)
                .padding(13.dp), color = Color.DarkGray
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Open",
            tint = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(8.dp))

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
                                text = "Hour",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            IconButton(onClick = {
                                hour.value = if (hour.value == 23) 0 else hour.value + 1
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "Up"
                                )
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

                            IconButton(onClick = {
                                hour.value = if (hour.value == 0) 23 else hour.value - 1
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Down"
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
                                text = "Minute",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            IconButton(onClick = {
                                minute.value = if (minute.value == 55) 0 else minute.value + 5
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "Up"
                                )
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

                            IconButton(onClick = {
                                minute.value = if (minute.value == 0) 55 else minute.value - 5
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Down"
                                )
                            }

                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Spacer(modifier = Modifier.width(15.dp))
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { stateDialog.value = false },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Accept",
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
                            hours.forEach {
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
                            minutes.forEach {
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
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectDate() {
    val stateDialog = remember { mutableStateOf(false) }
    val stateMonthDialog = remember { mutableStateOf(false) }
    val stateDayDialog = remember { mutableStateOf(false) }

    val day = remember { mutableStateOf(0) }
    val moon = remember { mutableStateOf(0) }

    val days = listOf("Mn", "Ts", "Wd", "Th", "Fr", "St", "Sn")

    val month = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
            .clickable { stateDialog.value = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Date",
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = "09.10", modifier = Modifier
                .height(50.dp)
                .padding(13.dp), color = Color.DarkGray
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Open",
            tint = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(8.dp))

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
                                text = "Day",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            IconButton(onClick = {
                                day.value = if (day.value == 6) 0 else day.value + 1
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "Up"
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            AnimatedContent(
                                targetState = days[day.value], label = ""
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

                            IconButton(onClick = {
                                day.value = if (day.value == 0) 6 else day.value - 1
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Down"
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
                                text = "Moon",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            IconButton(onClick = {
                                moon.value = if (moon.value == 11) 0 else moon.value + 1
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "Up"
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            AnimatedContent(
                                targetState = month[moon.value], label = ""
                            ) { targetCount ->
                                Text(
                                    text = targetCount,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { stateDayDialog.value = true },
                                    color = Color.Black,
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            IconButton(onClick = {
                                moon.value = if (moon.value == 0) 11 else moon.value - 1
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Down"
                                )
                            }

                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Spacer(modifier = Modifier.width(15.dp))
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { stateDialog.value = false },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Accept",
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
                            days.forEach {
                                item {
                                    Text(
                                        text = it,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .clickable {
                                                stateMonthDialog.value = false
                                                day.value = days.indexOf(it)
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
                            month.forEach {
                                item {
                                    Text(
                                        text = it,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .clickable {
                                                stateDayDialog.value = false
                                                moon.value = month.indexOf(it)
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
}