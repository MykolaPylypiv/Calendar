package com.example.calendar.app.activity

import android.app.Activity
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

@Composable
fun Background(view: View, background: Int, navigation: Int) {
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = background
            window.navigationBarColor = navigation
        }
    }
}