package com.example.villageevo.data.model

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

data class VillageState(
    val gold:Int,
    val food: Int,
    val wood: Int,
    val population: Int,
    val happiness: Int,
    val buildings: PersistentMap<BuildingType, Int>,
    val lastUpdated: Long
){
    companion object{
        fun initial(): VillageState{
            return VillageState(
                gold = 100,
                food = 100,
                wood = 100,
                population = 5,
                happiness = 70,
                buildings = persistentMapOf(
                    BuildingType.FARM to 1,
                    BuildingType.HOUSE to 1,
                    BuildingType.MARKET to 0,
                    BuildingType.LUMBER_MILL to 0,
                    BuildingType.SCHOOL to 0
                ),
                lastUpdated = System.currentTimeMillis()
            )
        }
    }
}
