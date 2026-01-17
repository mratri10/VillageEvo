package com.example.villageevo.domain.building

data class BuildingDefinition(
        val type: BuildingType,
        val name: String,
        val buildCostGold: Long,
        val buildCostLumber: Long,
        val baseProduction: Long,
        val baseOperationalCost: Long,
        val requiredWorkers: Int,
        val description: String
)
