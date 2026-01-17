package com.example.villageevo.domain.building

import com.example.villageevo.domain.worker.WorkerRole

data class BuildingDefinition(
    val type: BuildingType,
    val name: String,
    val buildCostGold: Int,
    val buildCostLumber: Int,
    val baseProduction: Int,
    val baseOperationalCost: Int,
    val requiredWorkers: Int,
    val description: String

    )
