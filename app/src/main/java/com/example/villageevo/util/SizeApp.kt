package com.example.villageevo.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SizeApp(
        val screenWidth: Dp,
        val screenHeight: Dp,
        // Grid Building
        val blockSize: Dp,

        // Spacing
        val paddingSmall: Dp,
        val paddingMedium: Dp,
        val paddingLarge: Dp,

        // Components
        val iconSize: Dp,
        val buttonHeight: Dp,
        val cornerRadius: Dp
)

val LocalSizeApp = staticCompositionLocalOf<SizeApp> { error("No SizeApp provided") }

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberSizeApp(): SizeApp {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Logic:
    // Layout is split 4:1 (Main : Sidebar)
    // Main has 4 columns.
    // So 1 block width = (screenWidth * 4/5) / 4 = screenWidth / 5.

    val blockSize = screenWidth / 5

    return remember(configuration) {
        SizeApp(
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                blockSize = blockSize,
                paddingSmall = screenWidth * 0.01f,
                paddingMedium = screenWidth * 0.025f,
                paddingLarge = screenWidth * 0.05f,
                iconSize = blockSize * 0.4f,
                buttonHeight = screenWidth * 0.13f,
                cornerRadius = screenWidth * 0.025f
        )
    }
}
