package com.example.villageevo.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.villageevo.R

@Composable
fun IconMap(name:String, color: Color= Color.Gray) {
    when(name.lowercase()){
        "forest" -> UnitImage(R.drawable.tree)
        "wild"->UnitImage(R.drawable.deer)
        "mine_iron"->UnitImage(R.drawable.mine_iron)
        "mine_rock"->UnitImage(R.drawable.mine_rock)
        "mine_gold"->UnitImage(R.drawable.mine_gold)

        "infantry"->UnitImage(R.drawable.infantry,color)
        "calvary"->UnitImage(R.drawable.calvary,color)
        "archer"->UnitImage(R.drawable.archer,color)
        "spearman"->UnitImage(R.drawable.spearman,color)

        "food"-> UnitImage(R.drawable.food)
        "coin"-> UnitImage(R.drawable.coin)
        "wood"-> UnitImage(R.drawable.wood)
        "human"-> UnitImage(R.drawable.farmer)
        "gold"->UnitImage(R.drawable.gold)
        "iron"->UnitImage(R.drawable.iron)
        "rock"->UnitImage(R.drawable.rock)
        else -> Text(text = name, style = MaterialTheme.typography.labelSmall, color = Color.White)

    }
}

@Composable
fun UnitImage(image:Int, color: Color=Color.Gray){
    Image(
        painter = painterResource(id = image),
        contentDescription = image.toString(),
        modifier = Modifier
            .size(30.dp).background(color, RoundedCornerShape(10.dp)),
        alignment = Alignment.Center
    )
}