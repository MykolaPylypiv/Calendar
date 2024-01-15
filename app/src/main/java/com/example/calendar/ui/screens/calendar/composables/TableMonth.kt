package com.example.calendar.ui.screens.calendar.composables

import androidx.compose.animation.core.snap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calendar.domain.model.DragAnchors
import com.example.calendar.ui.screens.calendar.CalendarViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableMonth(viewModel: CalendarViewModel, navController: NavController) {
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
                        viewModel.previous()
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
                            text = item.date,
                            navController = navController
                        )
                    }, modifier = item.modifier
                ) {
                    Text(
                        text = item.date,
                        fontSize = 20.sp,
                        color = item.color,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}