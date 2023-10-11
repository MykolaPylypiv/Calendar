package com.example.calendar.ui.screen.add

import androidx.lifecycle.ViewModel
import com.example.calendar.domain.Calendar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(calendar: Calendar): ViewModel() {

    val hours =
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)

    val minutes = listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55)

    val days = calendar.daysWeek
    val months = calendar.listOfMonth
}