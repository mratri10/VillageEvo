package com.example.villageevo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villageevo.domain.building.BuildEvoParams
import com.example.villageevo.domain.building.SourceEntity
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapDataWorker
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity
import com.example.villageevo.domain.map.PotentialData
import com.example.villageevo.repository.MapUserRepository
import com.example.villageevo.util.AbilitySource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList


class MapViewModel(private val repository: MapUserRepository): ViewModel(){

    private val _getUserData = MutableStateFlow(emptyList<MapDataWorker>())
    var getUserData = _getUserData.asStateFlow()

    private val _getUserResource = MutableStateFlow(emptyList<MapResourceEntity>())
    var getUserResource = _getUserResource.asStateFlow()

    private val _getUserMeta = MutableStateFlow(MapMetaDataEntity(0,"",""))
    var getUserMeta = _getUserMeta.asStateFlow()

    private val _getPotential = MutableStateFlow(emptyList<PotentialData>())
    var getPotential = _getPotential.asStateFlow()

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
                _getPotential.value = repository.getPotentialSource()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun turnProcess(potentialData: List<PotentialData>, mapData:List<MapDataWorker>){
        try {
            val sourceList=potentialData.map {data ->
                SourceEntity(
                    id=0,
                    params = when(data.name.lowercase()){
                        "forest" -> BuildEvoParams.WOOD
                        "wild" -> BuildEvoParams.FOOD
                        "farm" -> BuildEvoParams.FOOD
                        "stone" -> BuildEvoParams.STONE
                        "gold" -> BuildEvoParams.GOLD
                        "iron" -> BuildEvoParams.IRON
                        else -> BuildEvoParams.TURN
                    },
                    value = data.totalValue
                )
            }
            val mapDataList = mapData.map{data->
                val potential = potentialData.find { it.idMap == data.id }
                val convertAbility = when(data.name.lowercase()){
                    "forest" -> AbilitySource.forest.convert
                    "wild" -> AbilitySource.wild.convert
                    "farm" -> AbilitySource.farm.convert
                    "stone" -> AbilitySource.stone.convert
                    "gold" -> AbilitySource.gold.convert
                    "iron" -> AbilitySource.iron.convert
                    else -> 0
                }

                MapDataEntity(
                    id = data.id,
                    idMap = data.idMap,
                    name = data.name,
                    x = data.x,
                    y = data.y,
                    value = if(potential==null) data.value
                    else data.value-(potential.totalValue.toDouble()/convertAbility)
                )
            }

            viewModelScope.launch ( Dispatchers.IO){
                repository.turnProcess(sourceList, mapDataList)
            }
        }catch (e: Exception){
            print("Failed when save data: $e")
        }
    }

}