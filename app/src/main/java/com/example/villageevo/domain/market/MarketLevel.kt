package com.example.villageevo.domain.market

enum class MarketLevel (
    val investmentReturnRate: Float,
    val upgradeCost: Int
){
    LEVEL_1(0.04f, 200),
    LEVEL_2(0.07f, 400),
    LEVEL_3(0.10f, 800),
}