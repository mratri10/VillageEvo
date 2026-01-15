package com.example.villageevo.tools

import androidx.compose.runtime.mutableStateOf
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.worker.WorkerAssignment

object AutoTestRunner {
    fun runScenarios(baseCity: CityState, assignment: List<WorkerAssignment>):List<BalanceResult>{
        val results = mutableListOf<BalanceResult>()
        listOf(10,20,50).forEach { turns ->
            val endCity = SimulationEngine.simulate(baseCity, assignment, turns)
            results.add(
                BalanceResult(
                    turns,
                    gold = endCity.resources.gold,
                    lumber = endCity.resources.lumber,
                    food = endCity.resources.food
                )
            )
        }
        return results
    }
}
