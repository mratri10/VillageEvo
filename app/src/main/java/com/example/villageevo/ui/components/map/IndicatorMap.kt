package com.example.villageevo.ui.components.map

import androidx.compose.foundation.background
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
import com.example.villageevo.domain.map.PotentialData
import com.example.villageevo.domain.npc.TotalNpcAssign
import com.example.villageevo.util.LocalSizeApp

@Composable
fun IndicatorMap(
    userMeta: MapMetaDataEntity,
    listTotalNpc: List<TotalNpcAssign>,
    potentialData: List<PotentialData>,
    onClick: () -> Unit
) {
    val sizeApp = LocalSizeApp.current
    Column {
        Text(userMeta.title, color = Color.White, textAlign = TextAlign.Center)
        Row(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("NPC")
                Row{
                    listTotalNpc.map {
                        Text(
                            text = "${it.value}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                        if(it.name=="totalNpc")
                            Text(
                                text = "/",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                    }
                }
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Turn")
                Text(
                    text = "${1}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
        Box(modifier = Modifier.weight(1f))
        Column {
            potentialData.map {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = it.name, style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White
                    ))
                    Text(text = it.totalValue.toString(), style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White
                    ))
                }
            }
        }
        Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(sizeApp.paddingMedium),
                colors =
                        ButtonDefaults.buttonColors(
                                containerColor = Color.Cyan,
                                contentColor = Color.Black
                        ),
                shape = RoundedCornerShape(size = sizeApp.paddingSmall)
        ) { Text("Turns") }
    }
}
