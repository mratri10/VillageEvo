package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.villageevo.domain.npc.NpcAbilityEntity
import com.example.villageevo.domain.npc.NpcAssignEntity
import com.example.villageevo.domain.npc.NpcEntity
import com.example.villageevo.domain.npc.NpcMap

@Dao
interface NpcDao {
    @Insert
    suspend fun insertNpc(npc: NpcEntity): Long

    @Insert
    suspend fun insertNpcAbility(npc: NpcAbilityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpcAssign(npcAssignList: List<NpcAssignEntity>): List<Long>

    @Query("SELECT COUNT(*) FROM npc")
    suspend fun countNpc(): Int

    @Query("SELECT * FROM npc_assign" +
            " WHERE idNpc IN (:npcList)")
    suspend fun  getNpcAssign(npcList:List<Int>): List<NpcAssignEntity>


    @Query("""
    SELECT 
        n.id, 
        n.year,
        MAX(CASE WHEN a.idAbility = 1 THEN a.level ELSE 0 END) AS carpenter,
        MAX(CASE WHEN a.idAbility = 2 THEN a.level ELSE 0 END) AS farmer,
        MAX(CASE WHEN a.idAbility = 3 THEN a.level ELSE 0 END) AS miner,
        MAX(CASE WHEN a.idAbility = 4 THEN a.level ELSE 0 END) AS infantry,
        MAX(CASE WHEN a.idAbility = 5 THEN a.level ELSE 0 END) AS archer,
        MAX(CASE WHEN a.idAbility = 6 THEN a.level ELSE 0 END) AS spearman,
        MAX(CASE WHEN a.idAbility = 7 THEN a.level ELSE 0 END) AS calvary
    FROM npc n
    LEFT JOIN npc_ability a ON n.id = a.idNpc
    GROUP BY n.id, n.year
    ORDER BY n.id ASC
    LIMIT :limit OFFSET :offset
""")
    suspend fun getNpcAbilityPaged(limit: Int, offset: Int): List<NpcMap>
}