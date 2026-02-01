package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.soldier.MapSoldierDisplay
import com.example.villageevo.domain.soldier.Soldier
import com.example.villageevo.repository.SoldierRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SoldierViewModel(private val repository: SoldierRepository) : ViewModel() {

    private val _soldier = MutableStateFlow<Soldier?>(null)
    val soldier = _soldier.asStateFlow()

    private val _mapSoldierDisplay = MutableStateFlow<List<MapSoldierDisplay>>(emptyList())
    val mapSoldierDisplay = _mapSoldierDisplay.asStateFlow()

    fun getSoldierByIdMap(idMap: Int){
        viewModelScope.launch {
            val allMapSoldier = repository.getMapSoldier().filter { it.idMap == idMap }
            val allSoldierDetails = repository.getSoldier()

            val combinedData = allMapSoldier.map{mapSoldier->
                val detail = allSoldierDetails.find{it.id == mapSoldier.idSoldier}
                MapSoldierDisplay(
                    name = detail?.name ?: "Unknown",
                    sum = mapSoldier.sum,
                    idSoldier = mapSoldier.idSoldier,
                    weakId = detail?.weakId ?:0
                )
            }
            _mapSoldierDisplay.value = combinedData
        }
    }

    fun getSoldierById(idSoldier:Int){
        viewModelScope.launch {
            _soldier.value = repository.getSoldier().first() { it.id == idSoldier }
        }
    }
}