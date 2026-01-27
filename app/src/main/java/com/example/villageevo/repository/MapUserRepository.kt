package com.example.villageevo.repository

import androidx.room.Transaction
import com.example.villageevo.db.MapUserDao
import com.example.villageevo.domain.map.MapData
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResource
import com.example.villageevo.domain.map.MapUserDataEntity
import com.example.villageevo.domain.map.MapUserMetaDataEntity
import com.example.villageevo.domain.map.MapUserResourceEntity

class MapUserRepository(
    private val mapUserDao: MapUserDao
) {
    @Transaction
    suspend fun saveToMapUserResource(
        mapMeta: MapMetaData,
        mapResourceList: List<MapResource>,
        mapDataList: List<MapData>
    ){
        val mapUserDataList: List<MapUserDataEntity> = mapDataList.map { entity ->
            MapUserDataEntity(
                id = entity.id,
                idMap = entity.idMap,
                name = entity.name,
                value = entity.value,
                x = entity.x,
                y = entity.y
            )
        }
        val mapMetaUser = MapUserMetaDataEntity(
            id = mapMeta.id,
            title = mapMeta.title,
            description = mapMeta.description
        )
        val mapUserResourceList: List<MapUserResourceEntity> = mapResourceList.map { entity ->
            MapUserResourceEntity(
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

    suspend fun getMapUserResource(idMap:Int): List<MapUserResourceEntity>{
        return mapUserDao.getUserResource(idMap)
    }
    suspend fun getMapUserData(idMap:Int): List<MapUserDataEntity>{
        return mapUserDao.getUserData(idMap)
    }
    suspend fun getMapMetaUser(): List<MapUserMetaDataEntity>{
        return mapUserDao.getUserMeta()
    }

}