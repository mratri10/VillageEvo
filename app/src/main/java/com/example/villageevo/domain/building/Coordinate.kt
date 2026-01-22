package com.example.villageevo.domain.building

data class Coordinate (
    val buildingType: BuildingType,
    val x: Int,
    val y: Int,
    val building: Building
)