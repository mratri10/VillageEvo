package com.example.villageevo.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.villageevo.R
import com.example.villageevo.domain.building.BuildConvert
import com.example.villageevo.domain.building.BuildEvoParams
import com.example.villageevo.domain.building.SourceEntity
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.PotentialData
import com.example.villageevo.util.LocalSizeApp

@Composable
fun IndicatorMap(
    userMeta: MapMetaDataEntity,
    potentialData: List<PotentialData>,
    source: List<SourceEntity>,
    onClick: (required: List<SourceEntity>) -> Unit
) {
    val sizeApp = LocalSizeApp.current

    val isContinue = remember { mutableStateOf(false) }
    val sourcePotential = remember (potentialData) {
        potentialData.groupBy { it.name }.map { (name, items) ->
            val sumTotal = items.sumOf {
                minOf(it.totalValue, it.totalSource)
            }
            SourceEntity(
                params = BuildConvert.stringToBuildEvoParams(name),
                value = sumTotal
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = userMeta.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(sizeApp.paddingSmall)
        )
        GridSource(
            source = source,
            modifier = Modifier.weight(1f)
        )

        Button(
            modifier = Modifier.fillMaxWidth().padding(sizeApp.paddingSmall),
            onClick= { isContinue.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text("Turn")
        }
    }
    if(isContinue.value){
        PotentialView(
            source,
            sourcePotential, onClick={it->
            onClick(it)
            isContinue.value= false
        }, onCancel = {isContinue.value= false})
    }
}

@Composable
fun GridSource(source: List<SourceEntity>, modifier: Modifier){
    val sizeApp = LocalSizeApp.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier,
        contentPadding = PaddingValues(sizeApp.paddingSmall),
        verticalArrangement = Arrangement.spacedBy(sizeApp.paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(sizeApp.paddingSmall),
    ) {
        items(source){item ->
            Row(modifier = Modifier
                .background(
                    Color.Gray,
                    RoundedCornerShape(sizeApp.paddingSmall)
                )
                .padding(sizeApp.paddingSmall / 3),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,) {
                when(item.params){
                    BuildEvoParams.FOOD -> Image(
                        painter = painterResource(R.drawable.food),
                        contentDescription = "Food",
                        modifier = Modifier.size(sizeApp.iconSize/3)
                    )
                    BuildEvoParams.WOOD -> Image(
                        painter = painterResource(R.drawable.wood),
                        contentDescription = "Wood",
                        modifier = Modifier.size(sizeApp.iconSize/3)
                    )
                    BuildEvoParams.STONE -> Image(
                        painter = painterResource(R.drawable.rock),
                        contentDescription = "Rock",
                        modifier = Modifier.size(sizeApp.iconSize/3)
                    )
                    BuildEvoParams.IRON -> Image(
                        painter = painterResource(R.drawable.iron),
                        contentDescription = "Iron",
                        modifier = Modifier.size(sizeApp.iconSize/3)
                    )
                    BuildEvoParams.GOLD -> Image(
                        painter = painterResource(R.drawable.gold),
                        contentDescription = "Gold",
                        modifier = Modifier.size(sizeApp.iconSize/3)
                    )
                    BuildEvoParams.TURN -> Image(
                        painter = painterResource(R.drawable.vilvo),
                        contentDescription = "Food",
                        modifier = Modifier.size(sizeApp.iconSize/3)
                    )
                    BuildEvoParams.POPULATION -> Image(
                        painter = painterResource(R.drawable.farmer),
                        contentDescription = "Food",
                        modifier = Modifier.size(sizeApp.iconSize/3)
                    )
                }
                Text(item.value.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(Color.White))
            }
        }
    }
}

@Composable
fun PotentialView(
    source:List<SourceEntity>,
    sourcePotential: List<SourceEntity>, onClick: (req: List<SourceEntity>) -> Unit, onCancel:()->Unit){
    val sizeApp = LocalSizeApp.current

    val requiredList = BuildConvert.requiredSource(sourcePotential)
    var showNoResourceAlert by remember { mutableStateOf(false) }

    if (showNoResourceAlert) {
        AlertDialog(
            onDismissRequest = { showNoResourceAlert = false },
            title = { Text("Not Enough Resource") },
            text = { Text("You need more resources.") },
            confirmButton = {
                TextButton(onClick = { showNoResourceAlert = false }) {
                    Text("Ok")
                }
            }
        )
    }
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White).padding(sizeApp.paddingSmall)) {
        Text("Are you sure you want to continue",
            style = MaterialTheme.typography.bodyMedium.copy(),
            textAlign = TextAlign.Center)
        Box(modifier = Modifier.weight(1f)) { }
        Column (
            Modifier
                .fillMaxWidth()
        ){
            Text("Required", style = MaterialTheme.typography.labelSmall)
            GridSource(requiredList, Modifier)
            Spacer(modifier = Modifier.padding(sizeApp.paddingSmall))
            Text("Result", style = MaterialTheme.typography.labelSmall)
            GridSource(sourcePotential,Modifier)
        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick={
                    if(BuildConvert.isAvailableBuild(source, requiredList)){
                        onClick(requiredList)
                    }else{
                        showNoResourceAlert = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )

            ) {
                Text("Yes", style = MaterialTheme.typography.labelSmall)
            }

            Button(onCancel,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )) {
                Text("No", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
