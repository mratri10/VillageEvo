package com.example.villageevo.domain.map

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MapMetaData (
    val id: Int,
    val title:String,
    val description:String,
)
data class PotentialData(
    val name:String,
    val totalValue:Int,
    val idMap:Int
)

data class MapDataWorker(
    val id: Int,
    val idMap: Int,
    val name: String,
    val value: Double,
    val x: Int,
    val y: Int,
    val worker: Int,
)
data class ParamSource(
    var name:String,
    var abilityId:Int,
    var value:String,
    var convert:Int,
)