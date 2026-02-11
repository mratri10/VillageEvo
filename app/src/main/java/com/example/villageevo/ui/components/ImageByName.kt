package com.example.villageevo.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.villageevo.R

@Composable
fun ImageByName(name:String, color: Color?=null){
    when(name){
        "forest" -> Image(
            painterResource(R.drawable.tree), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "house" -> Image(
            painterResource(R.drawable.build_house), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "farm" -> Image(
            painterResource(R.drawable.farm), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "wild" -> Image(
            painterResource(R.drawable.deer), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "market" -> Image(
            painterResource(R.drawable.build_market), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "school" -> Image(
            painterResource(R.drawable.build_school), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "wood" -> Image(
            painterResource(R.drawable.wood), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "gold" -> Image(
            painterResource(R.drawable.gold), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
        "food" -> Image(
            painterResource(R.drawable.food), null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(color != null) ColorFilter.tint(color) else null
        )
    }
}