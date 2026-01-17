package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.villageevo.domain.building.BuildingRepository
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.turn.TurnManager
import com.example.villageevo.domain.worker.Worker
import com.example.villageevo.domain.worker.WorkerAssignment
import com.example.villageevo.domain.worker.WorkerEducation
import com.example.villageevo.domain.worker.WorkerRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class Screen {
    CITY,
    MARKET,
    WORKERS
}

class GameViewModel(initialCity: CityState) : ViewModel() {

    private val _cityState = MutableStateFlow(initialCity)
    val cityState: StateFlow<CityState> = _cityState.asStateFlow()

    private val _currentScreen = MutableStateFlow(Screen.CITY)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()
    private var workerAssignments: List<WorkerAssignment> = emptyList()

    private val workers = mutableListOf<Worker>()
    private var assignments: List<WorkerAssignment> = emptyList()

    init{
        generateInitialWorkers()
    }

    private fun generateInitialWorkers() {
        repeat(20){
            workers.add(
                Worker(
                    id = it.toString(),
                    role = WorkerRole.GENERAL,
                    education = WorkerEducation.NONE
                )
            )
        }
    }

    fun nextTurn() {
        val newState = TurnManager.nextTurn(_cityState.value, workerAssignments)
        _cityState.value = newState
    }

    fun buyBuilding(type: BuildingType){
        val current = _cityState.value
        val definition = BuildingRepository.getDefinition(type)

        if(current.resources.gold >= definition.buildCostGold && current.resources.lumber >= definition.buildCostLumber){
            val newResource = current.resources.copy(
                gold = current.resources.gold - definition.buildCostGold,
                lumber = current.resources.lumber - definition.buildCostLumber
            )
            val currentCount = current.buildings[type] ?: 0
            val newBuildings = current.buildings.toMutableMap().apply {
                put(type, currentCount+1)
            }

            _cityState.value = current.copy(
                resources = newResource,
                buildings = newBuildings
            )
        }
    }

    fun openMarket() {
        _currentScreen.value = Screen.MARKET
    }
    fun openWorkers() {
        _currentScreen.value = Screen.WORKERS
    }
    fun backToCity() {
        _currentScreen.value = Screen.CITY
    }

    private fun refreshState(){
        _cityState.value = _cityState.value.copy()
    }
}
