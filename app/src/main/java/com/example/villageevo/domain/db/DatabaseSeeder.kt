package com.example.villageevo.domain.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaDataEntity
import com.example.villageevo.domain.map.MapResourceEntity
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseSeeder(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            // Use application context to avoid leaks
            val database = AppDatabase.getDatabase(context)
            seedMapMetadata(database)
            seedMapData(database)
            seedMapResources(database)
        }
    }

    private suspend fun seedMapMetadata(database: AppDatabase) {
        val inputStream = context.assets.open("map_metadata.csv")
        val metadataList =
                csvReader().readAllWithHeader(inputStream).map {
                    MapMetaDataEntity(
                            id = it["id"]!!.toInt(),
                            title = it["title"]!!,
                            description = it["description"]!!
                    )
                }
        database.mapUserDao().insertAllMetadata(metadataList)
    }

    private suspend fun seedMapData(database: AppDatabase) {
        val inputStream = context.assets.open("map_data.csv")
        val dataList =
                csvReader().readAllWithHeader(inputStream).map {
                    MapDataEntity(
                            id = it["id"]!!.toInt(),
                            idMap = it["id_map"]!!.toInt(),
                            name = it["name"]!!,
                            value = it["value"]!!.toInt(),
                            x = it["x"]!!.toInt(),
                            y = it["y"]!!.toInt()
                    )
                }
        database.mapUserDao().insertAllMapData(dataList)
    }

    private suspend fun seedMapResources(database: AppDatabase) {
        val inputStream = context.assets.open("map_resource.csv")
        val resourceList =
                csvReader().readAllWithHeader(inputStream).map {
                    MapResourceEntity(
                            id = it["id"]!!.toInt(),
                            idMap = it["id_map"]!!.toInt(),
                            name = it["name"]!!,
                            sum = it["sum"]!!.toInt()
                    )
                }
        database.mapUserDao().insertAllResources(resourceList)
    }
}
