package com.example.villageevo.domain.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("map_user_metadata")
data class MapUserMetaDataEntity(
        @PrimaryKey val id: Int,
        val idMapTemplate: Int,
        var customTitle: String,
        var customDescription: String
)

@Entity("map_user_data")
data class MapUserDataEntity(
        @PrimaryKey val id: Int,
        val idUserMap: Int,
        val name: String,
        val value: Int,
        val x: Int,
        val y: Int,
)

@Entity("map_metadata")
data class MapMetaDataEntity(@PrimaryKey val id: Int, val title: String, val description: String)

@Entity("map_data")
data class MapDataEntity(
        @PrimaryKey val id: Int,
        val idMap: Int,
        val name: String,
        val value: Int,
        val x: Int,
        val y: Int,
)

@Entity("map_resource")
data class MapResourceEntity(
        @PrimaryKey val id: Int,
        val idMap: Int,
        val name: String,
        val sum: Int,
)
