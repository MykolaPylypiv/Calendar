package com.example.calendar.ui.add.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.calendar.app.Languages
import com.example.calendar.ui.add.AddViewModel

@Composable
fun SelectDate(viewModel: AddViewModel, languages: Languages, borderColor: Color) {
    StartRow(
        onClick = { viewModel.stateDateDialog.value = true },
        firstText = languages.date,
        secondText = viewModel.date.value,
        borderColor = borderColor,
    )

    StateDateDialog(languages = languages, viewModel = viewModel)
}

@Composable
fun SelectTime(
    context: Context, viewModel: AddViewModel, languages: Languages, borderColor: Color
) {
    StartRow(
        onClick = { viewModel.stateTimeDialog.value = true },
        firstText = languages.time,
        secondText = viewModel.time.value,
        borderColor = borderColor
    )

    StateTimeDialog(languages = languages, viewModel = viewModel, context = context)
}