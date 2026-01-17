package com.example.villageevo.domain.stage

data class StageState(
    var currentSeason: Int = 1,
    var currentStage: Int = 1,
    var turnUsed: Int = 0,
    var isFailed: Boolean = false,
    var isCompleted: Boolean = false
)
