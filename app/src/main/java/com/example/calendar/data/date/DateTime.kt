package com.example.calendar.data.date

import android.annotation.SuppressLint
import com.example.calendar.domain.model.Month
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

// Клас для отримання та опрацювання дати та часу
class DateTime @Inject constructor() {
    val daysWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

    val listOfMonth = listOf(
        Month(name = "January", days = 31),
        Month(name = "February", days = 28),
        Month(name = "March", days = 31),
        Month(name = "April", days = 30),
        Month(name = "May", days = 31),
        Month(name = "June", days = 30),
        Month(name = "July", days = 31),
        Month(name = "August", days = 31),
        Month(name = "September", days = 30),
        Month(name = "October", days = 31),
        Month(name = "November", days = 30),
        Month(name = "December", days = 31)
    )

    @SuppressLint("SimpleDateFormat")
    val nowDate = SimpleDateFormat("u/dd/M/yyyy/HH/mm").format(Date()).split("/")

    val dayOfWeek: Int = nowDate[0].toInt()
    val day: String = nowDate[1]
    val monthNumber: Int = nowDate[2].toInt()
    val year: String = nowDate[3]

    val hour: String = nowDate[4]
    val minute: String = nowDate[5]

    val month: Month = selectMonth(year = year, index = monthNumber - 1)

    // Повертає назву місяця і кількість днів
    fun selectMonth(year: String, index: Int): Month =
        if (year.toInt() % 4 == 0 && index == 1) Month(name = "February", days = 29)
        else if (index == -1) listOfMonth[11]
        else listOfMonth[index]
}
