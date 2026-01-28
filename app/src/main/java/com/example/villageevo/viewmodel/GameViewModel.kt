package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResourceEntity
import com.example.villageevo.repository.MapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class GameViewModel(private val repository: MapRepository) : ViewModel() {
    private val _metaList = MutableStateFlow<List<MapMetaData>>(emptyList())
    val metaList = _metaList.asStateFlow()

    private val _mapData = MutableStateFlow<List<MapDataEntity>>(emptyList())
    val mapData = _mapData.asStateFlow()

    private val _mapResource = MutableStateFlow<List<MapResourceEntity>>(emptyList())
    val mapResource = _mapResource.asStateFlow()

    fun getMapData() {
        viewModelScope.launch {
            _metaList.value = repository.getMapMetadata()
            _mapData.value = repository.getMapData()
            _mapResource.value = repository.getMapResource()
        }
    }
}
