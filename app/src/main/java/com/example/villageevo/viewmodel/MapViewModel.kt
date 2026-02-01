package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity
import com.example.villageevo.repository.MapUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList


class MapViewModel(private val repository: MapUserRepository): ViewModel(){

    private val _isUserExist = MutableStateFlow(false)
    val isUserExist = _isUserExist.asStateFlow()

    private val _getUserData = MutableStateFlow(emptyList<MapDataEntity>())
    var getUserData = _getUserData.asStateFlow()

    private val _getUserResource = MutableStateFlow(emptyList<MapResourceEntity>())
    var getUserResource = _getUserResource.asStateFlow()

    private val _getUserMeta = MutableStateFlow(MapMetaDataEntity(0,"",""))
    var getUserMeta = _getUserMeta.asStateFlow()

    fun insertUserMap(
        mapMetaUser: MapMetaData,
        mapResourceList: List<MapResourceEntity>,
        mapDataList: List<MapDataEntity>,
    ){
        viewModelScope.launch(Dispatchers.IO){
            repository.saveToMapUserResource(mapMetaUser, mapResourceList, mapDataList)
        }
    }
    fun dataMapUserById(id:Int){
        try {
            viewModelScope.launch (Dispatchers.IO ){
                _getUserResource.value = repository.getMapUserResource(id)
                _getUserData.value = repository.getMapUserData(id)
                _getUserMeta.value = repository.getMapMetaUser(id)
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