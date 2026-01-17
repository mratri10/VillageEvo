package com.example.villageevo.domain.building

object BuildingRepository {
    fun getDefinition(type: BuildingType): BuildingDefinition{
        return when (type){
            BuildingType.HOUSE -> BuildingDefinition(
                type = type,
                name = "House",
                buildCostGold = 80,
                buildCostLumber = 20,
                baseProduction = 0,
                baseOperationalCost = 0,
                requiredWorkers = 0,
                description = "Rumah untuk 5 penduduk"
            )
            BuildingType.FARM -> BuildingDefinition(
                type = type,
                name = "Farm",
                buildCostGold = 60,
                buildCostLumber = 15,
                baseProduction = 10,
                baseOperationalCost = 4,
                requiredWorkers = 3,
                description = "Sumber makanan utama"
            )
            BuildingType.LUMBER_MILL -> BuildingDefinition(
                type = type,
                name = "Lumber Mill",
                buildCostGold = 80,
                buildCostLumber = 20,
                baseProduction = 10,
                baseOperationalCost = 8,
                requiredWorkers = 4,
                description = "Produksi kayu utama untuk konstruksi"
            )
            BuildingType.MARKET -> BuildingDefinition(
                type = type,
                name = "Market",
                buildCostGold = 120,
                buildCostLumber = 30,
                baseProduction = 0,
                baseOperationalCost = 2,
                requiredWorkers = 2,
                description = "Pusat perdagangan"
            )
            BuildingType.SCHOOL -> BuildingDefinition(
                type = type,
                name = "School",
                buildCostGold = 200,
                buildCostLumber = 50,
                baseProduction = 0,
                baseOperationalCost = 5,
                requiredWorkers = 1,
                description = "Meningkatkan efisiensi biaya operasional."
            )
            else -> BuildingDefinition(type, "Unknown", 9999, 9999, 0,0,0,"")

        }
    }
}