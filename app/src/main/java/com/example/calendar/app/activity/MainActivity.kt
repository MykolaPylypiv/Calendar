package com.example.calendar.app.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import com.example.calendar.R
import com.example.calendar.ui.screens.ApplicationScreen
import com.example.calendar.ui.theme.Colors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val imageBackground = R.drawable.atmosphere_gradient

            val view = LocalView.current

            Background(
                view = view,
                background = Colors.backgroundStatusBarColor.toArgb(),
                navigation = Colors.navigationBarColor.toArgb()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painterResource(id = imageBackground),
                        contentScale = ContentScale.FillBounds,
                    )
            ) {
                ApplicationScreen()
            }
        }
    }
}