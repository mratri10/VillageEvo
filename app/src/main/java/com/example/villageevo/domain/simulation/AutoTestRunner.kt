package com.example.villageevo.domain.simulation

import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.economy.EconomicEngine
import com.example.villageevo.domain.economy.ResourceType
import com.example.villageevo.domain.worker.WorkerType

object AutoTestRunner {

    data class SimulationConfig(
            val turns: Int = 1000,
            val investPerTurn: Long = 100,
            val sellRicePerTurn: Long = 50,
            val sellLumberPerTurn: Long = 30
    )

    data class SimulationResult(
            val finalGold: Long,
            val finalWorkers: Int,
            val educatedWorkers: Int,
            val uneducatedWorkers: Int,
            val goldHistory: List<Long>
    )

    fun run(cityState: CityState, config: SimulationConfig = SimulationConfig()): SimulationResult {

        val engine = EconomicEngine()
        val goldHistory = mutableListOf<Long>()

        repeat(config.turns) { turn ->

            // --- Production ---
            engine.processTurn(cityState)

            // --- Market selling ---
            engine.sellCommodity(cityState, ResourceType.FOOD, config.sellRicePerTurn)
            engine.sellCommodity(cityState, ResourceType.LUMBER, config.sellLumberPerTurn)

            // --- Market investment ---
            if (cityState.resources.gold > config.investPerTurn) {
                engine.investMarket(cityState, config.investPerTurn)
            }

            goldHistory.add(cityState.resources.gold)
        }

        val educated = cityState.workers.count { it.type != WorkerType.UNEDUCATED }

        return SimulationResult(
                finalGold = cityState.resources.gold,
                finalWorkers = cityState.workers.size,
                educatedWorkers = educated,
                uneducatedWorkers = cityState.workers.size - educated,
                goldHistory = goldHistory
        )
    }
}
