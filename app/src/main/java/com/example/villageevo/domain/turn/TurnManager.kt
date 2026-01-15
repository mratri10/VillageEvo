package com.example.villageevo.domain.turn

import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.economy.MarketEngine
import com.example.villageevo.domain.economy.ProductionEngine
import com.example.villageevo.domain.worker.WorkerAssignment

object TurnManager {
    fun nextTurn(
        city: CityState,
        assignments: List<WorkerAssignment>,
        baseProductionMap: Map<String, Int>,
        baseCostMap: Map<String, Int>
    ): CityState {
        val (newResources, goldDelta) = ProductionEngine.produce(
            city = city,
            assignments = assignments,
            baseProductionMap = baseProductionMap,
            baseCostMap = baseCostMap
        )
        val afterProduction = city.copy(resources = newResources.copy(gold = newResources.gold +goldDelta))

        val afterInvestment = MarketEngine.processInvestment(afterProduction)

        val newTurn = city.turnState.currentTurn +1

        return afterInvestment.copy(
            turnState = city.turnState.copy(currentTurn = newTurn)
        )

    }

}