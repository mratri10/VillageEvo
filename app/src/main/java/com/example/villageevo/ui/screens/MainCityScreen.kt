package com.example.villageevo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.villageevo.ui.ResourceScreen
import com.example.villageevo.viewmodel.GameViewModel

@Composable
fun MainCityScreen(viewModel: GameViewModel){
    val cityState by viewModel.cityState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .padding(12.dp)
    ) {
        ResourceScreen(cityState)
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)) {
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .background(Color(0xFF1B1B1B))
                    .padding(8.dp)
            ){
                Text("Buildings", color =Color.White)
                cityState.buildings.forEach { (type, count)->
                    Text(
                        "- $type : $count",
                        color =Color(0xFFAAAAAA)
                    )
                }
            }
            Spacer(modifier=Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Button(
                        onClick = {viewModel.nextTurn()},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next Turn")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {viewModel.openMarket()},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Market")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {viewModel.openWorkers()},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Workers")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF222222))
                        .padding(6.dp)
                ){
                    Text("Stage 1 Progress", color= Color.White)
                    Text("Gold: ${cityState.resources.gold}/5000", color= Color.Green)
                    Text("Lumber: ${cityState.resources.lumber}/100", color= Color.Green)
                    Text("Food: ${cityState.resources.food}/200", color= Color.Green)
                    Text("Turn: ${cityState.turnState.currentTurn}/20", color= Color.Green)
                }
            }
        }
    }
}