package com.example.villageevo.viewmodel

import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.data.model.BuildingType
import com.example.villageevo.data.model.VillageState
import com.example.villageevo.game.GameConfig
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ceil

class VillageViewModel : ViewModel() {
    private val _state = MutableStateFlow(VillageState.initial())
    val state: StateFlow<VillageState> = _state

    init{
        startGameLoop()
    }

    private fun startGameLoop(){
        viewModelScope.launch {
            while(true){
                delay(GameConfig.TICK_INTERVAL_MS)
                produceResources()
            }
        }
    }

    private fun produceResources(){
        _state.update { current ->
            var foodGain = 0
            var woodGain = 0
            var goldgain = current.population

            current.buildings.forEach { (type, level) ->
                when(type){
                    BuildingType.FARM -> foodGain += level * type.productionPerTick
                    BuildingType.LUMBER_MILL -> woodGain += level * type.productionPerTick
                    BuildingType.MARKET -> goldgain += level * type.productionPerTick
                    else -> {}
                }
            }

            current.copy(
                food = current.food + foodGain,
                wood = current.wood + woodGain,
                gold = current.gold + goldgain,
                lastUpdated = System.currentTimeMillis()
            )
        }
    }

    fun buildBuilding(type: BuildingType){
        _state.update { current ->
            println("Gold1: ${current.gold}")
            val currentLevel = current.buildings[type] ?:0
            val cost = calculateBuildingCost(type, currentLevel)
            if(current.gold < cost) return@update current

            val newBuilding = current.buildings.toMutableMap().apply {
                this[type] = currentLevel + 1
            }.toPersistentMap()

            current.copy(
                gold = current.gold - cost,
                buildings = newBuilding
            )
        }
    }

    private fun calculateBuildingCost(type: BuildingType, level: Int):Int{
        return ceil(type.baseCost*Math.pow(GameConfig.COST_MULTIPLIER, level.toDouble())).toInt()
    }
}