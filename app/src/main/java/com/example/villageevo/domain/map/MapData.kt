package com.example.villageevo.domain.map

import androidx.room.Entity

data class MapMetaData (
    val id: Int,
    val title:String,
    val description:String,
)

data class MapTile(
    val id:Int,
    val idMap: String,
    val name: String,
    val value: Int,
    val x: Int,
    val y: Int,
)

@Entity("map_resource")
data class MapResource(
    val id:Int,
    val idMap: String,
    val name: String,
    val sum: Int,
)