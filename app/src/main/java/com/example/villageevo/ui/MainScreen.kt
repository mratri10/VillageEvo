package com.example.villageevo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.villageevo.viewmodel.GameViewModel

@Composable
fun MainScreen(vm: GameViewModel) {
    val city by vm.cityState.collectAsState()

    Column (modifier = Modifier.fillMaxSize().padding(12.dp)) {
        ResourceScreen(city)
        Spacer(modifier = Modifier.height(12.dp))
        EraProgressView(city)
        Row {
            Button(onClick = {vm.nextTurn()}) {
                Text("Next Turn")
            }
            Spacer(Modifier.width(12.dp))
            Button(onClick = {vm.openMarket()}){
                Text("Market")
            }
            Spacer(Modifier.width(12.dp))
            Button(onClick = {vm.openWorkers()}) {
                Text("Workers")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {vm.openMarket()}) { }
    }
}