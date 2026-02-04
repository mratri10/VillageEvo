package com.example.villageevo.ui.components.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.util.LocalSizeApp

@Composable
fun IndicatorMap(userMeta: MapMetaDataEntity, countNpc: Int) {
    val sizeApp = LocalSizeApp.current
    Column {
        Text(userMeta.title, color = Color.White, textAlign = TextAlign.Center)
        Box() {
            LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(sizeApp.paddingSmall),
                    horizontalArrangement = Arrangement.spacedBy(sizeApp.paddingSmall),
                    verticalArrangement = Arrangement.spacedBy(sizeApp.paddingSmall)
            ) {
                items(4) { index ->
                    Row(
                            verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(index.toString())
                        Text(
                                text = countNpc.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                        )
                    }
                }
            }
        }
        Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(sizeApp.paddingMedium),
                colors =
                        ButtonDefaults.buttonColors(
                                containerColor = Color.Cyan,
                                contentColor = Color.Black
                        ),
                shape = RoundedCornerShape(size = sizeApp.paddingSmall)
        ) { Text("Turn") }
    }
}
