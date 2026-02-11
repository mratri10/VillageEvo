package com.example.villageevo.ui.components.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.villageevo.R
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.map.MapDataWorker
import com.example.villageevo.util.LocalSizeApp

@Composable
fun MapGrid(
        type: BuildingType,
        modifier: Modifier = Modifier,
        data: MapDataWorker,
        onClick: (id: Int) -> Unit = {}
) {
    val sizeApp = LocalSizeApp.current

    val imageForest = SourceMapHelper.forestImage(data.value)
    val widthForest = SourceMapHelper.forestWidth(data.value)
    val heightForest = SourceMapHelper.forestHeight(data.value)

    val imageWild = SourceMapHelper.wildImage(data.value)
    val widthWild = SourceMapHelper.forestWidth(data.value)
    val heightWild = SourceMapHelper.forestHeight(data.value)

    Box(
            modifier =
                    Modifier.fillMaxSize().padding(sizeApp.paddingSmall/4).clickable {
                        onClick(data.id)
                    }
    ) {
        when (type) {
            BuildingType.FOREST -> SourceArea(
                modifier,
                data.value,
                imageForest,
                widthForest,
                heightForest,
                R.drawable.tree,
                data.worker,
                data.id
            )
            BuildingType.WILD -> SourceArea(
                modifier,
                data.value,
                imageWild,
                widthWild,
                heightWild,
                R.drawable.deer,
                data.worker,
                data.id
            )
            BuildingType.HOUSE -> VillageArea(
                data.value.toInt(),
                R.drawable.build_house,
            )
            else ->Text("Unknown")
        }
    }
}
