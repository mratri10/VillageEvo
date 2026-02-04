package com.example.villageevo.ui.components.map

import android.annotation.SuppressLint
import android.widget.Button
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.villageevo.R
import com.example.villageevo.domain.npc.NpcAbility
import com.example.villageevo.domain.npc.NpcEntity
import com.example.villageevo.util.ColorApp
import com.example.villageevo.viewmodel.NpcViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun NpcSelect(
    npcViewModel: NpcViewModel,
    idMapUser:Int,
    onClick: (id:Int) -> Unit = {}
){
    val configurationSize = LocalConfiguration.current
    val screenHeight = configurationSize.screenHeightDp

    val listNpc = npcViewModel.getNpcAbilityList.collectAsStateWithLifecycle()
    val listNpcAssign = npcViewModel.getNpcAssign.collectAsStateWithLifecycle()

    val page = remember { mutableIntStateOf(1) }
    val npcSelectList = remember { mutableStateListOf<Int>() }

    LaunchedEffect(listNpc) {
        val idList = listNpc.value.map { it.id }
        npcViewModel.loadNpcAssignByNpcIdList(idList)
    }
    LaunchedEffect(Unit) {
        npcViewModel.loadNpcAbilityList(page.intValue, 20)
    }
    Box (modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight(0.9f)
        .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.96f))){
        Column() {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1f).padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(listNpc.value.size){index->
                    val npc = listNpc.value[index]
                    val isSelected = npcSelectList.contains(npc.id)
                    val colorBorder = if(isSelected) MaterialTheme.colorScheme.primary else Color.White
                    val sizeBorder =if(isSelected) 4.dp else 2.dp
                    val npcBuildList:List<NpcMapAbility> =listOf(
                        NpcMapAbility(name = NpcAbility.FARMER, level = npc.farmer),
                        NpcMapAbility(name = NpcAbility.SPEARMAN, level = npc.carpenter),
                        NpcMapAbility(name = NpcAbility.MINER, level = npc.miner),
                    )
                    val npcBattleList:List<NpcMapAbility> =listOf(
                        NpcMapAbility(name = NpcAbility.ARCHER, level = npc.archer),
                        NpcMapAbility(name = NpcAbility.INFANTRY, level = npc.infantry),
                        NpcMapAbility(name = NpcAbility.SPEARMAN, level = npc.spearman),
                        NpcMapAbility(name = NpcAbility.CALVARY, level = npc.calvary),
                    )
                    val npcAssign = listNpcAssign.value.find { it.idNpc == npc.id }
                    val idMapAssign = npcAssign?.idMapUser?.toString() ?: ""
                    Box(modifier =
                        Modifier
                            .height(screenHeight.dp*0.4f)
                            .border(width = sizeBorder, color=colorBorder, RoundedCornerShape(5.dp))
                            .clickable{
                                if(isSelected){
                                    npcSelectList.remove(npc.id)
                                }else{
                                    npcSelectList.add(npc.id)
                                }
                            }
                    ){
                        Row(Modifier.align(Alignment.TopCenter),
                            horizontalArrangement = Arrangement.SpaceBetween,) {
                            npcBuildList.forEach {
                                TickAbility(it)
                            }
                        }
                        Row(
                            modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(
                                modifier = Modifier.padding(5.dp).background(color= MaterialTheme.colorScheme.primary)
                            ) {
                                Text(idMapAssign, style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White
                                ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            Image(
                                painter = painterResource(R.drawable.farmer),
                                contentDescription = "Farmer",
                                modifier = Modifier.size(50.dp)
                            )
                            Box(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text("", style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White
                                ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                        Row(Modifier.align(Alignment.BottomCenter),
                            horizontalArrangement = Arrangement.SpaceBetween,) {
                            npcBattleList.forEach {
                                TickAbility(it)
                            }
                        }

                    }
                }
            }
        }
        Row(modifier = Modifier.align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(screenHeight.dp*0.2f)) {
            Row(modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Button ({onClick(0)},
                    modifier = Modifier.fillMaxHeight(),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        disabledContentColor = Color.Transparent,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(5.dp)
                ){
                    Icon(
                        imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowLeft,
                        contentDescription = "Close",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(page.intValue.toString(), style= MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.tertiary
                ))
                Button ({onClick(0)},
                    modifier = Modifier.fillMaxHeight(),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        disabledContentColor = Color.Transparent,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(5.dp)
                ){
                    Icon(
                        imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                        contentDescription = "Close",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Box(modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center){
                Button(
                    enabled = npcSelectList.isNotEmpty(),
                    onClick = {
                        npcViewModel.saveNpcAssign(npcSelectList, idMapUser)
                        onClick(0) },
                    modifier = Modifier.fillMaxHeight(),
                ){
                    Text("Assign")
                }
            }
            Box(modifier = Modifier.width(20.dp))
            Button ({onClick(0)},
                modifier = Modifier.fillMaxHeight(),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    disabledContentColor = Color.Transparent,
                    containerColor = MaterialTheme.colorScheme.error,
                    disabledContainerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(5.dp)
            ){
                Box(){
                    Text("X")
                }
            }
        }
    }

}

@Composable
fun TickAbility(ability: NpcMapAbility){
    val colorTick:Color = when(ability.name){
        NpcAbility.FARMER  -> ColorApp.GreenV10
        NpcAbility.CARPENTER  -> ColorApp.BrownV10
        NpcAbility.MINER  -> ColorApp.GoldV10
        NpcAbility.INFANTRY  -> ColorApp.RedV10
        NpcAbility.ARCHER  -> ColorApp.BlueV10
        NpcAbility.CALVARY  -> ColorApp.NavyV20
        NpcAbility.SPEARMAN  -> ColorApp.OrangeV20
    }
    Box(
        modifier = Modifier.padding(5.dp).background(color=colorTick)
    ) {
        Text(ability.level.toString(), style = MaterialTheme.typography.bodySmall.copy(
            color = Color.White
        ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp)
        )
    }
}

data class NpcMapAbility(
    val name: NpcAbility,
    val level:Int
)