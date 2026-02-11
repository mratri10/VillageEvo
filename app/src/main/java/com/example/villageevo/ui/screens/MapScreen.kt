package com.example.villageevo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.R
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapDataWorker
import com.example.villageevo.ui.components.map.*
import com.example.villageevo.util.LocalSizeApp
import com.example.villageevo.viewmodel.MapViewModel
import com.example.villageevo.viewmodel.NpcViewModel

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun MapScreen(mapViewModel: MapViewModel, npcViewModel: NpcViewModel) {
    val isShowBuild = remember { mutableStateOf(false) }
    val mapState = remember { mutableStateMapOf("x" to 0, "y" to 0) }
    val sizeApp = LocalSizeApp.current

    val userData by mapViewModel.getUserData.collectAsStateWithLifecycle()
    val userMeta by mapViewModel.getUserMeta.collectAsStateWithLifecycle()

    val potentialData by mapViewModel.getPotential.collectAsStateWithLifecycle()
    val sourceData by mapViewModel.getSource.collectAsStateWithLifecycle()

    val gridMap = remember(userData) { userData.associateBy { "${it.y}_${it.x}" } }
    val idMapSelect = remember { mutableIntStateOf(0 )}
    val builtSelect = remember { mutableStateOf(MapDataWorker(0,0,"",0.0,0,0,0)) }

    LaunchedEffect(Unit) {
        npcViewModel.loadNpcList() }
    LaunchedEffect(idMapSelect.value){
        mapViewModel.dataMapUserById(userMeta.id)
    }

    fun selectMap(data: MapDataWorker){
        npcViewModel.updateAssignToNol()
        if(data.name =="house"){
            builtSelect.value = data
        }else{
            idMapSelect.value = data.id
        }

    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        Image(
                painter = painterResource(id = R.drawable.bg_grass),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .weight(4f)
                .fillMaxHeight()) {
                for (row in 0 until 3) {
                    Row(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        for (col in 0 until 4) {

                            val cellData = gridMap["${row}_${col}"]
                            Box(
                                    modifier =
                                            Modifier
                                                .weight(1f)
                                                .fillMaxHeight()
                                                .border(
                                                    0.5.dp,
                                                    Color.Green.copy(alpha = 0.3f)
                                                )
                            ) {
                                if (cellData != null && cellData.value != 0.0) {
                                    val type =
                                            remember(cellData.name) {
                                                try {
                                                    BuildingType.valueOf(cellData.name.uppercase())
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                    BuildingType.FOREST
                                                }
                                            }
                                    MapGrid(
                                            type = type,
                                            modifier = Modifier.align(Alignment.Center),
                                            data = cellData,
                                            onClick = { selectMap(cellData) }
                                    )
                                } else {
                                    Button(
                                            onClick = {
                                                isShowBuild.value = true
                                                mapState["x"] =col
                                                mapState["y"] =row
                                                      },
                                            modifier =
                                                    Modifier
                                                        .fillMaxSize()
                                                        .padding(sizeApp.paddingSmall),
                                            colors =
                                                    ButtonColors(
                                                            contentColor = Color.Transparent,
                                                            disabledContentColor =
                                                                    Color.Transparent,
                                                            containerColor = Color.Transparent,
                                                            disabledContainerColor =
                                                                    Color.Transparent
                                                    )
                                    ) {
                                        Image(
                                                painter = painterResource(id = R.drawable.rec_plus),
                                                contentDescription = "Empty Tile",
                                                modifier = Modifier.fillMaxSize(),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Sidebar atau Panel Detail
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Black)) {
                IndicatorMap(
                    userMeta = userMeta,
                    potentialData,
                    source = sourceData,
                    {
                        mapViewModel.turnProcess(potentialData, it, userData)
                    }
                )
            }
        }
        if (isShowBuild.value) {
            BuildArea(
                onClick = {isShowBuild.value = false},
                mapViewModel,
                idMap = userMeta.id,
                x = mapState["x"]!!,
                y = mapState["y"]!!,
                sourceData
            )
        }
        if (idMapSelect.value > 0) {
            Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {},
                    contentAlignment = Alignment.Center
            ) {
                NpcSelect(
                        npcViewModel = npcViewModel,
                        idMapUser = idMapSelect.intValue,
                        onClick = { idMapSelect.intValue = it }
                )
            }
        }
        if (builtSelect.value.id>0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {},
                contentAlignment = Alignment.Center
            ) {
                HouseSelect(builtSelect.value.value)
            }
        }
    }
}
