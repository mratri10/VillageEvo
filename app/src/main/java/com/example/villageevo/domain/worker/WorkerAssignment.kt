package com.example.villageevo.domain.worker

import com.example.villageevo.data.model.BuildingType

data class WorkerAssignment(
    val buildingType: BuildingType,
    val workers: List<Worker>
)
