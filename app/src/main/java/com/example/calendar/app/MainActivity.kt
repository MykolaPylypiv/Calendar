package com.example.calendar.app

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import com.example.calendar.R
import com.example.calendar.ui.ApplicationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val imageBackground = R.drawable.atmosphere_gradient
            val backgroundStatusBarColor = Color(0xff23405e)
            val navigationBarColor = Color(0xffe25521)

            val view = LocalView.current

            Background(
                view = view,
                background = backgroundStatusBarColor.toArgb(),
                navigation = navigationBarColor.toArgb()
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

@Composable
private fun Background(view: View, background: Int, navigation: Int) {
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = background
            window.navigationBarColor = navigation
        }
    }
}