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
            else -> BuildingDefinition(type, "Unknown", 9999, 9999, 0,0,0,"")

        }
    }

    fun  getMapCategory(type: MapCategory): List<Coordinate>{
        return when(type){
            MapCategory.GRASS_FOREST -> listOf(
                Coordinate(BuildingType.HOUSE,3,3,Building(1,5,0)),

                Coordinate(BuildingType.FOREST,0,0,Building(66,0,0)),
                Coordinate(BuildingType.FOREST,0,1,Building(126,0,0)),
                Coordinate(BuildingType.FOREST,0,2,Building(200,0,0)),
                Coordinate(BuildingType.FOREST,1,2,Building(29,0,0)),
                Coordinate(BuildingType.FOREST,2,2,Building(326,0,0)),

                Coordinate(BuildingType.WILD,1,0,Building(260,0,0)),
                Coordinate(BuildingType.WILD,1,1,Building(25,0,0)),
                Coordinate(BuildingType.WILD,2,1,Building(106,0,0)),
                Coordinate(BuildingType.WILD,2,0,Building(80,0,0))
            )
            MapCategory.GRASS_SEA -> listOf(
                Coordinate(BuildingType.HOUSE,0,0,Building(0,0,0)),
            )
            MapCategory.GRASS_RIVER -> listOf(
                Coordinate(BuildingType.FOREST,0,0,Building(0,0,0))
            )
        }
    }
}