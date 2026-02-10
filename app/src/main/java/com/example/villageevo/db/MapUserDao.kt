package com.example.villageevo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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

    @Query("UPDATE source SET value = value + :newValue WHERE params = :name")
    suspend fun updateResource(name: String, newValue: Int):Int
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResource(source: SourceEntity): Long
    @Update
    suspend fun updateMapUserAllData(data: List<MapDataEntity>)

    @Transaction
    suspend fun runTurnTransaction(sources: List<SourceEntity>, mapUpdates: List<MapDataEntity>) {
        sources.forEach{
            val rowsUpdated = updateResource(it.params.toString(), it.value)
            if (rowsUpdated == 0) {
                insertResource(SourceEntity(params = it.params, value = it.value))
            }
        }
        updateMapUserAllData(mapUpdates)
    }
    @Query("SELECT COUNT(DISTINCT idNpc) FROM npc_assign")
    suspend fun getGlobalWorkerCount(): Int

    @Query("SELECT * FROM map_user_metadata WHERE id = :id")
    suspend fun getUserMeta(id: Int): List<MapMetaDataEntity>
    @Query("SELECT * FROM map_user_resource WHERE idMap = :idMap")
    suspend fun getUserResource(idMap: Int):List<MapResourceEntity>
    @Query("""
        SELECT mud.*,COUNT(na.idNpc)as worker
            FROM map_user_data mud
        LEFT JOIN npc_assign na ON na.idMapUser = mud.id
            WHERE idMap = :idMap
            GROUP BY mud.id
    """)
    suspend fun getUserData(idMap: Int):List<MapDataWorker>

    @Query("""
        SELECT mud.id as idMap,mud.name,
        (CASE 
            WHEN mud.name = 'forest' THEN mud.value * :forestVal
            WHEN mud.name = 'wild' THEN mud.value * :wildVal
            WHEN mud.name = 'farm' THEN mud.value * :farmVal
            WHEN mud.name = 'stone' THEN mud.value * :stoneVal
            WHEN mud.name = 'gold' THEN mud.value * :goldVal
            WHEN mud.name = 'iron' THEN mud.value * :ironVal
            ELSE 0
        END
        ) totalSource,
        SUM(CASE 
            WHEN mud.name = 'forest' THEN :forestVal
            WHEN mud.name = 'wild'   THEN :wildVal
            WHEN mud.name = 'farm'   THEN :farmVal
            WHEN mud.name = 'stone'  THEN :stoneVal
            WHEN mud.name = 'gold'   THEN :goldVal
            WHEN mud.name = 'iron'   THEN :ironVal
            ELSE 0 
        END) + 
        (CASE 
            WHEN mud.name = 'forest' AND nb.idAbility = 1 THEN nb.level * :forestVal
            WHEN mud.name = 'wild'   AND nb.idAbility = 2 THEN nb.level * :wildVal
            WHEN mud.name = 'farm'   AND nb.idAbility = 2 THEN nb.level * :farmVal
            WHEN mud.name = 'stone'  AND nb.idAbility = 3 THEN nb.level * :stoneVal
            WHEN mud.name = 'gold'   AND nb.idAbility = 3 THEN nb.level * :goldVal
            WHEN mud.name = 'iron'   AND nb.idAbility = 3 THEN nb.level * :ironVal
            ELSE 0
        END
        ) totalValue
        FROM map_user_data mud
        JOIN npc_assign na ON na.idMapUser = mud.id
        LEFT JOIN npc_ability nb ON na.idNpc = nb.idNpc
        GROUP BY mud.id
    """)
    suspend fun getPotentialSource(forestVal: Int,
                                   wildVal: Int,
                                   farmVal: Int,
                                   stoneVal: Int,
                                   goldVal: Int,
                                   ironVal: Int): List<PotentialData>

}