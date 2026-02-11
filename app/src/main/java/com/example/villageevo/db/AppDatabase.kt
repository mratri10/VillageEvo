package com.example.villageevo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.villageevo.domain.building.*
import com.example.villageevo.domain.map.*
import com.example.villageevo.domain.npc.*

@Database(
        entities =
                [
                        MapMetaDataEntity::class,
                        MapResourceEntity::class,
                        MapDataEntity::class,
                        SourceEntity::class,
                        BuildDataEntity::class,
                        MapBuildEntity::class,
                        NpcEntity::class,
                        NpcAbilityEntity::class,
                        NpcAssignEntity::class],
        version = 11,
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
                                .fallbackToDestructiveMigration(false)
                                        .build()
                        INSTANCE = instance
                        instance
                    }
        }
    }
}
