package com.example.villageevo.domain.building

object BuildRequired {
    val house:List<BuildData> = listOf(
        BuildData(BuildEvoParams.WOOD, 50.0),
    )
    val npc:List<BuildData> = listOf(
        BuildData(BuildEvoParams.FOOD, 15.0),
    )
    val npcWood:List<BuildData> = listOf(
        BuildData(BuildEvoParams.FOOD, 0.1),
    )
    val npcIron:List<BuildData> = listOf(
        BuildData(BuildEvoParams.FOOD, 0.05),
        BuildData(BuildEvoParams.WOOD, 0.01),
    )
    val npcFood:List<BuildData> = listOf()
}

