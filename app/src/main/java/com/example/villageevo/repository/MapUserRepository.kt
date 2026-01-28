package com.example.villageevo.repository

import androidx.room.Transaction
import com.example.villageevo.db.MapUserDao
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity

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

    suspend fun getMapUserResource(idMap:Int): List<MapResourceEntity>{
        return mapUserDao.getUserResource(idMap)
    }
    suspend fun getMapUserData(idMap:Int): List<MapDataEntity>{
        return mapUserDao.getUserData(idMap)
    }
    suspend fun getMapMetaUser(): List<MapMetaDataEntity>{
        return mapUserDao.getUserMeta()
    }

}