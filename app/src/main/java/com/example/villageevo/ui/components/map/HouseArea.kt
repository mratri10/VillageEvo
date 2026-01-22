package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.villageevo.R

@Composable
fun HouseArea(
    modifier: Modifier = Modifier,
    xSum: Int = 1,
    ySum: Int = 1,
    farmerWork: Int = 0
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val screenWidth = maxWidth
        val houseSize = screenWidth / 10f
        val spacing = houseSize * 2.2f

        val totalHouses = xSum * ySum
        val maxCapacity = totalHouses * 5
        val farmersAtHome = (maxCapacity - farmerWork).coerceAtLeast(0)

        val calculatedWidth = (spacing * (xSum - 1)) + (houseSize * 3)
        val calculatedHeight = (spacing * (ySum - 1)) + (houseSize * 3)

        Box(modifier = Modifier.width(calculatedWidth).height(calculatedHeight)) {
            for (row in 0 until ySum) {
                for (col in 0 until xSum) {
                    val houseIndex = (row * xSum) + col
                    val farmersInThisHouse = when {
                        farmersAtHome >= (houseIndex * 5) + 5 -> 5
                        farmersAtHome > (houseIndex * 5) -> farmersAtHome % 5
                        else -> 0
                    }
                    Box(
                        modifier = Modifier
                            .offset(x = spacing * col, y = spacing * row)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "house",
                            modifier = Modifier.size(houseSize * 3),
                            contentScale = ContentScale.Fit
                        )
                        Box(
                            modifier = Modifier
                                .offset(y = houseSize * 2f)
                                .padding(start = 5.dp)
                        ) {
                            repeat(farmersInThisHouse) { farmerIndex ->
                                val farmerSize = houseSize * 0.3f
                                Image(
                                    painter = painterResource(id = R.drawable.farmer),
                                    contentDescription = "farmer",
                                    modifier = Modifier
                                        .size(farmerSize * 3)
                                        .offset(x = farmerIndex * (farmerSize * 1f)),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}