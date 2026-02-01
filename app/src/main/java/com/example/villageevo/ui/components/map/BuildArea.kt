package com.example.villageevo.ui.components.map

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.villageevo.R

@Composable
fun BuildArea(
    onClick: () -> Unit
){

    val buttonList: List<ButtonData> = listOf(
        ButtonData("Market", R.drawable.build_market,{}, -180f, 14,-15),
        ButtonData("House", R.drawable.build_house,{}, -144f,14,3),
        ButtonData("Build", R.drawable.build,{}, -108f,2,4),
        ButtonData("School", R.drawable.build_school,{}, -72f,14,3),
        ButtonData("Battle", R.drawable.build_battle,{}, -36f,14,-15)
    )

    val toHalfCircle = GenericShape{
        size, _ ->
        moveTo(0f, size.height)
        arcTo(
            rect = Rect(0f, 0f, size.width, size.height*2),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )
        close()
    }
    fun polarOffset(
        angleDeg: Float,
        radius: Float
    ): Offset{
        val rad = Math.toRadians(angleDeg.toDouble())
        return Offset(
            x = (kotlin.math.cos(rad)*radius).toFloat(),
            y = (kotlin.math.sin(rad)*radius).toFloat()
        )
    }

    fun toHalfCircle(degree: Float): Shape{
         return GenericShape { size, _ ->
            moveTo(size.width / 2, size.height)
            arcTo(
                rect = Rect(0f, 0f, size.width, size.height * 2),
                startAngleDegrees = degree,
                sweepAngleDegrees = 35f,
                forceMoveTo = false
            )
            close()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.BottomCenter
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(0.4f)
                .background(color = Color.Transparent, toHalfCircle)
        ) {

        }

        buttonList.forEach { it ->
            val sweep = 36f
            val midAngle =it.degree +sweep/2
            Button(
                modifier = Modifier.fillMaxSize(0.4f),
                onClick={},
                shape = toHalfCircle(it.degree),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.scrim,
                    disabledContainerColor = MaterialTheme.colorScheme.scrim,
                    disabledContentColor = Color.White
                )
            ){
                val radiusPx = with(LocalDensity.current){
                    10.dp.toPx()
                }
                val offset = polarOffset(midAngle, radiusPx)
                Image(
                    painter = painterResource(it.image),
                    contentDescription = it.name,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier
                        .size(36.dp)
                        .offset{
                            IntOffset(
                                offset.x.toInt()*it.contentX,
                                offset.y.toInt()*it.contentY
                            )
                        }
                        .rotate(midAngle+90f),
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxSize(0.2f),
            onClick={onClick()},
            shape = toHalfCircle,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.Black
            )
        ){
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Rounded.Close,
                contentDescription = "Back"
            )
        }


    }
}

data class ButtonData(
    var name:String,
    var image:Int,
    var onClick:()->Unit,
    var degree: Float,
    var contentX:Int,
    var contentY:Int
)