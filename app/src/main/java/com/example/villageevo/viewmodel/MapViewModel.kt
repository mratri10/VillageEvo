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

    private val _getSource = MutableStateFlow(emptyList<SourceEntity>())
    var getSource = _getSource.asStateFlow()


    fun insertUserMap(
        mapMetaUser: MapMetaData,
        mapResourceList: List<MapResourceEntity>,
        mapDataList: List<MapDataEntity>,
    ){
        viewModelScope.launch(Dispatchers.IO){
            repository.saveToMapUserResource(mapMetaUser, mapResourceList, mapDataList)
        }
    }

    fun saveUserMap(mapDataUser: MapDataEntity, required:List<SourceEntity>){
        viewModelScope.launch(Dispatchers.IO){
            repository.saveMapUserData(mapDataUser, required)
        }
    }
    fun dataMapUserById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resource = repository.getMapUserResource(id)
                val data = repository.getMapUserData(id)
                val potential = repository.getPotentialSource()

                // Ambil data meta, jika null jangan dipaksa (!!), gunakan default
                val meta = repository.getMapMetaUser(id)

                // Update state secara aman
                _getUserResource.value = resource
                _getUserData.value = data
                _getPotential.value = potential
                _getSource.value =repository.getSourceData()

                if (meta != null) {
                    _getUserMeta.value = meta
                } else {
                    // Opsional: Log atau set ke meta kosong jika data tidak ditemukan
                    android.util.Log.w("MapViewModel", "Metadata untuk ID $id tidak ditemukan")
                }
            } catch (e: Exception) {
                android.util.Log.e("MapViewModel", "Error loading data: ${e.message}")
            }
        }
    }

    fun turnProcess(potentialData: List<PotentialData>, requiredData:List<SourceEntity>, mapData:List<MapDataWorker>){
        try {
            val potentialMap = potentialData.associateBy { it.idMap }

            val sourceList = potentialData.groupBy { it.name }.map { (name, items) ->
                val sumTotal = items.sumOf {
                    if(it.totalValue>it.totalSource) it.totalSource else it.totalValue
                }
                SourceEntity(
                    id = 0,
                    params = mapNameToParam(name),
                    value = sumTotal
                )
            }
            val mapDataEntities = mapData.map { worker ->
                val potential = potentialMap[worker.id]
                val convertAbility = getAbilityConvertValue(worker.name)

                val newValue = if (potential != null && convertAbility > 0) {
                    val calculatedValue = worker.value - (potential.totalValue / convertAbility)
                    maxOf(0.0, calculatedValue)
                }
                else {
                    worker.value
                }
                worker.toMapDataEntity(newValue)
            }

            viewModelScope.launch ( Dispatchers.IO){
                repository.turnProcess(sourceList, requiredData, mapDataEntities, )
                dataMapUserById(getUserMeta.value.id)
            }

        }catch (e: Exception){
            print("Failed when save data: $e")
        }
    }

}

// Helper to keep logic clean
private fun mapNameToParam(name: String) = when (name.lowercase()) {
    "forest" -> BuildEvoParams.WOOD
    "farm", "wild" -> BuildEvoParams.FOOD
    "stone" -> BuildEvoParams.STONE
    "gold" -> BuildEvoParams.GOLD
    "iron" -> BuildEvoParams.IRON
    else -> BuildEvoParams.TURN
}

private fun getAbilityConvertValue(name: String) = when (name.lowercase()) {
    "forest" -> AbilitySource.forest.convert
    "wild", "farm" -> AbilitySource.wild.convert
    "stone", "gold", "iron" -> AbilitySource.stone.convert
    else -> 0
}
private fun MapDataWorker.toMapDataEntity(newValue: Double): MapDataEntity {
    return MapDataEntity(
        id = this.id,
        idMap = this.idMap,
        name = this.name,
        x = this.x,
        y = this.y,
        value = newValue
    )
}