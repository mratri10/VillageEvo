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
import com.example.villageevo.ui.components.map.*
import com.example.villageevo.viewmodel.MapViewModel
import com.example.villageevo.viewmodel.NpcViewModel

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun MapScreen(mapViewModel: MapViewModel, npcViewModel: NpcViewModel) {
    val isShowBuild = remember { mutableStateOf(false) }

    val userData by mapViewModel.getUserData.collectAsStateWithLifecycle()
    val userMeta by mapViewModel.getUserMeta.collectAsStateWithLifecycle()
    val countNpc by npcViewModel.countNpc.collectAsStateWithLifecycle()

    val gridMap = remember(userData) { userData.associateBy { "${it.y}_${it.x}" } }
    val idMapSelect = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) { npcViewModel.loadNpcList() }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Image(
                painter = painterResource(id = R.drawable.bg_grass),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(4f).fillMaxHeight()) {
                for (row in 0 until 3) {
                    Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                        for (col in 0 until 4) {

                            val cellData = gridMap["${row}_${col}"]
                            Box(
                                    modifier =
                                            Modifier.weight(1f)
                                                    .fillMaxHeight()
                                                    .border(0.5.dp, Color.Green.copy(alpha = 0.3f))
                                                    .clickable { /* Handle Tile Click */}
                            ) {
                                if (cellData != null) {
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
                                        onClick = {id ->
                                            idMapSelect.intValue = id
                                        }
                                    )
                                } else {
                                    Button(
                                            onClick = { isShowBuild.value = true },
                                            modifier = Modifier.fillMaxSize().padding(5.dp),
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
            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color.Black)) {
                IndicatorMap(userMeta = userMeta, countNpc )
            }
        }
        if (isShowBuild.value) {
            BuildArea(onClick = { isShowBuild.value = false })
        }
        if(idMapSelect.value>0){
            Box(modifier = Modifier.fillMaxSize()
                .clickable{},
                contentAlignment = Alignment.Center){
                NpcSelect(
                    npcViewModel = npcViewModel,
                    idMapUser= idMapSelect.intValue,
                    onClick = {idMapSelect.intValue = it
                })
            }
        }
    }
}
