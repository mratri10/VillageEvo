package com.example.villageevo.domain.market

data class MarketState(
    var investedGold: Long = 0,
    val sellOrders: MutableList<SellOrder> = mutableListOf()
)

data class SellOrder(
    val amount: Long,
    val pricePerUnit: Long
)
