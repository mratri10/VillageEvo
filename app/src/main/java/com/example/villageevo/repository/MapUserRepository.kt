package com.example.villageevo.repository

import androidx.room.Transaction
import com.example.villageevo.db.MapUserDao
import com.example.villageevo.domain.building.BuildConvert
import com.example.villageevo.domain.building.BuildEvoParams
import com.example.villageevo.domain.building.SourceEntity
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapDataWorker
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity
import com.example.villageevo.domain.map.PotentialData
import com.example.villageevo.util.AbilitySource

class MapUserRepository(
    private val mapUserDao: MapUserDao
) {
    @Transaction
    suspend fun saveToMapUserResource(
        mapMeta: MapMetaData,
        mapResourceList: List<MapResourceEntity>,
        mapDataList: List<MapDataEntity>
    ){
        val mapUserDataList: List<MapDataEntity> = mapDataList.map { entity ->
            MapDataEntity(
                id = entity.id,
                idMap = entity.idMap,
                name = entity.name,
                value = entity.value,
                x = entity.x,
                y = entity.y
            )
        }
        val mapMetaUser = MapMetaDataEntity(
            id = mapMeta.id,
            title = mapMeta.title,
            description = mapMeta.description
        )
        val mapUserResourceList: List<MapResourceEntity> = mapResourceList.map { entity ->
            MapResourceEntity(
                id = entity.id,
                idMap = entity.idMap,
                name = entity.name,
                sum = entity.sum
            )
        }
        try {
            mapUserDao.insertAllUserResources(mapUserResourceList)
            mapUserDao.insertAllUserData(mapUserDataList)
            mapUserDao.insertUserMetadata(mapMetaUser)
        }catch (e: Exception){
            print("Failed when save data: $e")
        }
    }

    @Transaction
    suspend fun saveMapUserData(mapDataUser: MapDataEntity, required:List<SourceEntity>){
        mapUserDao.insertMapDataUser(mapDataUser)
        required.forEach{
            val rowsUpdated = mapUserDao.updateResource(it.params.toString(), it.value)
            if (rowsUpdated == 0) {
                mapUserDao.insertSource(SourceEntity(params = it.params, value = it.value))
            }
        }
    }
    @Transaction
    suspend fun insertSourceData(params: BuildEvoParams, value: Double){
        mapUserDao.insertSource(SourceEntity(params=params, value=value))
    }
    @Transaction
    suspend fun turnProcess(sources: List<SourceEntity>, requiredList: List<SourceEntity>, maps: List<MapDataEntity>){
        mapUserDao.runTurnTransaction(sources, requiredList, maps)
    }
    suspend fun getMapUserResource(idMap:Int): List<MapResourceEntity>{
        return mapUserDao.getUserResource(idMap)
    }
    suspend fun getMapUserData(idMap:Int): List<MapDataWorker>{
        return mapUserDao.getUserData(idMap)
    }
    suspend fun getMapMetaUser(id:Int)=mapUserDao.getUserMeta(id).firstOrNull()
    suspend fun getPotentialSource():List<PotentialData>{
        return mapUserDao.getPotentialSource(
            forestVal = AbilitySource.forest.convert,
            wildVal = AbilitySource.wild.convert,
            farmVal = AbilitySource.farm.convert,
            stoneVal = AbilitySource.stone.convert,
            goldVal = AbilitySource.gold.convert,
            ironVal = AbilitySource.iron.convert
        )
    }

    suspend fun getSourceData():List<SourceEntity>{
        return mapUserDao.getSource()
    }
}