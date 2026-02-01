package com.example.villageevo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.villageevo.domain.building.BuildDataEntity
import com.example.villageevo.domain.building.BuildEvoEntity
import com.example.villageevo.domain.building.MapBuildEntity
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity
import com.example.villageevo.domain.soldier.NpcAbilityEntity
import com.example.villageevo.domain.soldier.NpcEntity

@Database(
        entities =
                [
                    MapMetaDataEntity::class,
                    MapResourceEntity::class,
                    MapDataEntity::class,

                    BuildEvoEntity::class,
                    BuildDataEntity::class,
                    MapBuildEntity::class,

                    NpcEntity::class,
                    NpcAbilityEntity::class
                ],
        version = 4,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mapUserDao(): MapUserDao
    abstract fun buildDao(): BuildDao
    abstract fun npcDao(): NpcDao


    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE
                    ?: synchronized(this) {
                        val instance =
                                Room.databaseBuilder(
                                                context.applicationContext,
                                                AppDatabase::class.java,
                                                "village_evo_database"
                                        )
                                        .fallbackToDestructiveMigration()
                                        .build()
                        INSTANCE = instance
                        instance
                    }
        }
    }
}