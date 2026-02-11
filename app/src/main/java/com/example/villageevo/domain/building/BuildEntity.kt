package com.example.villageevo.domain.building

import androidx.room.Entity
import androidx.room.Index
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

@Entity("source",
    indices = [Index(value=["params"], unique = true)]
)
data class SourceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    var params: BuildEvoParams,
    var value: Double,
)
