package com.example.villageevo.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Transaction
import com.example.villageevo.db.BuildDao
import com.example.villageevo.domain.building.BuildDataEntity
import com.example.villageevo.domain.building.BuildEvoEntity
import com.example.villageevo.domain.building.MapBuildEntity

class BuildRepository(private val buildDao: BuildDao){

    @Transaction
    suspend fun saveBuildEvo(buildEvo: BuildEvoEntity){
        try {
            buildDao.insertBuildEvo(buildEvo)
        }catch (e:Exception){
            print("Failed when save data: $e")
        }
    }

    @Transaction
    suspend fun saveBuildData(
        mapBuild: MapBuildEntity,
        buildData: BuildDataEntity){
        try {
            val idBuild: Long= buildDao.insertMapBuild(mapBuild)
            buildData.idBuild = idBuild.toInt()
            buildDao.insertBuildData(buildData)
        }catch (e:Exception){
            print("Failed when save data: $e")
        }
    }
}