package com.example.villageevo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.unit.dp
import com.example.villageevo.viewmodel.GameViewModel

@Composable
fun MarketScreen(vm: GameViewModel) {
    var food by remember { mutableStateOf("0") }
    var lumber by remember { mutableStateOf("0") }
    var invest by remember { mutableStateOf("0") }

    Column{
        OutlinedTextField(food, {food=it}, label = { Text("Sell Food") })
        OutlinedTextField(lumber, {lumber=it}, label = { Text("Sell Lumber") })
        Button(onClick = {vm.sell(food.toInt(), lumber.toInt())}){Text("Sell")}

        Spacer(modifier = Modifier.height(12.dp).background(Color.Gray))

        OutlinedTextField(invest, {invest=it}, label={Text("Invest Gold")})
        Button(onClick = {vm.invest(invest.toInt())}){Text("Invest")}
    }


}