package com.example.villageevo.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.domain.map.MapUserMetaDataEntity
import com.example.villageevo.viewmodel.MapViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SelectScreen(viewModel: MapViewModel) {
    val mapMeta: List<MapUserMetaDataEntity> by viewModel.getUserMeta.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.weight(3f).align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {
            mapMeta.map { entity ->
                viewModel.dataMapUserById(entity.id)
                val mapResource by viewModel.getUserResource.collectAsStateWithLifecycle()
                Column(
                    modifier = Modifier.fillMaxWidth(1/3f)
                        .border(  1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .padding(10.dp),
                ) {
                    Text(text = entity.title)
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 5.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                    mapResource.map { db ->
                        val sumByName = viewModel.sumSource(db.name)
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = db.name)
                            Box(Modifier.weight(1f)) { }
                            Text(text = sumByName.toString())
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Button(
                onClick = {}
            ) {
                Text(text = "Start Game")
            }
        }
    }
}
