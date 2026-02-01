package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import com.example.villageevo.domain.building.BuildDataEntity
import com.example.villageevo.domain.building.BuildEvoEntity
import com.example.villageevo.domain.building.MapBuildEntity

@Dao
interface BuildDao {

    @Insert
    suspend fun insertBuildEvo(buildEvo: BuildEvoEntity): Long

    @Insert
    suspend fun insertBuildData(buildData: BuildDataEntity): Long

    @Insert
    suspend fun insertMapBuild(mapBuild: MapBuildEntity): Long
}