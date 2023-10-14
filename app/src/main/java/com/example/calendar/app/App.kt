package com.example.calendar.app

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.room.Insert
import com.example.calendar.R
import com.example.calendar.data.db.AppDatabase
import com.example.calendar.domain.model.Task
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

val date = mutableStateOf("")
val selectTask = mutableStateOf(Task())

@HiltAndroidApp
class App: Application()
