package com.example.villageevo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.villageevo.domain.city.CityState
import com.example.villageevo.domain.economy.BaseEconomyData
import com.example.villageevo.domain.economy.MarketEngine
import com.example.villageevo.domain.turn.TurnManager
import com.example.villageevo.domain.worker.WorkerAssignment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel (initialCity: CityState): ViewModel() {
    private val _cityState = MutableStateFlow(initialCity)
    val cityState: StateFlow<CityState> = _cityState

    private var assignments:List<WorkerAssignment> = emptyList()

    fun nextTurn(){
        _cityState.value = TurnManager.nextTurn(
            _cityState.value,
            assignments,
            BaseEconomyData.production,
            BaseEconomyData.operationalCost
        )
    }

    fun sell(food:Int, lumber:Int){
        _cityState.value = MarketEngine.sell(_cityState.value, food, lumber)
    }

    fun invest(amount:Int){
        _cityState.value = MarketEngine.invest(_cityState.value, amount)
    }
    fun openMarket(){
    //        TODO("Not yet implemented")
    }
    fun openWorkers(){
    //    TODO("Not yet implemented")

    }
}