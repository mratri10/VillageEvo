package com.example.villageevo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.villageevo.domain.building.BuildingRepository
import com.example.villageevo.domain.building.BuildingType

@Composable
fun BuildingItem(
    type: BuildingType,
    currentCount:Int,
    canAfford: Boolean,
    onBuild: () -> Unit
) {
    val def = BuildingRepository.getDefinition(type)
    Card (
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(canAfford) MaterialTheme.colorScheme.surfaceVariant
                            else MaterialTheme.colorScheme.surfaceDim
        )
    ){
        Row(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "${def.name} (Lvl $currentCount)", style = MaterialTheme.typography.titleMedium)
                Text(text = "Cost: ${def.buildCostGold} Gold, ${def.buildCostLumber} Lumber", style = MaterialTheme.typography.bodySmall)
                Text(text = def.description, style = MaterialTheme.typography.labelSmall)
            }
            Button(
                onClick = onBuild,
                enabled = canAfford
            ) {
                Text("Build")
            }
        }
    }
}