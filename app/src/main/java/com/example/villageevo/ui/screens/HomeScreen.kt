package com.example.villageevo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResource
import com.example.villageevo.domain.soldier.MapSoldierDisplay
import com.example.villageevo.domain.soldier.Soldier
import com.example.villageevo.viewmodel.GameViewModel
import com.example.villageevo.viewmodel.MapViewModel
import com.example.villageevo.viewmodel.SoldierViewModel
import kotlin.math.ceil

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeScreen(viewModel: GameViewModel, mapUserModel: MapViewModel, soldierViewModel: SoldierViewModel) {
    val mapMetaData by viewModel.metaList.collectAsStateWithLifecycle()
    val mapResource by viewModel.mapResource.collectAsStateWithLifecycle()
    val mapUserResource by mapUserModel.getUserResource.collectAsStateWithLifecycle()
    val mapData by viewModel.mapData.collectAsStateWithLifecycle()
    val mapSoldier : List<MapSoldierDisplay> by soldierViewModel.mapSoldierDisplay.collectAsStateWithLifecycle()
    var selectedMeta by remember { mutableStateOf<MapMetaData?>(mapMetaData[0]) }
    var currentPage by remember { mutableStateOf(1) }
    var soldierData by remember { mutableStateOf<Soldier?>(null) }


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight / 3

    LaunchedEffect(Unit) {
        viewModel.getMapData()
    }

    LaunchedEffect(selectedMeta?.id) {
        selectedMeta?.id?.let {id->
            soldierViewModel.getSoldierByIdMap(id)
            mapUserModel.dataMapUserById(id)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyVerticalGrid (
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(9) { index ->
                Box(
                    modifier=Modifier.height(cardHeight).background(Color.DarkGray, RoundedCornerShape(12.dp)),
                ){
                    if (index < mapMetaData.size) {
                        val meta = mapMetaData[index]
//                    val resource:List<MapResource> = mapResource.filter { it.idMap == meta.id }
//                    val data:List<MapData> =mapData.filter { it.idMap == meta.id }
                        MapItemCard(
                            meta,
                            isSelected = selectedMeta == meta,
                            onPress = {
//                            mapViewModel.insertUserMap(meta, resource, data)
//                            mapViewModel.dataMapUser()
                                selectedMeta = meta
                            }
                        )
                    } else {
                        EmptyMapCard(index)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.DarkGray)
                .padding(10.dp)
        ) {
            Text(
                "VILVO",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center
                ),
                color = Color.White
            )
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top=10.dp),
                contentAlignment = Alignment.Center){
                Column() {
                    selectedMeta?.let{
                        val resources : List<MapResource> by viewModel.mapResource.collectAsStateWithLifecycle()
                        resources
                            .filter { it.idMap == selectedMeta?.id }
                            .forEach {source ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = source.name,
                                    style = MaterialTheme.typography.labelSmall.copy(Color.White)
                                )
                                Text(
                                    text = source.sum.toString(),
                                    style = MaterialTheme.typography.labelSmall.copy(Color.White)
                                )
                            }
                        }

                    }
                }
            }
            Box(
                modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center){
                Column() {
                    selectedMeta?.let{

                        mapSoldier.forEach {soldier ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = soldier.name,
                                    style = MaterialTheme.typography.labelSmall.copy(Color.White)
                                )
                                Text(
                                    text = soldier.sum.toString(),
                                    style = MaterialTheme.typography.labelSmall.copy(Color.White)
                                )
                            }
                        }

                    }
                }
            }
            Button(
                colors = ButtonColors(
                    containerColor = Color.Cyan,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                    ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {},
                )
            {
                if(mapSoldier.isNotEmpty()){
                    Text("Battle")
                }else{
                    Text("Masuk")
                }
            }
            Box(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    enabled = currentPage > 1,
                    onClick = {
                        if(currentPage > 1){
                            currentPage--
                        }
                    },
                    colors = IconButtonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Cyan,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(currentPage.toString(),
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style= MaterialTheme.typography.labelLarge.copy(color = Color.White)
                )
                IconButton(
                    enabled = currentPage <= ceil(((mapMetaData.size-1) / 9).toDouble()),
                    onClick = {currentPage++},
                    colors = IconButtonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Cyan,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MapItemCard(
    meta: MapMetaData,
    isSelected:Boolean,
    onPress: () -> Unit
) {
    val colorSelected:Color = when(isSelected){
        true -> Color.Cyan
        false -> Color.LightGray
    }
    Button(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight().border(
                width = 1.dp,
                color =colorSelected,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonColors(
            contentColor = colorSelected,
            disabledContentColor = Color.Gray,
            containerColor =  Color.DarkGray,
            disabledContainerColor = Color.Gray
        ),
        onClick = onPress
    ) {
        Text(
            text = meta.title,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun EmptyMapCard(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Empty $index",
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )
    }
}