package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.building.BuildingRepository
import com.example.villageevo.domain.building.Coordinate
import com.example.villageevo.domain.building.MapCategory
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.city.Resources
import com.example.villageevo.domain.country.CountryProfile
import com.example.villageevo.domain.era.Era
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapUserDao
import com.example.villageevo.domain.turn.TurnManager
import com.example.villageevo.domain.turn.TurnState
import com.example.villageevo.domain.worker.WorkerAssignment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    CITY,
    HOME
}

class GameViewModel(private val repository: MapUserDao) : ViewModel() {
    private val _selectedMapId = MutableStateFlow(1)

    private val _cityState = MutableStateFlow(createInitialCity())
    val cityState: StateFlow<CityState> = _cityState.asStateFlow()

    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _metadataList = MutableStateFlow<List<MapMetaDataEntity>>(emptyList())
    val metadataList = _metadataList.asStateFlow()

    val activeMapMetadata =
            combine(_selectedMapId, _metadataList) { id, list -> list.find { it.id == id } }
                    .stateIn(viewModelScope, SharingStarted.Lazily, null)

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

    fun showMap(): List<Coordinate> {
        val listCoordinate: List<Coordinate> =
                BuildingRepository.getMapCategory(type = MapCategory.GRASS_FOREST)
        return listCoordinate
    }

    fun changeMap(id: Int) {
        _selectedMapId.value = id
    }

    fun getMapData() {
        println("++++++++++++++")
        viewModelScope.launch { _metadataList.value = repository.getMapMetadata() }

        println("+++++++++++++ ${_metadataList.value}")
    }

    fun nextTurn() {
        val assignments: List<WorkerAssignment> = emptyList() // Placeholder for worker logic

        val newState = TurnManager.nextTurn(_cityState.value, assignments)
        _cityState.value = newState
    }
    fun openCity() {
        println("+++++++++++ OPEN CITY ++++++++++")
        _currentScreen.value = Screen.CITY
    }
    fun openHome() {
        _currentScreen.value = Screen.HOME
    }
}
