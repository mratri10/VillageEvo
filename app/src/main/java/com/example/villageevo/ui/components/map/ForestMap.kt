package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.villageevo.R

@Composable
fun VillageMap(mapSize: Int = 10){
    val centerRange = 3..6

    Column {
        for (y in 0 until mapSize) {
            Row {
                for (x in 0 until mapSize) {
                    val isForest = x !in centerRange || y !in centerRange
                    Box(modifier = Modifier.size(20.dp).padding(0.2.dp)) {
                        if (isForest) {
                            Image(painterResource(R.drawable.tree), "Forest")
                        } else {
                            Image(painterResource(R.drawable.bg_grass), "Village");
                        }
                    }
                }
            }
        }
    }
}