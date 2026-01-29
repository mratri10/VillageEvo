package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.villageevo.R

@Composable
fun SourceArea(
    modifier: Modifier = Modifier,
    sum: Int = 0,
    worker: Int = 0,
    image:Int=0,
    images:List<Int> = emptyList(),
    spaceBy:Int=0,
    sizeImage:Int=22
) {
    val visualTreeCount = remember(sum) { sum.coerceAtMost(3) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp)) // Menjaga agar pohon tidak keluar batas petak
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier.fillMaxSize(),
            alignment = Alignment.Center
        )
        // 3. Label Resource & Worker (Disederhanakan)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ResourceBadge1(label = "$sum", iconId = R.drawable.tree, color = Color.Cyan)
            ResourceBadge1(label = "$worker", iconId = R.drawable.farmer, color = Color.Green)
        }
    }
}

@Composable
fun ResourceBadge1(label: String, iconId: Int, color: Color) {
    Row(
        modifier = Modifier
            .background(color.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (color == Color.Green) { // Logika posisi ikon (kiri/kanan)
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Black)
            Image(painterResource(iconId), null, Modifier.size(12.dp))
        } else {
            Image(painterResource(iconId), null, Modifier.size(12.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Black)
        }
    }
}