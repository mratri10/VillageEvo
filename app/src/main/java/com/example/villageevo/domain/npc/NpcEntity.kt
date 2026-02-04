package com.example.villageevo.domain.npc

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("npc")
data class NpcEntity(
    @PrimaryKey val id: Int,
    val year: Int,
)
@Entity("npc_ability")
data class NpcAbilityEntity(
    @PrimaryKey val id: Int,
    var idNpc: Int,
    val idAbility: Int,
    val level:Int,
)

@Entity("npc_assign")
data class NpcAssignEntity(
    @PrimaryKey val id: Int,
    val idNpc: Int,
    val idMapUser: Int,
)