package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.villageevo.domain.building.BuildingRepository
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.city.Resources
import com.example.villageevo.domain.country.CountryProfile
import com.example.villageevo.domain.era.Era
import com.example.villageevo.domain.turn.TurnManager
import com.example.villageevo.domain.turn.TurnState
import com.example.villageevo.domain.worker.WorkerAssignment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class Screen {
    CITY,
    MARKET,
    WORKERS
}

class GameViewModel : ViewModel() {

    private val _cityState = MutableStateFlow(createInitialCity())
    val cityState: StateFlow<CityState> = _cityState.asStateFlow()

    private val _currentScreen = MutableStateFlow(Screen.CITY)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    init {
        // any other init logic
    }

    private fun createInitialCity(): CityState {
        // Create a dummy/starter profile
        val starterProfile =
                CountryProfile(
                        id = "starter",
                        name = "New Village",
                        productionMultiplier = 1.0f,
                        operationalCostMultiplier = 1.0f,
                        populationGrowthMultiplier = 1.0f,
                        disasterChance = 0.0f,
                        startingResource = Resources(gold = 500, lumber = 200, food = 200)
                )

        return CityState(
                countryProfile = starterProfile,
                currentEra = Era.AGRICULTURE,
                turnState = TurnState(),
                resources = starterProfile.startingResource
        )
    }

    fun nextTurn() {
        val assignments: List<WorkerAssignment> = emptyList() // Placeholder for worker logic

        val newState = TurnManager.nextTurn(_cityState.value, assignments)
        _cityState.value = newState
    }

    fun buyBuilding(type: BuildingType) {
        val current = _cityState.value
        val definition = BuildingRepository.getDefinition(type)

        if (current.resources.gold >= definition.buildCostGold &&
                        current.resources.lumber >= definition.buildCostLumber
        ) {
            val newResource =
                    current.resources.copy(
                            gold = current.resources.gold - definition.buildCostGold,
                            lumber = current.resources.lumber - definition.buildCostLumber
                    )
            val currentCount = current.buildings[type] ?: 0
            val newBuildings =
                    current.buildings.toMutableMap().apply { put(type, currentCount + 1) }

            _cityState.value = current.copy(resources = newResource, buildings = newBuildings)
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

    fun getBuildingCount(type: BuildingType): Int {
        return cityState.value.getBuildingCount(type)
    }
}
