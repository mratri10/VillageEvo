package com.example.villageevo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.villageevo.domain.city.CityState

@Composable
fun ResourceScreen(city: CityState) {
    Card{
        Column {
            Text("Gold: ${city.resources.gold}")
            Text("Lumber: ${city.resources.lumber}")
            Text("Food: ${city.resources.food}")
            Text("Energy: ${city.resources.energy}")
            Text("Population: ${city.population}")
            Text("Turn: ${city.turnState.currentTurn}/${city.turnState.maxTurn}")
        }
    }
}