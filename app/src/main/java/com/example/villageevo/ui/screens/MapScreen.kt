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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.villageevo.R
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.ui.components.map.DenseForestArea
import com.example.villageevo.ui.components.map.WildArea
import com.example.villageevo.viewmodel.GameViewModel
import com.example.villageevo.viewmodel.MapViewModel
import com.example.villageevo.viewmodel.SoldierViewModel

@Composable
fun MapScreen(
    idMap: Int,
    navController: NavController,
    gameViewModel: GameViewModel,
    mapViewModel: MapViewModel,
    soldierViewModel: SoldierViewModel
){
    val userData by mapViewModel.getUserData.collectAsState()

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
                            val cellData = userData.find { it.x == col && it.y == row }
                            Box(
                                modifier =
                                    Modifier.weight(1f)
                                        .fillMaxHeight()
                                        .align(Alignment.CenterVertically)
                                        .border(1.dp, Color.Green)
                                        .clickable {
                                        }
                            ){
                                if (cellData != null) {
                                    val type =
                                        try {
                                            BuildingType.valueOf(cellData.name.uppercase())
                                        } catch (e: Exception) {
                                            BuildingType.FOREST
                                        }
                                    val sum = cellData.value
                                    // Assuming 'worker' is not in MapData yet, default to 0
                                    MapGrid(
                                        type,
                                        modifier = Modifier.align(Alignment.Center),
                                        sum,
                                        0
                                    )
                                }else{
                                    Text(
                                        "$row - $col",
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(1f)) { }
        }
    }
}

@Composable
private fun MapGrid(
    type: BuildingType,
    modifier: Modifier = Modifier,
    sum: Int = 0,
    worker: Int = 0
) {
    when {
        //        type == BuildingType.HOUSE -> HouseArea(modifier, sum, worker)
        //        type == BuildingType.FARM -> FieldArea(modifier, sum, worker)
        type == BuildingType.FOREST -> DenseForestArea(modifier, sum, worker)
        type == BuildingType.WILD -> WildArea(modifier, sum, worker)
        else -> Text("Unknown")
    }
}