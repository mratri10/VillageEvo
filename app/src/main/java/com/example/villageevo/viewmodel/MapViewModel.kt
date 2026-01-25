package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.map.MapData
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResource
import com.example.villageevo.domain.map.MapUserDataEntity
import com.example.villageevo.domain.map.MapUserMetaDataEntity
import com.example.villageevo.domain.map.MapUserResourceEntity
import com.example.villageevo.repository.MapUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val repository: MapUserRepository): ViewModel(){

    private val _isUserExist = MutableStateFlow(false)
    val isUserExist = _isUserExist.asStateFlow()

    var getUserResource: List<MapUserMetaDataEntity> = emptyList()


    fun insertUserMap(
        mapMetaUser: MapMetaData,
        mapResource: MapResource,
        mapDataList: List<MapData>,
    ){
        viewModelScope.launch(Dispatchers.IO){
            repository.saveToMapUserResource(mapMetaUser, mapResource, mapDataList)
        }
    }

    fun dataMapUser(){
        try {
            viewModelScope.launch(Dispatchers.IO){
                val dataResource = repository.getMapMetaUser()
                _isUserExist.value = dataResource.isNotEmpty()
                getUserResource = repository.getMapMetaUser()
            }
        }catch (e: Exception){
            _isUserExist.value = false
        }
    }
}