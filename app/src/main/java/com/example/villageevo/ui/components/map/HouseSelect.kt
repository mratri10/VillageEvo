package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.villageevo.R
import com.example.villageevo.domain.building.BuildRequired
import com.example.villageevo.ui.components.ImageByName
import com.example.villageevo.util.LocalSizeApp

@Composable
fun HouseSelect(value: Double){
    val sizeApp = LocalSizeApp.current
    val sumState = remember { mutableDoubleStateOf(value) }
    Column(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .background(Color.Gray),
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(sizeApp.paddingLarge),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                enabled = sumState.value>1,
                onClick = {sumState.value--},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Red.copy(0.2f),
                    disabledContentColor = Color.White
                )
            ) {
                Text("-")
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(sumState.value.toInt().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White)
                Image(painterResource(
                    R.drawable.build_house), null,
                    Modifier
                        .size(sizeApp.iconSize / 2)
                        .padding(start = sizeApp.paddingSmall),
                    colorFilter = ColorFilter.tint(Color.White))
            }
            Button(
                onClick = {sumState.value++},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("+")
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(BuildRequired.house.size){
                val data = BuildRequired.house[it]
                Row(
                    Modifier.background(Color.Green),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(sizeApp.iconSize/2)) {
                        ImageByName(data.required.name.lowercase())
                    }
                    Text(
                        (data.value * (sumState.value-value)).toInt().toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White)
                }
            }
        }
    }
}