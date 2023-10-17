package com.example.calendar.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.calendar.R
import com.example.calendar.ui.screen.ApplicationScreen
import com.example.calendar.ui.theme.CalendarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val imageBackground = R.drawable.atmosphere_gradient

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painterResource(id = imageBackground),
                        contentScale = ContentScale.FillBounds,
                    )
            ) {
                CalendarTheme {
                    ApplicationScreen()
                }
            }
        }
    }
}