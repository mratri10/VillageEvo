package com.example.villageevo.domain.parameter

import com.example.villageevo.R

object ParameterRepository {
    fun getParameters(): List<Parameter> {
        return listOf(
            Parameter("Coin", 100, R.drawable.coin),
            Parameter("Food", 100, R.drawable.food),
            Parameter("Wood", 100, R.drawable.wood),
            Parameter("Citizen", 100, R.drawable.farmer),
        )
    }
}