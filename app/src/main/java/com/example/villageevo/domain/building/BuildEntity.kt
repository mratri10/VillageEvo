package com.example.villageevo.domain.building

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("map_build")
data class MapBuildEntity(
    @PrimaryKey val id: Int,
    var idMap: Int,
    var typeBuild: String,
    var x:Int,
    var y:Int,
)

@Entity("build_data")
data class BuildDataEntity(
    @PrimaryKey val id: Int,
    var idBuild:Int,
    var level:Int,
)

@Entity("build_evo")
data class BuildEvoEntity(
    @PrimaryKey val id: Int,
    var year:Int,
    var npc: Int,
    var wood: Int,
    var food: Int,
    var gold: Int,
    var iron: Int,
    var rock: Int,
)

