package com.example.villageevo.ui.components.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.repository.NpcRepository
import com.example.villageevo.viewmodel.NpcViewModel

@Composable
fun MapGrid(
    type: BuildingType,
    modifier: Modifier = Modifier,
    sum: Int = 0,
    worker: Int,
    npcViewModel: NpcViewModel,

) {
    Button(
        onClick = { npcViewModel.saveNpcAssign(listOf(1,2,3,4), 5) },
        modifier = modifier.fillMaxSize(),
        colors = ButtonColors(
            contentColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        when (type) {
            BuildingType.FOREST -> ForestArea(modifier, sum, worker)
            BuildingType.WILD -> WildArea(modifier, sum, worker)
            else -> Text("Unknown")
        }
    }


}