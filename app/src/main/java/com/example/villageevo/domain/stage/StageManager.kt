package com.example.villageevo.domain.stage

import com.example.villageevo.domain.city.CityState

class StageManager(private val city: CityState) {
    private val seasons = StageRepository.getSeasons()
    val state = StageState()

    fun getCurrentStageConfig(): StageConfig {
        val season = seasons.first { it.seasonId == state.currentSeason }
        return season.stages.first { it.stageId == state.currentStage }
    }

    fun onTurnPassed() {
        if (state.isCompleted || state.isFailed) return

        state.turnUsed++

        val cfg = getCurrentStageConfig()

        val food = city.resources.food
        val lumber = city.resources.lumber
        val gold = city.resources.gold

        if (gold >= cfg.targetGold && food >= cfg.targetFood && lumber >= cfg.targetLumber) {
            completeStage()
            return
        }

        if (state.turnUsed >= cfg.maxTurns) {
            failStage()
        }
    }

    private fun completeStage() {
        state.isCompleted = true
    }
    private fun failStage() {
        state.isFailed = true
    }
    fun retryStage() {
        state.turnUsed = 0
        state.isFailed = false
    }

    fun nextStageOrSeason() {
        val season = seasons.first { it.seasonId == state.currentSeason }
        if (state.currentStage < season.stages.size) {
            state.currentStage++
        } else {
            state.currentSeason++
            state.currentStage = 1
        }

        state.turnUsed = 0
        state.isCompleted = false
        state.isFailed = false
    }
}
