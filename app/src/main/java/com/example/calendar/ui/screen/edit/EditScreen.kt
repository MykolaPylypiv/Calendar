package com.example.calendar.ui.screen.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.calendar.app.Languages
import com.example.calendar.navigation.NavigationTree
import com.example.calendar.ui.screen.add.AcceptButton
import com.example.calendar.ui.screen.add.TopBodyLayer

@Composable
fun EditScreen(navController: NavController, viewModel: EditViewModel) {
    val context = LocalContext.current
    val languages = viewModel.languages
    val borderColor = Color.DarkGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(0.2F)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBodyLayer(text = languages.editTextTopBodyLayer, onClick = {
            navController.navigate(NavigationTree.Tasks.name)
        })

        Spacer(modifier = Modifier.height(35.dp))

        EditEventTextField(
            context = context,
            viewModel = viewModel,
            languages = languages,
            borderColor = borderColor
        )

        Spacer(modifier = Modifier.height(40.dp))

        EditDescriptionTextField(
            context = context,
            viewModel = viewModel,
            languages = languages,
            borderColor = borderColor
        )

        Spacer(modifier = Modifier.weight(1F))

        AcceptButton(
            onClick = {
                viewModel.update(
                    task = viewModel.task, context = context, navController = navController
                )
            }, languages = languages
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDescriptionTextField(
    context: Context, viewModel: EditViewModel, languages: Languages, borderColor: Color
) {
    val shape = RoundedCornerShape(10)
    var description by remember { mutableStateOf(TextFieldValue(viewModel.task.description)) }
    val maxChar = 130

    val leadingIcon: @Composable (() -> Unit) = {
        Icon(
            imageVector = Icons.Default.List,
            contentDescription = "description",
            modifier = Modifier.padding(start = 10.dp)
        )
    }

    val onValueChange: (TextFieldValue) -> Unit = {
        if (it.text.length <= maxChar) {
            description = it
            viewModel.task.description = it.text
        } else Toast.makeText(
            context, languages.toastNameContainHundredTwentyCharacters, Toast.LENGTH_SHORT
        ).show()
    }

    val label: @Composable (() -> Unit) = {
        Text(text = languages.enterYourDescription)
    }

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        unfocusedLeadingIconColor = Color.DarkGray,
        focusedBorderColor = Color.Black,
        focusedLeadingIconColor = Color.Black,
        placeholderColor = Color.DarkGray,
        focusedLabelColor = Color.Black,
        cursorColor = Color.DarkGray,
        unfocusedBorderColor = borderColor,
        containerColor = Color.Gray.copy(0.2F)
    )

    val modifier = Modifier
        .padding(start = 56.dp, end = 56.dp)
        .height(200.dp)

    OutlinedTextField(
        shape = shape,
        value = description,
        leadingIcon = leadingIcon,
        onValueChange = onValueChange,
        label = label,
        placeholder = label,
        colors = colors,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventTextField(
    context: Context, viewModel: EditViewModel, languages: Languages, borderColor: Color
) {
    val shape = CircleShape
    var task by remember { mutableStateOf(TextFieldValue(viewModel.task.name)) }
    val maxChar = 20

    val leadingIcon: @Composable (() -> Unit) = {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "emailIcon",
            modifier = Modifier.padding(start = 10.dp)
        )
    }

    val onValueChange: (TextFieldValue) -> Unit = {
        if (it.text.length <= maxChar) {
            task = it
            viewModel.task.name = it.text
        } else Toast.makeText(
            context, languages.toastNameContainTwentyCharacters, Toast.LENGTH_SHORT
        ).show()
    }

    val label: @Composable (() -> Unit) = { Text(text = languages.enterYourEvent) }

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        unfocusedLeadingIconColor = Color.DarkGray,
        focusedBorderColor = Color.Black,
        focusedLeadingIconColor = Color.Black,
        placeholderColor = Color.DarkGray,
        focusedLabelColor = Color.Black,
        cursorColor = Color.DarkGray,
        unfocusedBorderColor = borderColor,
        containerColor = Color.Gray.copy(0.2F)
    )

    OutlinedTextField(
        shape = shape,
        value = task,
        leadingIcon = leadingIcon,
        onValueChange = onValueChange,
        label = label,
        placeholder = label,
        colors = colors,
        singleLine = true
    )
}