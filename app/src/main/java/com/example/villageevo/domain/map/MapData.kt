package com.example.villageevo.domain.map

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MapMetaData (
    val id: Int,
    val title:String,
    val description:String,
)

data class MapData(
    val id:Int,
    val idMap: Int,
    val name: String,
    val value: Int,
    val x: Int,
    val y: Int,
)
data class MapResource(
    val id: Int,
    val idMap: Int,
    val name: String,
    val sum: Int,
)
