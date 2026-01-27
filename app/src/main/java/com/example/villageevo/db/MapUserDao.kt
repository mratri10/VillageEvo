package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.villageevo.domain.map.MapUserDataEntity
import com.example.villageevo.domain.map.MapUserMetaDataEntity
import com.example.villageevo.domain.map.MapUserResourceEntity

@Dao
interface MapUserDao {
    @Insert
    suspend fun insertUserMetadata(metaData: MapUserMetaDataEntity): Long
    @Insert
    suspend fun insertAllUserData(data: List<MapUserDataEntity>)
    @Insert
    suspend fun insertAllUserResources(data: List<MapUserResourceEntity>)

    @Query("UPDATE map_user_data SET value = value - :value WHERE id = :id")
    suspend fun updateData(id: Int, value: Int)

    @Query("DELETE FROM map_user_data WHERE value <= 0") suspend fun deleteEmptyTiles()

    @Query("SELECT * FROM map_user_metadata")
    suspend fun getUserMeta(): List<MapUserMetaDataEntity>
    @Query("SELECT * FROM map_user_resource WHERE idMap = :idMap")
    suspend fun getUserResource(idMap: Int):List<MapUserResourceEntity>
    @Query("SELECT * FROM map_user_data WHERE idMap = :idMap")
    suspend fun getUserData(idMap: Int):List<MapUserDataEntity>

}