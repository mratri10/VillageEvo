package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.soldier.NpcAbilityEntity
import com.example.villageevo.domain.soldier.NpcAssignEntity
import com.example.villageevo.domain.soldier.NpcEntity

@Dao
interface NpcDao {
    @Insert
    suspend fun insertNpc(npc: NpcEntity): Long

    @Insert
    suspend fun insertNpcAbility(npc: NpcAbilityEntity): Long

    @Insert fun insertNpcAssign(npc: NpcAssignEntity): Long

    @Query("SELECT * FROM npc")
    suspend fun getNpc(): List<NpcEntity>

    @Query("SELECT * FROM npc_assign WHERE idMapUser = :idMapUser")
    suspend fun  getNpcAssign(idMapUser: Int): List<NpcAssignEntity>

}