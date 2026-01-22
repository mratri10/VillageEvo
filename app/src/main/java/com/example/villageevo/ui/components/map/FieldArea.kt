package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.villageevo.R

@Composable
fun FieldArea(
    modifier: Modifier = Modifier,
    xSum: Int = 1,
    ySum: Int = 1,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val screenHeight = maxHeight
        val farmSize = screenHeight / 6f
        Column{
            for(row in 0 until ySum) {

                Row {
                    for(col in 0 until xSum) {
                        Box{
                            Image(
                                painter = painterResource(id = R.drawable.farm),
                                contentDescription = "farm",
                                modifier = Modifier
                                    .height(farmSize*1f),
                                contentScale = ContentScale.FillHeight
                            )
                            Box(modifier= Modifier.padding(start = 5.dp, top = 5.dp)){
                                for (farmer in 0 until 3){
                                    Image(
                                        painter = painterResource(id = R.drawable.farmer),
                                        contentDescription = "farm",
                                        modifier = Modifier
                                            .size(farmSize*0.5f)
                                            .offset(x = farmer.dp*7f, y=farmSize-7.dp),
                                        contentScale = ContentScale.FillHeight
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(farmSize*0.4f))
            }
        }
    }
}