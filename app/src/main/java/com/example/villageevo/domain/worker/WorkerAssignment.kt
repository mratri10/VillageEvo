package com.example.villageevo.domain.worker

import com.example.villageevo.domain.building.BuildingType

data class WorkerAssignment(
    val building: BuildingType,
    val workerCount: Int=0
)
