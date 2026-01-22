package com.example.villageevo.ui.components.diamondcard

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class PressedArea { NONE, TOP, BOTTOM }

@Composable
fun DiamondSplitCard(
    modifier: Modifier = Modifier,
    onTopClicked: () -> Unit,
    onBottomClicked: () -> Unit,
    // Gunakan awalan 'base' atau 'default' untuk membedakan dengan variabel animasi
    baseTopColor: Color = Color(0xFFE17055),
    baseBottomColor: Color = Color(0xFF74B9FF),
    widgetTop: @Composable () -> Unit,
    widgetBottom: @Composable () -> Unit,
    widgetTopYPosition:Int=-12,
    widgetBottomYPosition:Int=12,
    lineCard:Int=4,
) {
    val dividerColor = Color(0xFF2d3436)
    val borderColor = Color(0xFF2d3436)

    var pressedArea by remember { mutableStateOf(PressedArea.NONE) }

    // Menggunakan nama yang berbeda (animatedTopColor) untuk menghindari Redundant Assignment
    val animatedTopColor by animateColorAsState(
        targetValue = if (pressedArea == PressedArea.TOP) baseTopColor.copy(alpha = 0.5f) else baseTopColor,
        label = "TopAnimation"
    )

    val animatedBottomColor by animateColorAsState(
        targetValue = if (pressedArea == PressedArea.BOTTOM) baseBottomColor.copy(alpha = 0.5f) else baseBottomColor,
        label = "BottomAnimation"
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(DiamondShape())
            .border(lineCard.dp, borderColor, DiamondShape())
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        val area = if (offset.y < size.height / 2) PressedArea.TOP else PressedArea.BOTTOM
                        pressedArea = area
                        val startTime = System.currentTimeMillis()
                        try {
                            awaitRelease()
                        } finally {
                            val elapsed = System.currentTimeMillis() -startTime
                            val minDisplayTime = 100L
                            if (elapsed < minDisplayTime)delay(minDisplayTime - elapsed)
                            pressedArea = PressedArea.NONE
                        }
                    },
                    onTap = { offset ->
                        if (offset.y < size.height / 2) onTopClicked() else onBottomClicked()
                    }
                )
            },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // BAGIAN ATAS
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(animatedTopColor),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(y = (widgetTopYPosition).dp)
                ) {
                    widgetTop()
                }
            }

            // GARIS TENGAH
            Box(modifier = Modifier.fillMaxWidth().height(lineCard.dp).background(dividerColor))

            // BAGIAN BAWAH
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(animatedBottomColor),
                contentAlignment = Alignment.Center, // Gunakan alignBottom di sini
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(y = widgetBottomYPosition.dp) // Sedikit turun ke area segitiga
                ) {
                    widgetBottom()
                }
            }
        }
    }
}