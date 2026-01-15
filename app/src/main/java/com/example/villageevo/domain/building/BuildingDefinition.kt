package com.example.villageevo.domain.building

import com.example.villageevo.domain.worker.WorkerRole

data class BuildingDefinition(
    val type: BuildingType,
    val buildGoldCost: Int,
    val buildLumberCost:Int,

    val upgradeGoldCost:List<Int>,
    val requiredWorkers:Int,

    val allowedRoles: List<WorkerRole>,
    val baseProduction:Int,
    val baseOperationalCost:Int

    )
