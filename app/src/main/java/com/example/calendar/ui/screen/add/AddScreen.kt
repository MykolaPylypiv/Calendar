package com.example.calendar.ui.screen.add

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.calendar.app.languages
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.screen.add.components.DescriptionTextField
import com.example.calendar.ui.screen.add.components.EventTextField
import com.example.calendar.ui.screen.add.components.SelectDate
import com.example.calendar.ui.screen.add.components.SelectTime

val borderColor = Color.DarkGray

@Composable
fun AddScreen(navController: NavController, viewModel: AddViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.2F)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBodyLayer(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        EventTextField(context = context, viewModel = viewModel)

        Spacer(modifier = Modifier.height(40.dp))

        RepeatTime(viewModel = viewModel)

        Spacer(modifier = Modifier.height(35.dp))

        SelectDate(viewModel = viewModel)

        Spacer(modifier = Modifier.height(35.dp))

        SelectTime(context = context, viewModel = viewModel)

        Spacer(modifier = Modifier.height(35.dp))

        DescriptionTextField(context = context, viewModel = viewModel)

        Spacer(modifier = Modifier.weight(1F))

        AcceptButton(navController = navController, viewModel = viewModel)

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun TopBodyLayer(navController: NavController) {
    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { navController.navigate(NavigationTree.Start.name) }) {
            val icon = Icons.Filled.KeyboardArrowLeft
            val description = "Back"
            val tint = Color.Black

            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = tint,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(24.dp),
            )
        }

        Text(
            text = languages.addNewEvent,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 22.sp
        )
    }
}

@Composable
fun RepeatTime(viewModel: AddViewModel) {
    val stateDialog = remember { mutableStateOf(false) }
    val selectRepeat = remember { mutableStateOf(languages.oneTime) }

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
            .border(1.dp, borderColor, CircleShape)
            .clickable { stateDialog.value = true }, verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = "Date",
            tint = Color.DarkGray,
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            text = languages.repeat,
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = selectRepeat.value,
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
                viewModel.listRepeat.forEach { text ->
                    item {
                        RepeatDialogItem(text = text, onClick = {
                            selectRepeat.value = text
                            viewModel.newTask.repeat = text
                            stateDialog.value = false
                        })
                    }
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

@Composable
fun AcceptButton(navController: NavController, viewModel: AddViewModel) {
    TextButton(
        onClick = {
            navController.navigate(NavigationTree.Start.name)
            viewModel.insert(viewModel.newTask)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
    ) {
        Text(text = languages.accept, color = Color.Black, fontSize = 24.sp)
    }
}