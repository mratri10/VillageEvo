package com.example.villageevo.ui.screens

import com.example.villageevo.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.building.XYData
import com.example.villageevo.ui.components.map.DenseForestArea
import com.example.villageevo.ui.components.map.FieldArea
import com.example.villageevo.ui.components.map.HouseArea
import com.example.villageevo.ui.components.map.WildArea
import com.example.villageevo.util.XYSumConvert
import com.example.villageevo.viewmodel.GameViewModel

@Composable
fun MainCityScreen(viewModel: GameViewModel) {
    var isBuild by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Image(
            painter = painterResource(id = R.drawable.bg_grass),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Row(modifier = Modifier.fillMaxWidth()){
            Column(modifier = Modifier.weight(4f).fillMaxHeight()){
                for(row in 0 until 3){
                    Row(modifier = Modifier.weight(1f).fillMaxWidth()){
                        for (col in 0 until 4){
                            Box(modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically)
                                .border(1.dp, Color.Green)
                            ){
                                for (map in viewModel.showMap()){
                                    if(map.x == col && map.y == row) {
                                        val sum:Int = map.building.sum
                                        val workers:Int = map.building.workers
                                        MapGrid(map.buildingType, modifier = Modifier.align(Alignment.Center), sum, workers)
                                    }
                                }
                                when{

                                    else-> Text("$row - $col")
                                }
                            }
                        }
                    }
                }
            }
            MainGame(modifier = Modifier.weight(1f), viewModel)
        }
    }
}
@Composable
private fun MapGrid(type: BuildingType,modifier: Modifier = Modifier, sum: Int = 0, worker: Int = 0){
    when{
//        type == BuildingType.HOUSE -> HouseArea(modifier, sum, worker)
//        type == BuildingType.FARM -> FieldArea(modifier, sum, worker)
        type == BuildingType.FOREST -> DenseForestArea(modifier, sum, worker)
        type == BuildingType.ANIMAL -> WildArea(modifier, sum, worker)
        else -> Text("Unknown")
    }
}