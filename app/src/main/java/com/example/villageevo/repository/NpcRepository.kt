package com.example.villageevo.repository

import androidx.room.Transaction
import com.example.villageevo.db.NpcDao
import com.example.villageevo.domain.npc.NpcAbilityEntity
import com.example.villageevo.domain.npc.NpcAssignEntity
import com.example.villageevo.domain.npc.NpcEntity
import com.example.villageevo.domain.npc.NpcMap
import com.example.villageevo.domain.npc.TotalNpcAssign

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

    suspend fun getNpcCount(): List<TotalNpcAssign>{
        return npcDao.getTotalNpcAssign()
    }

    suspend fun getNpcAbility(
        page: Int,
        pageSize: Int = 10,
        orderBy: String = "",
        filterWoodier: Int = 0,
        filterFarmer: Int = 0,
        filterMiner: Int = 0,
        filterInfantry: Int = 0,
        filterArcher: Int = 0,
        filterCalvary: Int = 0,
        filterSpearman: Int = 0
    )
    : List<NpcMap> {
        // Calculate offset: Page 1 starts at 0, Page 2 starts at 20, etc.
        val offset = (page - 1) * pageSize
        return npcDao.getNpcWithOrderAndFilter(
            pageSize, offset,
            orderBy,
            filterWoodier,
            filterFarmer,
            filterMiner,
            filterInfantry,
            filterArcher,
            filterCalvary,
            filterSpearman
        )
    }

    @Transaction
    suspend fun updateAssignToNol(){
        npcDao.updateNpcAssignToNol()
    }

}