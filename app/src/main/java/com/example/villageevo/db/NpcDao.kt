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
    SELECT n.id, n.year, 
        MAX(na.idMapUser) as idMapUser, 
        MAX(na.id) as last_na_id,
        MAX(CASE WHEN nb.idAbility = 1 THEN nb.level ELSE 0 END) AS wooder,
        MAX(CASE WHEN nb.idAbility = 2 THEN nb.level ELSE 0 END) AS farmer,
        MAX(CASE WHEN nb.idAbility = 3 THEN nb.level ELSE 0 END) AS miner,
        MAX(CASE WHEN nb.idAbility = 4 THEN nb.level ELSE 0 END) AS infantry,
        MAX(CASE WHEN nb.idAbility = 5 THEN nb.level ELSE 0 END) AS archer,
        MAX(CASE WHEN nb.idAbility = 6 THEN nb.level ELSE 0 END) AS calvary,
        MAX(CASE WHEN nb.idAbility = 7 THEN nb.level ELSE 0 END) AS spearman
    FROM npc n
    LEFT JOIN npc_assign na ON n.id = na.idNpc
    LEFT JOIN npc_ability nb ON n.id = nb.idNpc
    GROUP BY n.id
    HAVING
        (:filterWoodier = 1 AND wooder > 0 OR :filterWoodier = 0) AND
        (:filterFarmer = 1 AND farmer > 0 OR :filterFarmer = 0) AND
        (:filterMiner = 1 AND miner > 0 OR :filterMiner = 0)AND
        (:filterMiner = 1 AND infantry > 0 OR :filterMiner = 0)AND
        (:filterMiner = 1 AND archer > 0 OR :filterMiner = 0)AND
        (:filterMiner = 1 AND calvary > 0 OR :filterMiner = 0)AND
        (:filterMiner = 1 AND spearman > 0 OR :filterMiner = 0)
    ORDER BY 
        CASE WHEN :orderBy = 'wooder' THEN wooder END DESC,
        CASE WHEN :orderBy = 'farmer' THEN farmer END DESC,
        CASE WHEN :orderBy = 'miner' THEN miner END DESC,
        CASE WHEN :orderBy = 'infantry' THEN infantry END DESC,
        CASE WHEN :orderBy = 'archer' THEN archer END DESC,
        CASE WHEN :orderBy = 'calvary' THEN calvary END DESC,
        CASE WHEN :orderBy = 'spearman' THEN spearman END DESC,
        n.id ASC
    LIMIT :limit OFFSET :offset
""")
    suspend fun getNpcWithOrderAndFilter(
        limit: Int,
        offset: Int,
        orderBy: String,
        filterWoodier: Int,
        filterFarmer: Int,
        filterMiner: Int,
        filterInfantry: Int,
        filterArcher: Int,
        filterCalvary: Int,
        filterSpearman: Int,
    ): List<NpcMap>
}