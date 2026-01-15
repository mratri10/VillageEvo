package com.example.villageevo.domain.country

import com.example.villageevo.domain.city.Resources


data class CountryProfile(
    val id: String,
    val name: String,

    val productionMultiplier: Float,
    val operationalCostMultiplier: Float,
    val populationGrowthMultiplier: Float,
    val disasterChance: Float,

    val startingResource: Resources,
    val locked: Boolean = false
)
