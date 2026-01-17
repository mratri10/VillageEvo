package com.example.villageevo.domain.stage

object StageRepository {
    fun getSeasons(): List<SeasonConfig>{
        return listOf(
            SeasonConfig(
                1, "Agriculture Age",
                listOf(
                    StageConfig(1, 5000, 500, 500,20),
                    StageConfig(2, 9000, 900, 800,22),
                )
            ),
            SeasonConfig(
                2, "Industrial Age",
                listOf(
                    StageConfig(1, 15000, 1500, 2000,25),
                    StageConfig(2, 25000, 2000, 3500,28),
                )
            )
        )
    }
}