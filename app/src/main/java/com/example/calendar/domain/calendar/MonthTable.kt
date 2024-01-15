package com.example.calendar.domain.calendar

import com.example.calendar.data.date.DateTime
import com.example.calendar.data.repository.Repository
import javax.inject.Inject

interface MonthTable {

    class Base @Inject constructor(private val dateTime: DateTime, private val constants: Repository) {

    }
}