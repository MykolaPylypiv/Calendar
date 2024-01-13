//package com.example.calendar.domain
//
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.graphics.Color
//import com.example.calendar.domain.model.TextButtonParams
//import javax.inject.Inject
//
//
//class MonthCharacteristics @Inject constructor(private val dateTime: DateTime) {
//    val visibleNowDayClick = mutableStateOf(false)
//
//    val year = mutableStateOf(dateTime.year)
//    val month = mutableStateOf(dateTime.month)
//
//    val nameMonth = mutableStateOf(dateTime.month.name)
//
//    val firstDayOfWeek = mutableIntStateOf(
//        if (dateTime.dayOfWeek == 1) dateTime.day.toInt() % 7 - dateTime.dayOfWeek
//        else (dateTime.day.toInt() % 7 + 7 - dateTime.dayOfWeek) % 7
//    )
//
//    val newCount = mutableIntStateOf(0)
//
//    private var monthIndex = dateTime.monthNumber - 1
//
//    fun isEmptyFirstRow(count: Int) = !(count in 1..month.value.days || count <= 0 && count != -6)
//
//    fun isToday(count: Int, day: String): Boolean =
//        dateTime.day.toInt() == count + dateTime.daysWeek.indexOf(day) && monthIndex == dateTime.monthNumber - 1 && dateTime.year == year.value
//
//
//    fun next() {
//        if (monthIndex == 11) {
//            year.value = (year.value.toInt() + 1).toString()
//            changeMonth(-11)
//            previousMonthDays = dateTime.selectMonth(year = year.value, index = 11).days
//        } else {
//            changeMonth(1)
//            previousMonthDays = dateTime.selectMonth(year = year.value, index = monthIndex - 1).days
//        }
//
//        firstDayOfWeek.intValue = 7 - newCount.intValue
//        previousMonthStartDays = previousMonthDays - 6 + firstDayOfWeek.intValue
//    }
//
//    fun back() {
//        when (monthIndex) {
//            0 -> {
//                year.value = (year.value.toInt() - 1).toString()
//                changeMonth(11)
//                previousMonthDays = dateTime.selectMonth(year = year.value, index = 10).days
//            }
//
//            1 -> {
//                changeMonth(-1)
//                previousMonthDays = dateTime.selectMonth(year = year.value, index = 11).days
//            }
//
//            else -> {
//                changeMonth(-1)
//                previousMonthDays =
//                    dateTime.selectMonth(year = year.value, index = monthIndex - 1).days
//            }
//        }
//
//        val term = if (firstDayOfWeek.intValue + month.value.days > 35) 42 else 35
//        firstDayOfWeek.intValue = -(term - firstDayOfWeek.intValue - month.value.days - 7)
//        previousMonthStartDays = previousMonthDays - 6 + firstDayOfWeek.intValue
//    }
//
//    fun textTable(count: Int, day: String): TextButtonParams {
//        val days = month.value.days
//        val dayNumber = dateTime.daysWeek.indexOf(day)
//
//        var text = ""
//        var color = Color.White
//
//        if (count in 1..days) { // 0 < count < days
//
//            if (count + dayNumber <= days) {
//                text = (count + dayNumber).toString()
//                color = Color.White
//                newCount.intValue += 1
//            } else {
//                text = (dayNumber - newCount.intValue + 1).toString()
//                color = Color.LightGray
//            }
//
//        } else if (count <= 0 && count != -6) { // count < 0
//
//            if (previousMonthStartDays + dayNumber <= previousMonthDays) {
//                text = (previousMonthStartDays + dayNumber).toString()
//                color = Color.LightGray
//            } else {
//                text = (count + 7 - (7 - dayNumber)).toString()
//                color = Color.White
//            }
//        }
//
//        return TextButtonParams(text = text, color = color)
//    }
//
//    private fun changeMonth(index: Int) {
//        monthIndex += index
//        month.value = dateTime.selectMonth(year = year.value, index = monthIndex)
//        nameMonth.value = month.value.name
//        visibleNowDayClick.value =
//            monthIndex != dateTime.monthNumber - 1 || year.value != dateTime.year
//    }
//
//}
