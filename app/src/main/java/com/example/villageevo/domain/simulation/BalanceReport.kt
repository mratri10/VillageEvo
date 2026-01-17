package com.example.villageevo.domain.simulation

object BalanceReport {

    fun print(result: AutoTestRunner.SimulationResult) {
        println("====== BALANCE REPORT ======")
        println("Final Gold        : ${result.finalGold}")
        println("Total Workers     : ${result.finalWorkers}")
        println("Educated Workers  : ${result.educatedWorkers}")
        println("Uneducated Workers: ${result.uneducatedWorkers}")

        println("\nGold Progress Snapshot:")
        val step = result.goldHistory.size / 10
        result.goldHistory.forEachIndexed { index, value ->
            if (index % step == 0) {
                println("Turn $index -> Gold: $value")
            }
        }
        println("============================")
    }
}
