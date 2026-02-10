package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.villageevo.domain.npc.NpcAbilityEntity
import com.example.villageevo.domain.npc.NpcAssignEntity
import com.example.villageevo.domain.npc.NpcEntity
import com.example.villageevo.domain.npc.NpcMap
import com.example.villageevo.domain.npc.TotalNpcAssign

@Dao
interface NpcDao {
    @Insert suspend fun insertNpc(npc: NpcEntity): Long

    @Insert suspend fun insertNpcAbility(npc: NpcAbilityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpcAssign(npcAssignList: List<NpcAssignEntity>): List<Long>

    @Query("SELECT COUNT(*) FROM npc") suspend fun countNpc(): Int
    @Query("SELECT COUNT(*) FROM npc_assign na " +
            "JOIN map_user_data mud on na.idMapUser = mud.idMap " +
            "WHERE mud.value >0 "+
            "GROUP BY idNpc ")
    suspend fun  countNpcWork():Int

    @Query("""
        SELECT 'totalNpc' As name, COUNT(n.id) AS value FROM npc N
        UNION ALL
        SELECT 'totalNpcWork' As name, COUNT(na.idNpc) AS value FROM npc n
        LEFT JOIN npc_assign na ON na.idNpc = n.id
        WHERE na.idNpc IS NOT NULL
    """)
    suspend fun getTotalNpcAssign(): List<TotalNpcAssign>

    @Query(
            """
    SELECT n.id, n.year, 
        na.idMapUser as idMapUser,
        MAX(CASE WHEN nb.idAbility = 1 THEN nb.level ELSE 0 END) AS wooder,
        MAX(CASE WHEN nb.idAbility = 2 THEN nb.level ELSE 0 END) AS farmer,
        MAX(CASE WHEN nb.idAbility = 3 THEN nb.level ELSE 0 END) AS miner,
        MAX(CASE WHEN nb.idAbility = 4 THEN nb.level ELSE 0 END) AS infantry,
        MAX(CASE WHEN nb.idAbility = 5 THEN nb.level ELSE 0 END) AS archer,
        MAX(CASE WHEN nb.idAbility = 6 THEN nb.level ELSE 0 END) AS calvary,
        MAX(CASE WHEN nb.idAbility = 7 THEN nb.level ELSE 0 END) AS spearman
    FROM npc n
    LEFT JOIN npc_assign na on n.id = na.idNpc
    LEFT JOIN npc_ability nb ON n.id = nb.idNpc
    GROUP BY n.id, n.year, na.idMapUser
    HAVING
        (:filterWoodier = 1 AND wooder > 0 OR :filterWoodier = 0) AND
        (:filterFarmer = 1 AND farmer > 0 OR :filterFarmer = 0) AND
        (:filterMiner = 1 AND miner > 0 OR :filterMiner = 0) AND
        (:filterInfantry = 1 AND infantry > 0 OR :filterInfantry = 0) AND
        (:filterArcher = 1 AND archer > 0 OR :filterArcher = 0) AND
        (:filterCalvary = 1 AND calvary > 0 OR :filterCalvary = 0) AND
        (:filterSpearman = 1 AND spearman > 0 OR :filterSpearman = 0)
    ORDER BY 
        CASE WHEN :orderBy = 'wooder' THEN wooder END DESC,
        CASE WHEN :orderBy = 'farmer' THEN farmer END DESC,
        CASE WHEN :orderBy = 'miner' THEN miner END DESC,
        CASE WHEN :orderBy = 'infantry' THEN infantry END DESC,
        CASE WHEN :orderBy = 'archer' THEN archer END DESC,
        CASE WHEN :orderBy = 'calvary' THEN calvary END DESC,
        CASE WHEN :orderBy = 'spearman' THEN spearman END DESC,
        n.id ASC
    LIMIT :limit OFFSET :offset""")
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
