package com.example.villageevo.domain.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("map_user_metadata")
data class MapUserMetaDataEntity(
        @PrimaryKey val id: Int,
        var title: String,
        var description: String
)

@Entity("map_user_data")
data class MapUserDataEntity(
        @PrimaryKey val id: Int,
        val idMap: Int,
        val name: String,
        val value: Int,
        val x: Int,
        val y: Int,
)
@Entity("map_user_resource")
data class MapUserResourceEntity(
        @PrimaryKey val id: Int,
        val idMap: Int,
        val name: String,
        val sum: Int,
)
