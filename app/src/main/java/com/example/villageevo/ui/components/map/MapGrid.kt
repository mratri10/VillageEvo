package com.example.villageevo.ui.components.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.map.MapDataEntity

@Composable
fun MapGrid(
    type: BuildingType,
    modifier: Modifier = Modifier,
    data: MapDataEntity,
    onClick: (id:Int) -> Unit = {}
) {
    println(data.toString())
    Box(
        modifier = Modifier.fillMaxSize().padding().clickable { onClick(data.id) }
    ) {
            when (type) {
                BuildingType.FOREST -> ForestArea(modifier, data.value, 0, data.id)
                BuildingType.WILD -> WildArea(modifier, data.value)
                else -> Text("Unknown")
            }
    }
}
