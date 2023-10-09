package com.example.calendar.app

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.calendar.R
import dagger.hilt.android.HiltAndroidApp

val imageBackground = R.drawable.atmosphere_gradient

@HiltAndroidApp
class App: Application() {
}