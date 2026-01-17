package com.example.villageevo.domain.stage

data class StageConfig (
    val stageId: Int,
    val targetGold: Long,
    val targetFood: Long,
    val targetLumber: Long,
    val maxTurns:Int
)