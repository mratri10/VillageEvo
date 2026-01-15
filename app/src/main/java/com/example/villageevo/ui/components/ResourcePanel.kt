package com.example.villageevo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResourcePanel(
    gold:Int,
    food: Int,
    wood: Int,
    population: Int,
    happiness: Int
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Gold: $gold")
            Text("Food: $food")
            Text("Wood: $wood")
            Text("Population: $population")
        }
    }
}