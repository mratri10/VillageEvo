package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.building.Coordinate
import com.example.villageevo.domain.map.MapData
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResource
import com.example.villageevo.repository.MapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Screen {
    CITY,
    HOME
}

class GameViewModel(private val repository: MapRepository) : ViewModel() {
    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _metaList = MutableStateFlow<List<MapMetaData>>(emptyList())
    val metaList = _metaList.asStateFlow()

    private val _mapData = MutableStateFlow<List<MapData>>(emptyList())
    val mapData = _mapData.asStateFlow()

    private val _mapResource = MutableStateFlow<List<MapResource>>(emptyList())
    val mapResource = _mapResource.asStateFlow()

    init {
        getMapData()
    }

    fun showMap(): List<Coordinate> {
        // Mapping MapData to Coordinate for UI compatibility
        return _mapData.value.map { data ->
            val type =
                    try {
                        BuildingType.valueOf(data.name.uppercase())
                    } catch (e: IllegalArgumentException) {
                        BuildingType.FOREST // Fallback
                    }
            Coordinate(
                    buildingType = type,
                    x = data.x,
                    y = data.y,
                    building =
                            com.example.villageevo.domain.building.Building(
                                    sum = data.value,
                                    workers = 0,
                                    destroy = 0
                            )
            )
        }
    }

    fun openHome() {
        _currentScreen.value = Screen.HOME
    }

    fun getMapData() {
        viewModelScope.launch {
            _metaList.value = repository.getMapMetadata()
            _mapData.value = repository.getMapData()
            _mapResource.value = repository.getMapResource()
        }
    }
}
