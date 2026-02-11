package com.example.villageevo.ui.components.map

import android.service.autofill.UserData
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.example.villageevo.R
import com.example.villageevo.domain.building.BuildConvert
import com.example.villageevo.domain.building.BuildRequired
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.building.SourceEntity
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.util.LocalSizeApp
import com.example.villageevo.viewmodel.MapViewModel
import com.example.villageevo.viewmodel.NpcViewModel

data class ButtonData(
        var name: BuildingType,
        var image: Int,
        var onClick: () -> Unit,
        var degree: Float,
        var contentX: Int,
        var contentY: Int
)

@Composable
fun BuildArea(onClick: () -> Unit, mapViewModel: MapViewModel, idMap:Int, x:Int, y:Int,sourceData: List<SourceEntity>) {
    val sizeApp = LocalSizeApp.current
    var showNoResourceAlert by remember { mutableStateOf(false) }

    if (showNoResourceAlert) {
        AlertDialog(
            onDismissRequest = { showNoResourceAlert = false },
            title = { Text("Not Enough Resource") },
            text = { Text("You need more resources to build a House.") },
            confirmButton = {
                TextButton(onClick = { showNoResourceAlert = false }) {
                    Text("Ok")
                }
            }
        )
    }

    val buttonList: List<ButtonData> =
        listOf(
            ButtonData(BuildingType.MARKET, R.drawable.build_market, {}, -180f, 6, -7),
            ButtonData(BuildingType.HOUSE, R.drawable.build_house, {
                val listRequired = BuildRequired.house
                val requiredList = listRequired.map {
                    SourceEntity(params = it.required, value = it.value)
                }
                if(BuildConvert.isAvailableBuild(sourceData, requiredList)){
                    mapViewModel.saveUserMap(
                        MapDataEntity(
                            id = 0,
                            idMap = idMap,
                            name = BuildingType.HOUSE.name.lowercase(),
                            value = 1.0,
                            x = x,
                            y = y
                        ),
                        requiredList
                    )
                    onClick()
                }else{
                    showNoResourceAlert = true
                }
            }, -144f, 6, 1),
            ButtonData(BuildingType.BUILD, R.drawable.build, {}, -108f, 2, 2),
            ButtonData(BuildingType.SCHOOL, R.drawable.build_school, {}, -72f, 6, 1),
            ButtonData(BuildingType.BARRACK, R.drawable.build_battle, {}, -36f, 6, -7)
        )

    val toHalfCircle = GenericShape { size, _ ->
        moveTo(0f, size.height)
        arcTo(
            rect = Rect(0f, 0f, size.width, size.height * 2),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )
        close()
    }
    fun polarOffset(angleDeg: Float, radius: Float): Offset {
        val rad = Math.toRadians(angleDeg.toDouble())
        return Offset(
            x = (kotlin.math.cos(rad) * radius).toFloat(),
            y = (kotlin.math.sin(rad) * radius).toFloat()
        )
    }

    fun toHalfCircle(degree: Float): Shape {
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
        modifier =
            Modifier.fillMaxSize().background(color = Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier =
                Modifier.fillMaxSize(0.4f)
                    .background(color = Color.Transparent, toHalfCircle)
        ) {}

        buttonList.forEach {
            val sweep = 36f
            val midAngle = it.degree + sweep / 2
            Button(
                modifier = Modifier.fillMaxSize(0.4f),
                onClick = it.onClick,
                shape = toHalfCircle(it.degree),
                colors =
                    ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.scrim,
                        disabledContainerColor =
                            MaterialTheme.colorScheme.scrim,
                        disabledContentColor = Color.White
                    )
            ) {
                val radiusPx =
                    with(LocalDensity.current) { sizeApp.paddingMedium.toPx() }
                val offset = polarOffset(midAngle, radiusPx)
                Image(
                    painter = painterResource(it.image),
                    contentDescription = it.name.toString(),
                    colorFilter =
                        ColorFilter.tint(
                            MaterialTheme.colorScheme.tertiary
                        ),
                    modifier =
                        Modifier.size(sizeApp.iconSize/2)
                            .offset {
                                IntOffset(
                                    offset.x.toInt() *
                                            it.contentX,
                                    offset.y.toInt() *
                                            it.contentY
                                )
                            }
                            .rotate(midAngle + 90f),
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxSize(0.2f),
            onClick = { onClick() },
            shape = toHalfCircle,
            colors =
                ButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
        ) {
            Icon(
                modifier = Modifier.size(sizeApp.iconSize/2),
                imageVector = Icons.Rounded.Close,
                contentDescription = "Back"
            )
        }
    }
}
