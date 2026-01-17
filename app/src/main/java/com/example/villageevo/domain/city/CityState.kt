package com.example.villageevo.domain.city

import com.example.villageevo.domain.building.Building
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.country.CountryProfile
import com.example.villageevo.domain.economy.ResourceType
import com.example.villageevo.domain.era.Era
import com.example.villageevo.domain.market.MarketLevel
import com.example.villageevo.domain.market.MarketState
import com.example.villageevo.domain.turn.TurnState
import com.example.villageevo.domain.worker.Worker


data class CityState(
    val countryProfile: CountryProfile,
    val currentEra: Era,
    val turnState: TurnState,

    val population: Int,
    val availableWorkers:Int,
    var marketLevel: MarketLevel,
    var investedGold: Int,
    val happiness: Float = 1.0f,
    val technologyLevel:Int = 0,
    val schoolLevel: Int = 0,
    var gold: Long,

    val resources: MutableMap<ResourceType, Long> = mutableMapOf(
        ResourceType.FOOD to 0,
        ResourceType.LUMBER to 0
    ),
    val buildings: MutableList<Building> = mutableListOf(),
    val workers: MutableList<Worker> = mutableListOf(),

    val workerAssignments: MutableMap<String, MutableList<Worker>> = mutableMapOf(),

    val marketState: MarketState = MarketState(),

){
    fun getBuilding(type: BuildingType): Building? {
        return buildings.firstOrNull { it.type == type }
    }
}
