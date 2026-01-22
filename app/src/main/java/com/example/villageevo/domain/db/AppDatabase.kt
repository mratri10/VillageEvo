package com.example.villageevo.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity
import com.example.villageevo.domain.map.MapUserDao
import com.example.villageevo.domain.map.MapUserDataEntity
import com.example.villageevo.domain.map.MapUserMetaDataEntity

@Database(
        entities =
                [
                        MapUserMetaDataEntity::class,
                        MapUserDataEntity::class,
                        MapMetaDataEntity::class,
                        MapDataEntity::class,
                        MapResourceEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mapUserDao(): MapUserDao

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
                                        .addCallback(DatabaseSeeder(context))
                                        .build()
                        INSTANCE = instance
                        instance
                    }
        }
    }
}
