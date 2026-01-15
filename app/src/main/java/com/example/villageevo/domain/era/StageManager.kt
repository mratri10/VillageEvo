package com.example.villageevo.domain.era

import com.example.villageevo.domain.city.CityState

object StageManager {
    fun isStageCleared(city: CityState, requirement: EreRequirement): Boolean{
        return city.resources.gold >= requirement.targetGold &&
                city.resources.lumber >= requirement.targetLumber
    }
    fun isStageFailed(city: CityState, requirement: EreRequirement): Boolean{
        return city.turnState.currentTurn >= requirement.maxTurns && !isStageCleared(city, requirement)
    }

    fun resetStage(city: CityState, startResourcesGold: Int, startResourcesLumber: Int): CityState{
        return city.copy(
            resources = city.resources.copy(
                gold = startResourcesGold,
                lumber = startResourcesLumber
            ),
            turnState = city.turnState.copy(
                currentTurn = 0
            ),
            investedGold = 0
        )
    }
}