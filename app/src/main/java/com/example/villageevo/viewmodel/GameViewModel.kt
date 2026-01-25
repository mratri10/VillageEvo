package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.building.BuildingRepository
import com.example.villageevo.domain.building.Coordinate
import com.example.villageevo.domain.building.MapCategory
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


    fun showMap(): List<Coordinate> {
        val listCoordinate: List<Coordinate> =
                BuildingRepository.getMapCategory(type = MapCategory.GRASS_FOREST)
        return listCoordinate
    }

    fun openHome() {
        _currentScreen.value = Screen.HOME
    }

    fun getMapData() {
        print("+++++++++++ 1 $mapData ")
        viewModelScope.launch { _metaList.value = repository.getMapMetadata() }
    }

    fun getUserData(){
        viewModelScope.launch { _mapData.value = repository.getMapData() }
    }

    fun getUserResource(){
        viewModelScope.launch { _mapResource.value = repository.getMapResource() }
    }

}
