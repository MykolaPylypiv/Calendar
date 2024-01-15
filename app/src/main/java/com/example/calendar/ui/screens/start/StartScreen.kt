package com.example.calendar.ui.screens.start

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.domain.model.DragAnchors
import com.example.calendar.navigation.NavigationTree
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// Екран на якому знаходиться календар
@Composable
fun StartScreen(navController: NavController, viewModel: StartViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(15.dp))

        StartTopBody(viewModel = viewModel)

        Spacer(modifier = Modifier.height(25.dp))

        WeekdayRow(viewModel = viewModel)

        Spacer(modifier = Modifier.height(30.dp))

        TableMonth(viewModel = viewModel, navController = navController)

        Spacer(modifier = Modifier.weight(1F))

        StartLowLayer(
            navController = navController, viewModel = viewModel, day = viewModel.dateTime.day
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun StartTopBody(viewModel: StartViewModel) {
    val color = Color.White

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        var visible by remember {
            mutableStateOf(true)
        }

        IconButton(onClick = {
            viewModel.back()
            visible = !visible
        }, modifier = Modifier.weight(1F)) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = color
            )
        }

        AnimatedContent(
            transitionSpec = {
                (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
            }, targetState = viewModel.nameMonth.value, label = ""
        ) { targetCount ->
            Text(
                text = targetCount + " " + viewModel.year.value,
                fontSize = 24.sp,
                color = color,
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center
            )
        }

        IconButton(onClick = {
            viewModel.next()
            visible = !visible
        }, modifier = Modifier.weight(1F)) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = color
            )
        }
    }
}

@Composable
fun WeekdayRow(viewModel: StartViewModel) {
    val color = Color.White

    Row(modifier = Modifier.fillMaxWidth()) {
        viewModel.dateTime.daysWeek.forEach { text ->

            Text(
                text = text,
                fontSize = 20.sp,
                color = color,
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableMonth(viewModel: StartViewModel, navController: NavController) {
    val density = LocalDensity.current

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 200.dp.toPx() } },
            animationSpec = snap(),
        ).apply {
            updateAnchors(

                DraggableAnchors {
                    DragAnchors.Left at 100f
                    DragAnchors.Center at 0f
                    DragAnchors.Right at -100f
                })
        }
    }

    val scope = rememberCoroutineScope()

    if (state.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                when (state.currentValue) {
                    DragAnchors.Right -> {
                        viewModel.next()
                    }

                    DragAnchors.Left -> {
                        viewModel.back()
                    }

                    else -> {
                        return@onDispose
                    }
                }

                scope.launch {
                    state.animateTo(DragAnchors.Center)
                }
            }
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .offset {
                IntOffset(
                    x = state
                        .requireOffset()
                        .roundToInt(),
                    y = 0,
                )
            }
            .anchoredDraggable(state, Orientation.Horizontal)) {

        viewModel.dates.forEach { item ->
            item {
                TextButton(
                    onClick = {
                        viewModel.dateClick(
                            color = item.color,
                            text = item.date.toString(),
                            navController = navController
                        )
                    }, modifier = item.modifier
                ) {
                    Text(
                        text = item.date.toString(),
                        fontSize = 20.sp,
                        color = item.color,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun StartLowLayer(
    navController: NavController,
    day: String,
    viewModel: StartViewModel,
) {
    Row {
        Spacer(modifier = Modifier.weight(1F))

        AnimatedVisibility(visible = viewModel.visibleNowDayClick.value) {
            TextButton(
                onClick = {
                    viewModel.nowDayClick(navController = navController)
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = Color(0xff23405e).copy(0.85F)
                ),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(68.dp),
            ) {
                Text(text = day, fontSize = 24.sp, maxLines = 1)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            onClick = { navController.navigate(NavigationTree.Add.name) },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Gray.copy(0.5f))
                .border(2.dp, Color.Gray.copy(0.2F), CircleShape)
                .size(68.dp),
        ) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}