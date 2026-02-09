package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.villageevo.domain.building.SourceEntity
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapDataWorker
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity
import com.example.villageevo.domain.map.PotentialData

@Dao
interface MapUserDao {
    @Insert
    suspend fun insertUserMetadata(metaData: MapMetaDataEntity): Long
    @Insert
    suspend fun insertAllUserData(data: List<MapDataEntity>)
    @Insert
    suspend fun insertAllUserResources(data: List<MapResourceEntity>)

    @Insert
    suspend fun insertAllSource(data: List<SourceEntity>)

    @Update
    suspend fun updateMapUserAllData(data: List<MapDataEntity>)

    @Query("SELECT * FROM map_user_metadata WHERE id = :id")
    suspend fun getUserMeta(id: Int): List<MapMetaDataEntity>
    @Query("SELECT * FROM map_user_resource WHERE idMap = :idMap")
    suspend fun getUserResource(idMap: Int):List<MapResourceEntity>
    @Query("""
        SELECT mud.*,COUNT(na.idNpc)as worker
            FROM map_user_data mud
        LEFT JOIN (
            SELECT idNpc, idMapUser, id FROM npc_assign
                WHERE id IN (
                    SELECT MAX(id) FROM npc_assign GROUP BY idNpc)
        )na ON na.idMapUser = mud.id
            WHERE idMap = :idMap
            GROUP BY mud.id
    """)
    suspend fun getUserData(idMap: Int):List<MapDataWorker>

    @Query("""
        SELECT mud.id as idMap,mud.name,
        SUM(
            CASE 
                WHEN mud.name = "forest" AND nb.idAbility = 1 THEN (nb.level +1)*:forestVal
                WHEN mud.name = "wild" AND nb.idAbility = 2 THEN (nb.level +1)*:wildVal
                WHEN mud.name = "farm" AND nb.idAbility = 2 THEN (nb.level +1)*:farmVal
                WHEN mud.name = "stone" AND nb.idAbility = 3 THEN (nb.level +1)*:stoneVal
                WHEN mud.name = "gold" AND nb.idAbility = 3 THEN (nb.level +1)*:goldVal
                WHEN mud.name = "iron" AND nb.idAbility = 3 THEN (nb.level +1)*:ironVal
            ELSE 10 END
        ) AS totalValue
        FROM map_user_data mud
        JOIN (
            SELECT idNpc, idMapUser, id AS last_npc_assign
            FROM npc_assign
            WHERE id IN (SELECT MAX(id) FROM npc_assign GROUP BY idNpc)
        )na ON na.idMapUser = mud.id
        LEFT JOIN npc_ability nb ON na.idNpc = nb.idNpc
        AND(
            (mud.name = 'forest' AND nb.idAbility = 1)OR
            (mud.name = 'wild' AND nb.idAbility =2)OR
            (mud.name = 'farm' AND nb.idAbility =2)OR
            (mud.name = 'stone' AND nb.idAbility =3)OR
            (mud.name = 'iron' AND nb.idAbility =3)OR
            (mud.name = 'gold' AND nb.idAbility =3)
        )
        GROUP BY mud.id
    """)
    suspend fun getPotentialSource(forestVal: Int,
                                   wildVal: Int,
                                   farmVal: Int,
                                   stoneVal: Int,
                                   goldVal: Int,
                                   ironVal: Int): List<PotentialData>

}