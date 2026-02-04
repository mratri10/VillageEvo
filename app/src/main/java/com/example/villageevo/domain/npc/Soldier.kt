package com.example.villageevo.domain.npc

data class Soldier(
    val id:Int,
    val name: String,
    val weakId: Int,
)

data class MapSoldier(
    val id:Int,
    val idMap:Int,
    val idSoldier:Int,
    val sum:Int,
)

data class MapSoldierDisplay(
    val name: String,
    val sum: Int,
    val idSoldier: Int,
    val weakId: Int,
)