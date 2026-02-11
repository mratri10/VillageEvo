package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.villageevo.R
import com.example.villageevo.util.LocalSizeApp

@Composable
fun VillageArea(sum:Int, imageSource:Int){
    val sizeApp = LocalSizeApp.current
    val sumShow = if(sum>9)10 else sum
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(count = sumShow){
                Image(
                    painter = painterResource(id = imageSource),
                    contentDescription = null,
                    modifier =
                        Modifier.size(sizeApp.iconSize/2),
                )
            }
        }
        Row(
            modifier =
                Modifier.background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    RoundedCornerShape(sizeApp.paddingSmall)
                )
                    .padding(sizeApp.paddingSmall / 2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painterResource(
                    R.drawable.build_house), null,
                Modifier.size(sizeApp.iconSize / 4),
                colorFilter = ColorFilter.tint(Color.White))
            Text(sum.toString(), style = MaterialTheme.typography.labelSmall, color = Color.White)
        }
    }
}