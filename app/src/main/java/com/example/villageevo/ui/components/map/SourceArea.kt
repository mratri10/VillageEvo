package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.villageevo.R
import com.example.villageevo.util.LocalSizeApp

@Composable
fun SourceArea(
    modifier: Modifier = Modifier,
    sum: Int = 0,
    imageSource:Int,
    widthSource: Float,
    heightSource: Float,
    iconMap:Int,
    worker: Int = 0, id: Int = 0
) {
    val sizeApp = LocalSizeApp.current
    Column(modifier = modifier.fillMaxSize()) {
        Box(
                modifier = Modifier.weight(1f).fillMaxSize(),
        ) {
            Image(
                    painter = painterResource(id = imageSource),
                    contentDescription = null,
                    modifier =
                            Modifier.fillMaxWidth(widthSource)
                                    .fillMaxHeight(heightSource)
                                    .align(Alignment.BottomStart),
            )
        }
        // 3. Label Resource & Worker (Disederhanakan)
        Row(
                modifier = Modifier.fillMaxWidth().padding(sizeApp.paddingSmall),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ResourceBadge(label = "$sum", iconId = iconMap, color = Color.Cyan)
            Box(
                modifier =
                    Modifier.background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(sizeApp.paddingSmall)
                    )
                        .padding(sizeApp.paddingSmall/2)
            ) {
                Text(
                    id.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White)
                )
            }
            ResourceBadge(label = "$worker", iconId = R.drawable.farmer, color = Color.Green)
        }
    }
}

@Composable
private fun ResourceBadge(label: String, iconId: Int, color: Color) {
    val sizeApp = LocalSizeApp.current
    Row(
            modifier =
                    Modifier.background(
                                    color.copy(alpha = 0.8f),
                                    RoundedCornerShape(sizeApp.paddingSmall)
                            )
                            .padding(sizeApp.paddingSmall / 2),
            verticalAlignment = Alignment.CenterVertically
    ) {
        if (color == Color.Green) { // Logika posisi ikon (kiri/kanan)
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Black)
            Image(painterResource(iconId), null, Modifier.size(sizeApp.iconSize / 4))
        } else {
            Image(painterResource(iconId), null, Modifier.size(sizeApp.iconSize / 4))
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Black)
        }
    }
}
