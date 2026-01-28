package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity

@Dao
interface MapUserDao {
    @Insert
    suspend fun insertUserMetadata(metaData: MapMetaDataEntity): Long
    @Insert
    suspend fun insertAllUserData(data: List<MapDataEntity>)
    @Insert
    suspend fun insertAllUserResources(data: List<MapResourceEntity>)

    @Query("UPDATE map_user_data SET value = value - :value WHERE id = :id")
    suspend fun updateData(id: Int, value: Int)

    @Query("DELETE FROM map_user_data WHERE value <= 0") suspend fun deleteEmptyTiles()

    @Query("SELECT * FROM map_user_metadata")
    suspend fun getUserMeta(): List<MapMetaDataEntity>
    @Query("SELECT * FROM map_user_resource WHERE idMap = :idMap")
    suspend fun getUserResource(idMap: Int):List<MapResourceEntity>
    @Query("SELECT * FROM map_user_data WHERE idMap = :idMap")
    suspend fun getUserData(idMap: Int):List<MapDataEntity>

}