package com.example.calendar.ui.screens.calendar.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.calendar.ui.screens.calendar.CalendarViewModel

@Composable
fun WeekdayRow(viewModel: CalendarViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        viewModel.dateTime.daysWeek.forEach { text ->

            Text(
                text = text,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center
            )
        }
    }
}