package com.example.villageevo.domain.map

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MapMetaData (
    val id: Int,
    val title:String,
    val description:String,
)
