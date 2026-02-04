package com.example.villageevo.repository

import androidx.room.Transaction
import com.example.villageevo.db.NpcDao
import com.example.villageevo.domain.npc.NpcAbilityEntity
import com.example.villageevo.domain.npc.NpcAssignEntity
import com.example.villageevo.domain.npc.NpcEntity
import com.example.villageevo.domain.npc.NpcMap

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
    suspend fun saveNpcAssign(npcAssignList: List<NpcAssignEntity>){
        npcDao.insertNpcAssign(npcAssignList)
    }

    suspend fun countNpc(): Int{
        return npcDao.countNpc()
    }

    suspend fun getNpcAbility(page: Int, pageSize: Int): List<NpcMap> {
        // Calculate offset: Page 1 starts at 0, Page 2 starts at 20, etc.
        val offset = (page - 1) * pageSize
        return npcDao.getNpcAbilityPaged(pageSize, offset)
    }

    suspend fun getNpcAssign(npcList:List<Int>): List<NpcAssignEntity> {
        return npcDao.getNpcAssign(npcList)
    }

}