package com.example.calendar.ui.screen.add

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.calendar.app.languages

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTextField(context: Context) {
    var task by remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 20

    OutlinedTextField(shape = CircleShape,
        value = task,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "emailIcon",
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        onValueChange = {
            if (it.text.length <= maxChar) task = it else Toast.makeText(
                context, languages.toastNameContainTwentyCharacters, Toast.LENGTH_SHORT
            ).show()
        },
        label = { Text(text = languages.enterYourEvent) },
        placeholder = { Text(text = languages.enterYourEvent) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLeadingIconColor = Color.DarkGray,
            focusedBorderColor = Color.Black,
            focusedLeadingIconColor = Color.Black,
            placeholderColor = Color.DarkGray,
            focusedLabelColor = Color.Black,
            cursorColor = Color.DarkGray,
            unfocusedBorderColor = borderColor,
            containerColor = Color.Gray.copy(0.2F),
        ),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionTextField(context: Context) {
    var description by remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 130

    OutlinedTextField(
        shape = RoundedCornerShape(10),
        value = description,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "description",
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        onValueChange = {
            if (it.text.length <= maxChar) description = it else Toast.makeText(
                context, languages.toastNameContainHundredTwentyCharacters, Toast.LENGTH_SHORT
            ).show()
        },
        label = {
            Text(text = languages.enterYourDescription)
        },
        placeholder = { Text(text = languages.enterYourDescription) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLeadingIconColor = Color.DarkGray,
            focusedBorderColor = Color.Black,
            focusedLeadingIconColor = Color.Black,
            placeholderColor = Color.DarkGray,
            focusedLabelColor = Color.Black,
            cursorColor = Color.DarkGray,
            unfocusedBorderColor = borderColor,
            containerColor = Color.Gray.copy(0.2F)
        ),
        modifier = Modifier
            .padding(start = 56.dp, end = 56.dp)
            .height(200.dp),
    )
}