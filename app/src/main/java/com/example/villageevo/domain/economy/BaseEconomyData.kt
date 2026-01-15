package com.example.villageevo.domain.economy

object BaseEconomyData {
    val production = mapOf(
        "FARM" to 10,
        "LUMBER_MILL" to 10,
        "FACTORY" to 20
    )

    val operationalCost = mapOf(
        "FARM" to 4,
        "LUMBER_MILL" to 8,
        "FACTORY" to 15
    )
}