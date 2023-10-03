package com.example.calendar.ui.screen.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.R

@Composable
fun StartScreen(navController: NavController, viewModel: StartViewModel) {
    Box(modifier = with(Modifier) {
        fillMaxSize().paint(
            painterResource(id = R.drawable.atmosphere_gradient),
            contentScale = ContentScale.FillBounds
        )
    }) {
        Column {

            Spacer(modifier = Modifier.height(15.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                val color = Color.White
                val modifier = Modifier.weight(1F)

                IconButton(onClick = { }, modifier = modifier) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = color
                    )
                }

                Text(text = "September", fontSize = 20.sp, color = color, modifier = modifier)

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Next",
                        tint = color,
                        modifier = modifier
                    )
                }
            }
        }
    }
}