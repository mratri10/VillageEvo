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
import kotlin.collections.emptyList


class MapViewModel(private val repository: MapUserRepository): ViewModel(){

    private val _isUserExist = MutableStateFlow(false)
    val isUserExist = _isUserExist.asStateFlow()

    private val _getUserMeta = MutableStateFlow(emptyList<MapUserMetaDataEntity>())
    var getUserMeta = _getUserMeta.asStateFlow()

    private val _getUserData = MutableStateFlow(emptyList<MapUserDataEntity>())
    var getUserData = _getUserData.asStateFlow()

    private val _getUserResource = MutableStateFlow(emptyList<MapUserResourceEntity>())
    var getUserResource = _getUserResource.asStateFlow()


    fun insertUserMap(
        mapMetaUser: MapMetaData,
        mapResource: List<MapResource>,
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
                _getUserMeta.value = repository.getMapMetaUser()
            }
        }catch (e: Exception){
            e.printStackTrace()
            _isUserExist.value = false
        }
    }

    fun dataMapUserById(id:Int){
        try {
            viewModelScope.launch (Dispatchers.IO ){
                _getUserResource.value = repository.getMapUserResource(id)
                _getUserData.value = repository.getMapUserData(id)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun sumSource(name: String):Int{
        return when (name){
            "forest" ->  {
                getUserResource.value.filter { it.name == "forest" }.sumOf { it.sum }
            }
            "wild" ->  {
                getUserResource.value.filter { it.name == "wild" }.sumOf { it.sum }
            }
            "house" ->  {
                getUserResource.value.filter { it.name == "house" }.sumOf { it.sum }
            }
            else -> 0
        }
    }
}