package com.example.calendar.ui.screen.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.app.languages
import com.example.calendar.ui.screen.add.borderColor

@Composable
fun IconButtonUp(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Up"
        )
    }
}

@Composable
fun IconButtonDown(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Down"
        )
    }
}

@Composable
fun AcceptRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = languages.accept,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Right,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Accept",
        )
    }
}

@Composable
fun StartRow(onClick: () -> Unit, firstText: String, secondText: String) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 56.dp, end = 56.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.2F))
            .border(1.dp, borderColor, CircleShape)
            .clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = firstText,
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = secondText,
            modifier = Modifier
                .height(50.dp)
                .padding(13.dp),
            color = Color.DarkGray
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Open",
            tint = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(8.dp))
    }
}