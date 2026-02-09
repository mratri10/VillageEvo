package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import com.example.villageevo.domain.building.*

@Dao
interface BuildDao {

    @Insert
    suspend fun insertSource(source: SourceEntity): Long

    @Insert
    suspend fun insertBuildData(buildData: BuildDataEntity): Long

    @Insert
    suspend fun insertMapBuild(mapBuild: MapBuildEntity): Long
}