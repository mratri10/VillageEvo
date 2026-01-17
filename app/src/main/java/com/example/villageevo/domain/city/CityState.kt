package com.example.villageevo.domain.city

import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.country.CountryProfile
import com.example.villageevo.domain.era.Era
import com.example.villageevo.domain.market.MarketLevel
import com.example.villageevo.domain.market.MarketState
import com.example.villageevo.domain.turn.TurnState
import com.example.villageevo.domain.worker.Worker

data class CityState(
        val countryProfile: CountryProfile,
        val currentEra: Era = Era.AGRICULTURE,
        val turnState: TurnState = TurnState(),
        val population: Int = 0,
        val availableWorkers: Int = 0,
        var marketLevel: MarketLevel = MarketLevel.LEVEL_1,
        var investedGold: Long = 0,
        val happiness: Float = 1.0f,
        val technologyLevel: Int = 0,
        val schoolLevel: Int = 0,
        val resources: Resources = Resources(),
        val buildings: Map<BuildingType, Int> = emptyMap(),
        val workers: MutableList<Worker> = mutableListOf(),
        val workerAssignments: MutableMap<String, MutableList<Worker>> = mutableMapOf(),
        val marketState: MarketState = MarketState(),
) {
    fun getBuildingCount(type: BuildingType): Int {
        return buildings[type] ?: 0
    }
}
