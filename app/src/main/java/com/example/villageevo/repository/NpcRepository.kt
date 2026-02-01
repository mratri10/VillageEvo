package com.example.villageevo.repository

import androidx.room.Transaction
import com.example.villageevo.db.NpcDao
import com.example.villageevo.domain.soldier.NpcAbilityEntity
import com.example.villageevo.domain.soldier.NpcAssignEntity
import com.example.villageevo.domain.soldier.NpcEntity

class NpcRepository(private val npcDao: NpcDao){

    @Transaction
    suspend fun saveNpcWithAbility(npc: NpcEntity, npcAbility: NpcAbilityEntity){
        try {
            val idNpc: Long = npcDao.insertNpc(npc)
            npcAbility.idNpc = idNpc.toInt()
            npcDao.insertNpcAbility(npcAbility)
        }catch (e:Exception){
            print("Failed when save data: $e")
        }
    }

    @Transaction
    suspend fun saveNpcAssign(npcAssign: NpcAssignEntity){
        try {
            npcDao.insertNpcAssign(npcAssign)
        }catch (e: Exception){
            print("Failed when save data: $e")
        }
    }

    suspend fun getNpc(): List<NpcEntity>{
        return npcDao.getNpc()
    }

}