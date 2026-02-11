package com.example.villageevo.domain.building

import com.example.villageevo.domain.map.PotentialData

object BuildConvert {
    fun stringToBuildEvoParams(name: String): BuildEvoParams {
        return when (name.lowercase()) {
            "wild", "farm" -> BuildEvoParams.FOOD
            "forest"     -> BuildEvoParams.WOOD
            "stone"      -> BuildEvoParams.STONE
            "iron"       -> BuildEvoParams.IRON
            "gold"       -> BuildEvoParams.GOLD
            "population" -> BuildEvoParams.POPULATION
            else         -> BuildEvoParams.TURN
        }
    }

    fun isAvailableBuild(source: List<SourceEntity>, required:List<SourceEntity>): Boolean{
        val availableMap = source.groupBy { it.params }
            .mapValues{entry -> entry.value.sumOf { it.value }}

        val needMap = required.groupBy { it.params }
            .mapValues { entry ->  entry.value.sumOf { it.value } }

        return needMap.all{(param, amoundNeeded) ->
            val amountAvailable = availableMap[param] ?:0.0
            amountAvailable >= amoundNeeded
        }
    }

    fun requiredSource(potentialList: List<SourceEntity>): List<SourceEntity> {
        // 1. Ambil nilai potensi untuk masing-masing resource sebagai pengali
        val potentialWood = potentialList.find { it.params == BuildEvoParams.WOOD }?.value ?: 0.0
        val potentialIron = potentialList.find { it.params == BuildEvoParams.IRON }?.value ?: 0.0
        // Tambahkan yang lain jika ada, misal Gold atau Stone

        // 2. Hitung kebutuhan spesifik berdasarkan kategori
        val costs = mutableListOf<SourceEntity>()

        // Hitung biaya untuk Wood (Biaya Food)
        BuildRequired.npcWood.forEach { req ->
            costs.add(SourceEntity(params = req.required, value = req.value * potentialWood))
        }

        // Hitung biaya untuk Iron (Biaya Food dan Wood)
        BuildRequired.npcIron.forEach { req ->
            // Perbaikan: Gunakan potentialIron untuk biaya yang bersumber dari Iron
            costs.add(SourceEntity(params = req.required, value = req.value * potentialIron))
        }

        // 3. Agregasi: Gabungkan semua biaya yang parameternya sama (misal semua FOOD digabung)
        return costs.groupBy { it.params }
            .map { (param, entities) ->
                SourceEntity(params = param, value = entities.sumOf { it.value })
            }
    }
}