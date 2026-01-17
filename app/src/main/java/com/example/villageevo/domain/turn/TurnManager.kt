package com.example.villageevo.domain.turn

import com.example.villageevo.domain.building.BuildingRepository
import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.worker.WorkerAssignment
import kotlin.math.min

object TurnManager {
    fun nextTurn(
            currentCity: CityState,
            assignments: List<WorkerAssignment>,
    ): CityState {
        val houseCount = currentCity.buildings[BuildingType.HOUSE] ?: 0
        val totalPopulation = houseCount * 5

        val foodConsumption = if (totalPopulation > 0) totalPopulation / 5 else 0

        var producedGold = 0L
        var producedFood = 0L
        var producedLumber = 0L

        val efficiencyMultiplier = getSchoolEfficiency(currentCity.schoolLevel)

        assignments.forEach { assignment ->
            val type = assignment.building
            val assignedWorkers = assignment.workerCount
            val definition = BuildingRepository.getDefinition(type)
            val buildingCount = currentCity.buildings[type] ?: 0

            val workersPerBuilding = definition.requiredWorkers

            val activeBuildings =
                    if (workersPerBuilding > 0) {
                        min(buildingCount, assignedWorkers / workersPerBuilding)
                    } else {
                        buildingCount
                    }

            val totalProduction = activeBuildings * definition.baseProduction
            val baseOpCost = activeBuildings * definition.baseOperationalCost

            val finalOpCost = (baseOpCost * efficiencyMultiplier).toLong()

            producedGold -= finalOpCost

            when (type) {
                BuildingType.FARM -> producedFood += totalProduction.toLong()
                BuildingType.LUMBER_MILL -> producedLumber += totalProduction.toLong()
                else -> {}
            }
        }

        val newGold = currentCity.resources.gold + producedGold
        val newFood = currentCity.resources.food + producedFood
        val newLumber = currentCity.resources.lumber + producedLumber

        return currentCity.copy(
                resources =
                        currentCity.resources.copy(
                                gold = newGold,
                                food = newFood,
                                lumber = newLumber
                        ),
                turnState =
                        currentCity.turnState.copy(
                                currentTurn = currentCity.turnState.currentTurn + 1
                        )
        )
    }

    private fun getSchoolEfficiency(level: Int): Double {
        return when (level) {
            0 -> 1.0
            1 -> 0.85
            2 -> 0.70
            3 -> 0.55
            else -> 0.5
        }
    }
}
