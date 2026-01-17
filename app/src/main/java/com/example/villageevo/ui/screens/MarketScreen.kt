package com.example.villageevo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.economy.EconomicEngine
import com.example.villageevo.domain.economy.ResourceType

@Composable
fun MarketScreen(
    city: CityState,
    engine: EconomicEngine
) {
    var foodSell by remember { mutableStateOf("0") }
    var lumberSell by remember { mutableStateOf("0") }
    var investGold by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(12.dp)
            .background(Color(0xFF1E1E1E))
    ) {
        Text("MARKET", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(12.dp))

        ResourceSellRow("FOOD", foodSell) { foodSell = it }
        ResourceSellRow("LUMBER", lumberSell) { lumberSell = it }

        Button (onClick = {
            engine.sellCommodity(city, ResourceType.FOOD, foodSell.toLongOrNull() ?: 0)
            engine.sellCommodity(city, ResourceType.LUMBER, lumberSell.toLongOrNull() ?: 0)
        }) {
            Text("SELL")
        }

        Spacer(Modifier.height(16.dp))

        Text("INVEST")

        OutlinedTextField(
            value = investGold,
            onValueChange = { investGold = it },
            label = { Text("Gold") }
        )

        Button(onClick = {
            engine.investMarket(city, investGold.toLongOrNull() ?: 0)
        }) {
            Text("INVEST")
        }
    }
}

@Composable
private fun ResourceSellRow(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.width(80.dp))
        OutlinedTextField(value, onChange, modifier = Modifier.width(120.dp))
    }
}
