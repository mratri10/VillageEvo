package com.example.villageevo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.domain.map.MapData
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResource
import com.example.villageevo.viewmodel.GameViewModel
import com.example.villageevo.viewmodel.MapViewModel

@Composable
fun HomeScreen(viewModel: GameViewModel, mapViewModel: MapViewModel) {
    val mapMetaData by viewModel.metaList.collectAsStateWithLifecycle()
    val mapResource by viewModel.mapResource.collectAsStateWithLifecycle()
    val mapData by viewModel.mapData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMapData()
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Tema gelap Village Evolution
    ) {
        LazyVerticalGrid (
            columns = GridCells.Fixed(4), // 4 Kolom sesuai logika row 1..5 Anda
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(12) { index ->
                if (index < mapMetaData.size) {
                    val meta = mapMetaData[index]
                    val resource:MapResource = mapResource.first { it.idMap == meta.id }
                    val data:List<MapData> =mapData.filter { it.idMap == meta.id }

                    MapItemCard(
                        meta,
                        onPress = {
                            mapViewModel.insertUserMap(meta, resource, data)
                            viewModel.openHome()
                        }
                    )
                } else {
                    EmptyMapCard(index)
                }
            }
        }

        // Sidebar (20% lebar layar)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.DarkGray)
        ) {
            // Tempat tombol menu atau info tambahan
        }
    }
}

@Composable
fun MapItemCard(
    meta: MapMetaData,
    onPress: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonColors(
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            containerColor = Color.Cyan,
            disabledContainerColor = Color.Gray
        ),
        onClick = onPress
    ) {
        Text(
            text = meta.title,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun EmptyMapCard(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Empty $index",
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )
    }
}