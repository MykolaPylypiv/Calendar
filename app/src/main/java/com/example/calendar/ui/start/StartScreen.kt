package com.example.calendar.ui.start

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.navigation.NavigationTree
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
            navController = navController, viewModel = viewModel, day = viewModel.calendar.day
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun StartTopBody(viewModel: StartViewModel) {
    val color = Color.White

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        val modifier = Modifier.weight(1F)

        IconButton(onClick = { viewModel.back() }, modifier = Modifier.weight(0.5F)) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = color
            )
        }

        Text(
            text = viewModel.nameMonth.value + " " + viewModel.year.value,
            fontSize = 24.sp,
            color = color,
            modifier = modifier,
            textAlign = TextAlign.Center
        )

        IconButton(onClick = { viewModel.next() }, modifier = Modifier.weight(0.5F)) {
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
        viewModel.calendar.daysWeek.forEach { text ->

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
    var count = -(6 - viewModel.pointer.intValue)
    val density = LocalDensity.current

    val state = remember {
        AnchoredDraggableState(
            // 2
            initialValue = DragAnchors.Center,
            // 3
            positionalThreshold = { distance: Float -> distance * 0.5f },
            // 4
            velocityThreshold = { with(density) { 200.dp.toPx() } },
            // 5
            animationSpec = tween(easing = LinearEasing),
        ).apply {
            // 6
            updateAnchors(
                // 7
                DraggableAnchors {
                    DragAnchors.Left at 400f
                    DragAnchors.Center at 0f
                    DragAnchors.Right at -400f
                })
        }
    }

    val scope = rememberCoroutineScope()

    if (state.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                scope.launch {
                    state.animateTo(DragAnchors.Center)
                }

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
            }
        }
    }

    while (count < 7 - viewModel.pointer.intValue + viewModel.month.value.days) {
        viewModel.newCount.intValue = 0

        if (count != -6) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                // 1
                .offset {
                    IntOffset(
                        // 2
                        x = state
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                // 3
                .anchoredDraggable(state, Orientation.Horizontal)

            ) {
                viewModel.calendar.daysWeek.forEach { day ->
                    val text = mutableStateOf("")
                    val textColor = mutableStateOf(Color.White)

                    val modifier =
                        if (viewModel.isToday(count = count, day = day)) Modifier
                            .weight(1F)
                            .clip(CircleShape)
                            .height(58.dp)
                            .background(Color(0xffff984f))
                        else if (viewModel.isEmptyFirstRow(count = count)) Modifier.height(0.dp)
                        else Modifier
                            .weight(1F)
                            .height(60.dp)

                    val textButtonParams = viewModel.textTable(count = count, day = day)

                    text.value = textButtonParams.text
                    textColor.value = textButtonParams.color

                    TextButton(
                        onClick = {
                            viewModel.dateClick(
                                color = textColor.value,
                                text = text.value,
                                navController = navController
                            )
                        }, modifier = modifier
                    ) {

                        Text(
                            text = text.value,
                            fontSize = 20.sp,
                            color = textColor.value,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        count += 7
    }
}

@Composable
fun StartLowLayer(navController: NavController, day: String, viewModel: StartViewModel) {
    Row {
        Spacer(modifier = Modifier.weight(1F))

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

enum class DragAnchors(val fraction: Float) {
    Left(0f), Center(0.5f), Right(1f),
}