package com.example.villageevo.domain.economy

import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.worker.WorkerType

class EconomicEngine {

    fun processTurn(city: CityState) {
        produceFood(city)
        produceLumber(city)
    }

    private fun produceFood(city: CityState) {
        val farms = city.buildings.filter { it.type == BuildingType.FARM }
        var total = 0L

        farms.forEach { building ->
            val workers = city.workerAssignments[building.id] ?: emptyList()
            workers.forEach {
                total += (10 * efficiency(it.type)).toLong()
            }
        }

        city.resources[ResourceType.FOOD] =
            city.resources.getOrDefault(ResourceType.FOOD, 0) + total
    }

    private fun produceLumber(city: CityState) {
        val forests = city.buildings.filter { it.type == BuildingType.FOREST }
        var total = 0L

        forests.forEach { building ->
            val workers = city.workerAssignments[building.id] ?: emptyList()
            workers.forEach {
                total += (10 * efficiency(it.type)).toLong()
            }
        }

        city.resources[ResourceType.LUMBER] =
            city.resources.getOrDefault(ResourceType.LUMBER, 0) + total
    }

    private fun efficiency(type: WorkerType): Double {
        return when (type) {
            WorkerType.UNEDUCATED -> 1.0
            WorkerType.SCHOOL_LV1 -> 0.8
            WorkerType.SCHOOL_LV2 -> 0.6
            WorkerType.SCHOOL_LV3 -> 0.4
        }
    }

    // ================================
    // MARKET
    // ================================

    fun sellCommodity(city: CityState, type: ResourceType, amount: Long) {
        val available = city.resources.getOrDefault(type, 0)
        if (available <= 0) return

        val sellAmount = minOf(amount, available)

        val price = when (type) {
            ResourceType.FOOD -> 1
            ResourceType.LUMBER -> 2
            else -> 0
        }

        city.resources[type] = available - sellAmount
        city.gold += sellAmount * price
    }

    fun investMarket(city: CityState, amount: Long) {
        if (city.gold < amount) return

        val educated = city.workers.count { it.type != WorkerType.UNEDUCATED }
        val total = city.workers.size.coerceAtLeast(1)

        val educationRatio = educated.toDouble() / total

        val baseProfit = 0.03
        val bonusProfit = educationRatio * 0.12

        val profitRate = baseProfit + bonusProfit
        val profit = (amount * profitRate).toLong()

        city.gold -= amount
        city.gold += amount + profit
    }
}
