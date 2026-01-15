package com.example.villageevo.tools

import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.economy.BaseEconomyData
import com.example.villageevo.domain.turn.TurnManager
import com.example.villageevo.domain.worker.WorkerAssignment

object SimulationEngine {
    fun simulate(
        city: CityState,
        assignment: List<WorkerAssignment>,
        turns: Int
    ): CityState {
        var currentCity = city
        repeat(turns) {
            currentCity = TurnManager.nextTurn(
                currentCity,
                assignment,
                BaseEconomyData.production,
                BaseEconomyData.operationalCost
            )
        }
        return currentCity
    }
}