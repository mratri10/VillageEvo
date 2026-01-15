package com.example.villageevo.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.villageevo.data.model.BuildingType
import com.example.villageevo.ui.components.BuildingItem
import com.example.villageevo.ui.components.ResourcePanel
import com.example.villageevo.viewmodel.VillageViewModel

@Composable
fun VillageScreen (viewModel: VillageViewModel){
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Village Evolution", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        ResourcePanel(
            gold = state.gold,
            food = state.food,
            wood = state.wood,
            population = state.population,
            happiness = state.happiness
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Buildings", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(BuildingType.values().size){
                index ->
                val building  = BuildingType.values()[index]
                BuildingItem (
                    type = building,
                    level = state.buildings[building] ?:0,
                    onBuild = {viewModel.buildBuilding(building)}
                )
            }
        }

    }
}