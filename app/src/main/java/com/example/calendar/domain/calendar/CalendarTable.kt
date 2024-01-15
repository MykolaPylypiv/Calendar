package com.example.calendar.domain.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calendar.data.date.DateTime
import com.example.calendar.data.repository.Repository
import com.example.calendar.domain.model.UiDate
import javax.inject.Inject

interface CalendarTable {

    fun nextMonth()

    fun previousMonth()

    fun selectDate(text: String)

    fun updateDatesList(): List<UiDate>

    class Base @Inject constructor(
        private val dateTime: DateTime, private val constants: Repository
    ) : CalendarTable {

        var visibleNowDayClick = false
        var year = dateTime.year
        var nameMonth = dateTime.month.name

        private var month = dateTime.month

        private var firstDayOfWeek =
            if (dateTime.dayOfWeek == 1) dateTime.day.toInt() % 7 + 7 - dateTime.dayOfWeek
            else (dateTime.day.toInt() % 7 + 7 - dateTime.dayOfWeek) % 7

        private var newCount = 0

        private var monthIndex = dateTime.monthNumber - 1

        private var previousMonthDays =
            dateTime.selectMonth(year = dateTime.year, index = monthIndex - 1).days

        private var previousMonthStartDays = previousMonthDays - (6 - firstDayOfWeek)

        override fun nextMonth() {
            if (monthIndex == 11) {
                year = (year.toInt() + 1).toString()
                changeMonth(-11)
                previousMonthDays = dateTime.selectMonth(year = year, index = 11).days
            } else {
                changeMonth(1)
                previousMonthDays = dateTime.selectMonth(year = year, index = monthIndex - 1).days
            }

            firstDayOfWeek = if (newCount != 0) newCount else 7
            previousMonthStartDays = previousMonthDays - 6 + firstDayOfWeek
        }

        override fun previousMonth() {
            when (monthIndex) {
                0 -> {
                    year = (year.toInt() - 1).toString()
                    changeMonth(11)
                    previousMonthDays = dateTime.selectMonth(year = year, index = 10).days
                }

                1 -> {
                    changeMonth(-1)
                    previousMonthDays = dateTime.selectMonth(year = year, index = 11).days
                }

                else -> {
                    changeMonth(-1)
                    previousMonthDays =
                        dateTime.selectMonth(year = year, index = monthIndex - 1).days
                }
            }

            val term = if (firstDayOfWeek + month.days > 35) 42 else 35
            firstDayOfWeek = -(term - firstDayOfWeek - month.days - 7)
            previousMonthStartDays = previousMonthDays - 6 + firstDayOfWeek
        }

        override fun selectDate(text: String) {
            constants.selectDate.value = "${text}.${monthIndex + 1}.${year}"
        }

        override fun updateDatesList(): List<UiDate> {
            val dates = mutableListOf<UiDate>()
            var dayPointer = -(6 - firstDayOfWeek)

            newCount = 0

            while (dayPointer < 7 - firstDayOfWeek + month.days) {

                dateTime.daysWeek.forEach { day ->

                    val days = month.days
                    val dayNumber = dateTime.daysWeek.indexOf(day)

                    var text = 0
                    var color = Color.White

                    val modifier = if (isToday(count = dayPointer, day = day)) {
                        Modifier
                            .clip(CircleShape)
                            .height(58.dp)
                            .background(Color(0xffff984f))
                    } else if (isEmptyFirstRow(count = dayPointer)) {
                        Modifier.height(0.dp)
                    } else {
                        Modifier.height(60.dp)
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

                    dates.add(
                        UiDate(
                            date = finalText, color = color, modifier = modifier
                        )
                    )
                }
                dayPointer += 7
            }

            return dates.toList()
        }

        private fun isEmptyFirstRow(count: Int) =
            !(count in 1..month.days || count <= 0 && count != -6)

        private fun isToday(count: Int, day: String): Boolean =
            dateTime.day.toInt() == count + dateTime.daysWeek.indexOf(day) && monthIndex == dateTime.monthNumber - 1 && dateTime.year == year

        private fun changeMonth(index: Int) {
            monthIndex += index
            month = dateTime.selectMonth(year = year, index = monthIndex)
            nameMonth = month.name
            visibleNowDayClick = monthIndex != dateTime.monthNumber - 1 || year != dateTime.year
        }
    }
}