package com.example.villageevo.domain.economy

import com.example.villageevo.domain.city.CityState

object MarketEngine {
    fun sell(
        city: CityState,
        sellFood: Int,
        sellLumber:Int
    ): CityState{
        val food = minOf(city.resources.food, sellFood)
        val lumber = minOf(city.resources.lumber, sellLumber)

        val goldGain = food *1 + lumber*2

        val newResources = city.resources.copy(
            food = city.resources.food - food,
            lumber = city.resources.lumber - lumber,
            gold = city.resources.gold +goldGain
        )

        return city.copy(resources = newResources)
    }

    fun invest(city: CityState, amount: Int): CityState{
        val investable = minOf(amount, (city.resources.gold*0.3f).toInt())
        val newResources = city.resources.copy(
            gold = city.resources.gold - investable
        )
        return city.copy(
            resources = newResources,
            investedGold = city.investedGold+investable
        )
    }

    fun processInvestment(city: CityState): CityState{
        val rate= city.marketLevel.investmentReturnRate
        val profit = (city.investedGold * rate *city.countryProfile.productionMultiplier).toInt()
        val newResources = city.resources.copy(
            gold = city.resources.gold + profit
        )

        return city.copy(resources = newResources)
    }

}