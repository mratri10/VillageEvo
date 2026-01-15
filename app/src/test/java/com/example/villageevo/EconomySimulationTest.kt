package com.example.villageevo

import com.example.villageevo.data.model.BuildingType
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.city.CountryRepository
import com.example.villageevo.domain.city.Resources
import com.example.villageevo.domain.era.Era
import com.example.villageevo.domain.market.MarketLevel
import com.example.villageevo.domain.turn.TurnState
import com.example.villageevo.domain.worker.Worker
import com.example.villageevo.domain.worker.WorkerAssignment
import com.example.villageevo.domain.worker.WorkerEducation
import com.example.villageevo.domain.worker.WorkerRole
import com.example.villageevo.tools.AutoTestRunner
import com.example.villageevo.tools.BalanceReport
import org.junit.Test

class EconomySimulationTest {
    @Test
    fun runEconomySimulation(){
        val cityState = CityState(
            countryProfile = CountryRepository.ideaCountry,
            currentEra = Era.AGRICULTURE,
            turnState = TurnState(),
            resources = Resources(gold=1000, lumber=100),
            population = 20,
            availableWorkers = 20,
            buildings = mapOf(
                BuildingType.FARM to 2,
                BuildingType.LUMBER_MILL to 2
            ),
            marketLevel = MarketLevel.LEVEL_1,
            investedGold = 0
        )

        val workers = List(10){
            Worker(
                id= it.toString(),
                role = WorkerRole.FARMER,
                education = WorkerEducation.NONE
            )
        }

        val assignment = listOf(
            WorkerAssignment(BuildingType.FARM, workers.take(6)),
            WorkerAssignment(BuildingType.LUMBER_MILL, workers.takeLast(4))
        )

        val results = AutoTestRunner.runScenarios(cityState, assignment)
        BalanceReport.print(results)

    }
}