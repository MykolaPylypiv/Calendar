package com.example.calendar.data.repository

import androidx.compose.runtime.mutableStateOf
import com.example.calendar.domain.model.Task

object Repository {
    val selectDate = mutableStateOf("")

    val selectTask = mutableStateOf(Task())
}