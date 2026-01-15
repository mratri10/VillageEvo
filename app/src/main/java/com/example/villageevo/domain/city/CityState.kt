package com.example.villageevo.domain.city

import com.example.villageevo.data.model.BuildingType
import com.example.villageevo.domain.country.CountryProfile
import com.example.villageevo.domain.era.Era
import com.example.villageevo.domain.market.MarketLevel
import com.example.villageevo.domain.turn.TurnState

data class CityState(
    val countryProfile: CountryProfile,
    val currentEra: Era,
    val turnState: TurnState,
    val resources: Resources,
    val population: Int,
    val availableWorkers:Int,
    val buildings: Map<BuildingType, Int>,
    val marketLevel: MarketLevel,
    val investedGold: Int,
    val happiness: Float = 1.0f,
    val technologyLevel:Int = 0
)
