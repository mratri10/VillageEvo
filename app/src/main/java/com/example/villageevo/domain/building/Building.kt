package com.example.villageevo.domain.building

data class Building(
    val id: String,
    val type: BuildingType,
    var level: Int = 1,
    val baseProduction: Long,
    val maxWorkers: Int
)

