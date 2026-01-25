package com.example.villageevo.repository

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Transaction
import com.example.villageevo.db.MapUserDao
import com.example.villageevo.domain.map.MapData
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResource
import com.example.villageevo.domain.map.MapUserDataEntity
import com.example.villageevo.domain.map.MapUserMetaDataEntity
import com.example.villageevo.domain.map.MapUserResourceEntity
import com.example.villageevo.viewmodel.GameViewModel

class MapUserRepository(
    private val mapUserDao: MapUserDao
) {

    @Transaction
    suspend fun saveToMapUserResource(
        mapMeta: MapMetaData,
        mapResource: MapResource,
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
        val mapUserResource = MapUserResourceEntity(
            id = mapResource.id,
            idMap = mapResource.idMap,
            name = mapResource.name,
            sum = mapResource.sum
        )
        try {
            mapUserDao.insertUserResources(mapUserResource)
            mapUserDao.insertAllUserData(mapUserDataList)
            mapUserDao.insertUserMetadata(mapMetaUser)
        }catch (e: Exception){
            print("Failed when save data: $e")
        }
    }

    suspend fun getMapUserResource(idMap:Int): List<MapUserResourceEntity>{
        return mapUserDao.getUserResource(idMap)
    }
    suspend fun getMapMetaUser(): List<MapUserMetaDataEntity>{
        return mapUserDao.getUserMeta()
    }

}