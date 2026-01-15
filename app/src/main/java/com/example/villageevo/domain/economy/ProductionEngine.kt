package com.example.villageevo.domain.economy

import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.city.Resources
import com.example.villageevo.domain.worker.WorkerAssignment

object ProductionEngine{
    fun produce(
        city: CityState,
        assignments: List<WorkerAssignment>,
        baseProductionMap:Map<String, Int>,
        baseCostMap: Map<String, Int>
    ): Pair<Resources, Int>{
        var totalGoldDelta = 0
        var newResources = city.resources

        assignments.forEach { assignment ->
            val workers = assignment.workers

            if(workers.isEmpty()) return@forEach

            val avgProdMulti = workers.map{it.education.productionMultiplier}.average().toFloat()
            val avgCostMulti = workers.map{it.education.costMultiplier}.average().toFloat()

            val baseProd = baseProductionMap[assignment.buildingType.name] ?:0
            val baseCost = baseCostMap[assignment.buildingType.name] ?:0

            val realProduction = (baseProd * avgProdMulti * city.countryProfile.productionMultiplier ).toInt()
            val realCost = (baseCost * avgCostMulti * city.countryProfile.operationalCostMultiplier).toInt()

            when(assignment.buildingType.name){
                "FARM" -> newResources = newResources.copy(food = newResources.food + realProduction)
                "LUMBER_MILL" -> newResources = newResources.copy(lumber = newResources.lumber + realProduction)
                "FACTORY" -> newResources = newResources.copy(goods = newResources.goods + realProduction)

            }

            totalGoldDelta -= realCost
        }
        return newResources to totalGoldDelta
    }
}