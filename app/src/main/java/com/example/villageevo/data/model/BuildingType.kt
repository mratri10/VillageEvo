package com.example.villageevo.data.model

enum class BuildingType (
    val displayName: String,
    val baseCost: Int,
    val productionPerTick: Int
){
    FARM("Farm", 50, 3),
    HOUSE("House",80, 0),
    MARKET("Market", 120, 2),
    LUMBER_MILL("Lumber Mill", 100, 4),
    SCHOOL("School", 150, 0)

}