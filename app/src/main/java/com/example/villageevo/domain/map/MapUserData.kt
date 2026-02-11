package com.example.villageevo.domain.map

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("map_user_metadata")
data class MapMetaDataEntity(
        @PrimaryKey val id: Int,
        var title: String,
        var description: String
)

@Entity("map_user_data",
    indices = [
        Index(value=["idMap", "x", "y"], unique = true)
    ])
data class MapDataEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val idMap: Int,
        val name: String,
        val value: Double,
        val x: Int,
        val y: Int,
)
@Entity("map_user_resource")
data class MapResourceEntity(
        @PrimaryKey val id: Int,
        val idMap: Int,
        val name: String,
        val sum: Int,
)
