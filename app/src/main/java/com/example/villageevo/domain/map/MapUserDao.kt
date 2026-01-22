package com.example.villageevo.domain.map

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MapUserDao {
    @Insert suspend fun insertMetadata(metaData: MapUserMetaDataEntity): Long
    @Insert suspend fun insertData(data: MapUserDataEntity): Long

    @Insert suspend fun insertAllMetadata(list: List<MapMetaDataEntity>)
    @Insert suspend fun insertAllMapData(list: List<MapDataEntity>)
    @Insert suspend fun insertAllResources(list: List<MapResourceEntity>)

    @Query("UPDATE map_user_data SET value = value - :value WHERE id = :id")
    suspend fun updateData(id: Int, value: Int)

    @Query("DELETE FROM map_user_data WHERE value <= 0") suspend fun deleteEmptyTiles()

    @Query("SELECT * FROM map_user_data") fun observeUserTiles(): Flow<List<MapUserDataEntity>>

    @Query("SELECT * FROM map_metadata") suspend fun getMapMetadata(): List<MapMetaDataEntity>

    @Query("SELECT * FROM map_data WHERE id = :idMap")
    suspend fun getMapData(idMap: Int): List<MapDataEntity>

    @Query("SELECT * FROM map_resource WHERE id = :idMap")
    suspend fun getMapResource(idMap: Int): List<MapResourceEntity>
}
