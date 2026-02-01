package com.example.villageevo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.ui.components.VilvoLoading
import com.example.villageevo.ui.components.home.IconMap
import com.example.villageevo.ui.components.home.PaginationSection
import com.example.villageevo.viewmodel.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

@SuppressLint("ConfigurationScreenWidthHeight", "AutoboxingStateCreation")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GameViewModel,
    mapViewModel: MapViewModel,
    soldierViewModel: SoldierViewModel,
    npcViewModel: NpcViewModel,
) {
    // 1. PINDAHKAN SEMUA COLLECT KE ATAS (Top-Level)
    val mapMetaData by viewModel.metaList.collectAsStateWithLifecycle()
    val mapData by viewModel.mapData.collectAsStateWithLifecycle()
    val mapResourceList by viewModel.mapResource.collectAsStateWithLifecycle()
    val mapSoldier by soldierViewModel.mapSoldierDisplay.collectAsStateWithLifecycle()
    val mapUserResource by mapViewModel.getUserResource.collectAsStateWithLifecycle()

    // 2. Gunakan remember & derivedStateOf untuk filtering berat
    var selectedMeta by remember { mutableStateOf<MapMetaData?>(null) }
    var currentPage by remember { mutableStateOf(1) }

    var idMapSelected by remember { mutableStateOf(0) }

    var isLoading by remember { mutableStateOf(false) }

    // Efek filter resource agar tidak dihitung ulang setiap frame
    val filteredResources = remember(selectedMeta, mapResourceList, mapUserResource) {
        val csvData = mapResourceList.filter { it.idMap == selectedMeta?.id }
        val sqlData = mapUserResource.filter { it.idMap == selectedMeta?.id }
        sqlData.ifEmpty { csvData }
    }
    // Ambil tinggi layar
    val configuration = LocalConfiguration.current
    val cardHeight = configuration.screenHeightDp.dp / 3

    // Inisialisasi data awal
    LaunchedEffect(Unit) {
        viewModel.getMapData()
    }

    // Fetch data hanya saat ID berubah
    LaunchedEffect(selectedMeta?.id) {
        selectedMeta?.id?.let { id ->
            mapViewModel.dataMapUserById(id)
            soldierViewModel.getSoldierByIdMap(id)
        }
    }

    LaunchedEffect(idMapSelected, isLoading) {
        if(idMapSelected>0 && !isLoading && mapResourceList.isNotEmpty()){
            navController.navigate("map/$idMapSelected")
        }
    }
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // GRID SECTION
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(3f).fillMaxHeight().padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            // Logika Pagination: Menampilkan item berdasarkan halaman
            val startIndex = (currentPage - 1) * 9
            items(9) { index ->
                val actualIndex = startIndex + index
                Box(modifier = Modifier.height(cardHeight).background(Color.DarkGray, RoundedCornerShape(12.dp))) {
                    if (actualIndex < mapMetaData.size) {
                        val meta = mapMetaData[actualIndex]
                        MapItemCard(
                            meta,
                            isSelected = selectedMeta == meta,
                            onPress = { selectedMeta = meta }
                        )
                    } else {
                        EmptyMapCard(actualIndex)
                    }
                }
            }
        }

        // DETAIL COLUMN
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight().background(Color.DarkGray).padding(10.dp)
        ) {
            Text(when(selectedMeta!=null){
                true -> selectedMeta!!.title
                false -> "Village Evolution"
            },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center, color = Color.White,
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(filteredResources.size){index->
                    val source = filteredResources[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        IconMap(source.name)
                        Box(modifier = Modifier.width(5.dp))
                        Text(text = source.sum.toString(), style = MaterialTheme.typography.labelSmall, color = Color.White)
                    }
                }
            }
            // INFO SOLDIER
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f).padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(mapSoldier.size){index->
                    val soldier = mapSoldier[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        IconMap(soldier.name, Color.Red.copy(0.5f))
                        Box(modifier = Modifier.width(5.dp))
                        Text(text = soldier.sum.toString(), style = MaterialTheme.typography.labelSmall, color = Color.White)
                    }
                }
            }
            // ACTION BUTTON
            Button(
                enabled = selectedMeta != null && !isLoading,
                onClick = {
                        if (mapSoldier.isEmpty()){
                            selectedMeta?.let { meta ->
                                isLoading = true
                                scope.launch {
                                    try {
                                        if(mapUserResource.isEmpty()){
                                            mapViewModel.insertUserMap(meta, mapResourceList,mapData, )
                                            npcViewModel.saveNpcFirst()
                                            delay(500)
                                        }
                                        mapViewModel.dataMapUserById(meta.id)
                                        idMapSelected = meta.id
                                    }catch (e:Exception) {
                                        e.printStackTrace()
                                    }finally {
                                        isLoading = false
                                    }
                                }
                            }
                        }else{
                            // Logika untuk mode "BATTLE" (Langsung navigasi)
//                            selectedMeta?.let { meta ->
//                                navController.navigate("map_screen/${meta.id}")
//                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan, contentColor = Color.Black)
            ) {
                Text(if (mapSoldier.isNotEmpty()) "BATTLE" else "MASUK")
            }

            // PAGINATION
            PaginationSection(
                currentPage = currentPage,
                maxPage = ceil(mapMetaData.size.toDouble() / 9).toInt(),
                onNext = { currentPage++ },
                onBack = { if (currentPage > 1) currentPage-- }
            )
        }
    }

    if(isLoading){
        VilvoLoading()
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