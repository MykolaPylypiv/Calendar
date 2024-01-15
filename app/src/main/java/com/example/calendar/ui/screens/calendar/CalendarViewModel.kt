package com.example.calendar.ui.screens.calendar

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.calendar.data.date.DateTime
import com.example.calendar.domain.calendar.CalendarTable
import com.example.calendar.domain.model.UiDate
import com.example.calendar.navigation.NavigationTree
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    val dateTime: DateTime, private val calendarTable: CalendarTable.Base
) : ViewModel() {

    val visibleNowDayClick = mutableStateOf(calendarTable.visibleNowDayClick)

    val year = mutableStateOf(calendarTable.year)
    val nameMonth = mutableStateOf(calendarTable.nameMonth)

    val dates = mutableStateListOf<UiDate>()

    init {
        dates.addAll(calendarTable.updateDatesList())
    }

    fun next() {
        calendarTable.nextMonth()
        updateData()
    }

    fun previous() {
        calendarTable.previousMonth()
        updateData()
    }

    fun nowDayClick(navController: NavController) {
        navController.navigate(NavigationTree.Calendar.name)
    }

    fun dateClick(color: Color, text: String, navController: NavController) {
        if (color == Color.White) {
            navController.navigate(NavigationTree.Tasks.name)
            calendarTable.selectDate(text = text)
        }
    }

    private fun updateData() {
        dates.clear()
        dates.addAll(calendarTable.updateDatesList())
        visibleNowDayClick.value = calendarTable.visibleNowDayClick
        year.value = calendarTable.year
        nameMonth.value = calendarTable.nameMonth
    }
}