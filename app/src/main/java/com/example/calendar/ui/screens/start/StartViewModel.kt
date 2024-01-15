package com.example.calendar.ui.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.calendar.data.date.DateTime
import com.example.calendar.data.repository.Repository
import com.example.calendar.navigation.NavigationTree
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    val dateTime: DateTime, private val constants: Repository,
) : ViewModel() {

    val visibleNowDayClick = mutableStateOf(false)

    val year = mutableStateOf(dateTime.year)
    val nameMonth = mutableStateOf(dateTime.month.name)

    val dates = mutableStateListOf<ItemDate>()

    private var month = dateTime.month

    private var firstDayOfWeek =
        if (dateTime.dayOfWeek == 1) dateTime.day.toInt() % 7 + 7 - dateTime.dayOfWeek
        else (dateTime.day.toInt() % 7 + 7 - dateTime.dayOfWeek) % 7

    private var newCount = 0

    private var monthIndex = dateTime.monthNumber - 1

    private var previousMonthDays =
        dateTime.selectMonth(year = year.value, index = monthIndex - 1).days

    private var previousMonthStartDays = previousMonthDays - (6 - firstDayOfWeek)

    init {
        textTable()
    }

    private fun dayPointer() = -(6 - firstDayOfWeek)

    fun nowDayClick(navController: NavController) {
        if (monthIndex != dateTime.monthNumber - 1 || year.value != dateTime.year) navController.navigate(
            NavigationTree.Start.name
        )
    }

    private fun textTable() {
        dates.clear()
        newCount = 0
        var dayPointer =  dayPointer()

        while (dayPointer < 7 - firstDayOfWeek + month.days) {

                dateTime.daysWeek.forEach { day ->

                    val days = month.days
                    val dayNumber = dateTime.daysWeek.indexOf(day)

                    var text = 0
                    var color = Color.White

                    val modifier = if (isToday(count = dayPointer(), day = day)) {
                        Modifier
                            .clip(CircleShape)
                            .height(58.dp)
                            .background(Color(0xffff984f))
                    } else if (isEmptyFirstRow(count = dayPointer())) {
                        Modifier.height(0.dp)
                    } else {
                        Modifier
                            .height(60.dp)
                    }

                    if (dayPointer in 0..days) { // 0 <= count <= days

                        if (dayPointer + dayNumber <= days) {
                            text = dayPointer + dayNumber
                            color = Color.White

                        } else if (dayPointer + dayNumber > days) {
                            newCount++

                            text = newCount
                            color = Color.LightGray
                        }
                    } else if (dayPointer <= 0) { // count < 0

                        if (previousMonthStartDays + dayNumber <= previousMonthDays) {
                            text = previousMonthStartDays + dayNumber
                            color = Color.LightGray

                        } else {
                            text = dayPointer + 7 - (7 - dayNumber)
                            color = Color.White
                        }
                    }

                    val finalText = if (text != 0) text.toString() else ""

                    dates.add(ItemDate(
                        date = finalText, color = color, modifier = modifier)
                    )
            }
            dayPointer += 7
        }
    }

    fun dateClick(color: Color, text: String, navController: NavController) {
        if (color == Color.White) {
            navController.navigate(NavigationTree.Tasks.name)
            constants.selectDate.value = "${text}.${monthIndex + 1}.${year.value}"
        }
    }

    private fun isEmptyFirstRow(count: Int) =
        !(count in 1..month.days || count <= 0 && count != -6)

    private fun isToday(count: Int, day: String): Boolean =
        dateTime.day.toInt() == count + dateTime.daysWeek.indexOf(day) && monthIndex == dateTime.monthNumber - 1 && dateTime.year == year.value

    fun next() {
        if (monthIndex == 11) {
            year.value = (year.value.toInt() + 1).toString()
            changeMonth(-11)
            previousMonthDays = dateTime.selectMonth(year = year.value, index = 11).days
        } else {
            changeMonth(1)
            previousMonthDays =
                dateTime.selectMonth(year = year.value, index = monthIndex - 1).days
        }

        firstDayOfWeek = if (newCount != 0) newCount else 7
        previousMonthStartDays = previousMonthDays - 6 + firstDayOfWeek

        textTable()
    }

    fun back() {
        when (monthIndex) {
            0 -> {
                year.value = (year.value.toInt() - 1).toString()
                changeMonth(11)
                previousMonthDays = dateTime.selectMonth(year = year.value, index = 10).days
            }

            1 -> {
                changeMonth(-1)
                previousMonthDays = dateTime.selectMonth(year = year.value, index = 11).days
            }

            else -> {
                changeMonth(-1)
                previousMonthDays =
                    dateTime.selectMonth(year = year.value, index = monthIndex - 1).days
            }
        }

        val term = if (firstDayOfWeek + month.days > 35) 42 else 35
        firstDayOfWeek = -(term - firstDayOfWeek - month.days - 7)
        previousMonthStartDays = previousMonthDays - 6 + firstDayOfWeek

        textTable()
    }

    private fun changeMonth(index: Int) {
        monthIndex += index
        month = dateTime.selectMonth(year = year.value, index = monthIndex)
        nameMonth.value = month.name
        visibleNowDayClick.value =
            monthIndex != dateTime.monthNumber - 1 || year.value != dateTime.year
    }
}

data class ItemDate(
    val date: String,
    val color: Color,
    val modifier: Modifier
)