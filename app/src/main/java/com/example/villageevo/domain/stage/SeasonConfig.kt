package com.example.villageevo.domain.stage

data class SeasonConfig(
    val seasonId: Int,
    val name: String,
    val stages: List<StageConfig>
)
