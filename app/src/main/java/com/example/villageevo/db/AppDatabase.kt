package com.example.villageevo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.villageevo.domain.map.MapUserDataEntity
import com.example.villageevo.domain.map.MapUserMetaDataEntity
import com.example.villageevo.domain.map.MapUserResourceEntity

@Database(
        entities =
                [
                    MapUserMetaDataEntity::class,
                    MapUserDataEntity::class,
                    MapUserResourceEntity::class
                ],
        version = 3,
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
                                        .fallbackToDestructiveMigration()
                                        .build()
                        INSTANCE = instance
                        instance
                    }
        }
    }
}