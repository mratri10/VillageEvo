package com.example.villageevo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.viewmodel.GameViewModel

@Composable
fun HomeScreen(viewModel: GameViewModel){

    LaunchedEffect(Unit) {
        viewModel.getMapData()
    }

    val mapData by viewModel.metadataList.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Button(
            onClick = { println(mapData) },
        ){
            Text(text = "Start Game")
        }
    }
}