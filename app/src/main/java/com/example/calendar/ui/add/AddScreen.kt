package com.example.calendar.ui.add

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import com.example.calendar.app.Languages
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.add.components.DescriptionTextField
import com.example.calendar.ui.add.components.EventTextField
import com.example.calendar.ui.add.components.DateTitle
import com.example.calendar.ui.add.components.SelectTime

@Composable
fun AddScreen(navController: NavController, viewModel: AddViewModel) {
    val context = LocalContext.current
    val languages = viewModel.languages
    val borderColor = Color.DarkGray

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.2F)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TopBodyLayer(text = languages.addNewEvent,
                onClick = { navController.navigate(NavigationTree.Start.name) })

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            EventTextField(
                context = context,
                viewModel = viewModel,
                languages = languages,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            RepeatTime(viewModel = viewModel, languages = languages, borderColor = borderColor)

            Spacer(modifier = Modifier.height(35.dp))
        }

        item {
            DateTitle(viewModel = viewModel, languages = languages, borderColor = borderColor)

            Spacer(modifier = Modifier.height(35.dp))
        }

        item {
            SelectTime(
                context = context,
                viewModel = viewModel,
                languages = languages,
                borderColor = borderColor
            )

            Spacer(modifier = Modifier.height(35.dp))

        }

        item {
            DescriptionTextField(
                context = context,
                viewModel = viewModel,
                languages = languages,
                borderColor = borderColor
            )
        }

        item {
            Spacer(modifier = Modifier.height(125.dp))
        }

        item {
            AcceptButton(
                onClick = {
                    viewModel.insert(
                        task = viewModel.newTask, context = context, navController = navController
                    )
                }, languages = languages
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun TopBodyLayer(text: String, onClick: () -> Unit) {
    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = onClick) {
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
            text = text,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 22.sp
        )
    }
}

@Composable
fun RepeatTime(viewModel: AddViewModel, languages: Languages, borderColor: Color) {
    val stateDialog = remember { mutableStateOf(false) }
    val selectRepeat = remember { mutableStateOf(languages.oneTime) }
    val icon = viewModel.icon(stateDialog.value)

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
            .border(1.dp, borderColor, CircleShape)
            .clickable { stateDialog.value = true },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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
                .padding(start = 10.dp, top = 14.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = selectRepeat.value,
            modifier = Modifier
                .height(50.dp)
                .padding(14.dp),
            color = Color.DarkGray
        )

        AnimatedContent(
            transitionSpec = {
                (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
            }, targetState = icon, label = ""
        ) { target ->
            Icon(
                imageVector = target,
                contentDescription = "Open",
                tint = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
    }

    if (stateDialog.value) {
        Dialog(onDismissRequest = { stateDialog.value = false }) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .background(Color.LightGray)
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
fun AcceptButton(
    onClick: () -> Unit, languages: Languages
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
    ) {
        Text(text = languages.accept, color = Color.Black, fontSize = 24.sp)
    }
}