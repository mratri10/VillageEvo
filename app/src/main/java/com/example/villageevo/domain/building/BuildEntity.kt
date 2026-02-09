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

@Entity("source")
data class SourceEntity(
    @PrimaryKey val id: Int,
    var params: BuildEvoParams  ,
    var value: Int,
)
enum class BuildEvoParams{
    FOOD,
    WOOD,
    GOLD,
    IRON,
    TURN,
    POPULATION,
    STONE
}
