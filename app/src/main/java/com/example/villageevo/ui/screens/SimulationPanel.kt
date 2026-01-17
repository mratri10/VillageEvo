package com.example.villageevo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.economy.EconomicEngine

@Composable
fun SimulationPanel(
    city: CityState,
    engine: EconomicEngine
) {
    Column (
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
            .background(Color(0xFF2A2A2A))
    ) {
        Text("SIMULATION", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(8.dp))

        Button (onClick = {
            engine.processTurn(city)
        }) {
            Text("NEXT TURN")
        }

        Button(onClick = {
            repeat(10) { engine.processTurn(city) }
        }) {
            Text("+10 TURNS")
        }

        Button(onClick = {
            repeat(100) { engine.processTurn(city) }
        }) {
            Text("+100 TURNS")
        }
    }
}
