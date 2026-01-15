package com.example.villageevo.domain.city

import com.example.villageevo.domain.country.CountryProfile

object CountryRepository{
    val ideaCountry = CountryProfile(
        id = "ideal",
        name = "Ideal Country",
        productionMultiplier = 1.0f,
        operationalCostMultiplier = 1.0f,
        populationGrowthMultiplier = 1.0f,
        disasterChance = 0.02f,
        startingResource = Resources(gold=1000, lumber=100),
        locked = false
    )
}