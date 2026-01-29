package com.example.villageevo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.villageevo.R
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.viewmodel.GameViewModel
import com.example.villageevo.viewmodel.MapViewModel
import com.example.villageevo.viewmodel.SoldierViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.ui.components.map.ForestArea
import com.example.villageevo.ui.components.map.SourceArea
import com.example.villageevo.ui.components.map.WildArea

@Composable
fun MapScreen(
    idMap: Int,
    navController: NavController,
    gameViewModel: GameViewModel,
    mapViewModel: MapViewModel,
    soldierViewModel: SoldierViewModel
){
    // 1. Gunakan collectAsStateWithLifecycle untuk efisiensi resource Android
    val userData by mapViewModel.getUserData.collectAsStateWithLifecycle()

    // 2. Optimasi Pencarian: Ubah List menjadi Map (Key: "y_x")
    // Menggunakan remember agar indexing hanya dilakukan saat userData berubah
    val gridMap = remember (userData) {
        userData.associateBy { "${it.y}_${it.x}" }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Image(
            painter = painterResource(id = R.drawable.bg_grass),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(4f).fillMaxHeight()) {
                for (row in 0 until 3) {
                    Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                        for (col in 0 until 4) {
                            // Lookup O(1) menggunakan key yang sudah diproses
                            val cellData = gridMap["${row}_${col}"]

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .border(0.5.dp, Color.Green.copy(alpha = 0.3f)) // Border lebih halus
                                    .clickable { /* Handle Tile Click */ }
                            ) {
                                if (cellData != null) {
                                    // 3. Pindahkan parsing tipe ke luar atau gunakan helper
                                    val type = remember(cellData.name) {
                                        try {
                                            BuildingType.valueOf(cellData.name.uppercase())
                                        } catch (e: Exception) {
                                            BuildingType.FOREST
                                        }
                                    }
                                    MapGrid(
                                        type = type,
                                        modifier = Modifier.align(Alignment.Center),
                                        sum = cellData.value,
                                        worker = 0
                                    )
                                } else {
                                    Text(
                                        text = "$row - $col",
                                        color = Color.Gray,
                                        modifier = Modifier.align(Alignment.Center),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
            // Sidebar atau Panel Detail
            Box(modifier = Modifier.weight(1f)) { }
        }
    }
}
@Composable
private fun MapGrid(
    type: BuildingType,
    modifier: Modifier = Modifier,
    sum: Int = 0,
    worker: Int,
) {
    when (type) {
        BuildingType.FOREST -> ForestArea(modifier, sum, worker)
        BuildingType.WILD -> WildArea(modifier, sum, worker)
        else -> Text("Unknown")
    }

}