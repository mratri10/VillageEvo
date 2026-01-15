package com.example.villageevo.tools

data class BalanceResult (
    val turns:Int,
    val gold:Int,
    val lumber:Int,
    val food:Int
)
object BalanceReport{
    fun print(result: List<BalanceResult>){
        result.forEach {
            println("Turn: ${it.turns} | Gold: ${it.gold} | Lumber: ${it.lumber} | Food: ${it.food}")
        }
    }
}