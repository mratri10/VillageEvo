package com.example.villageevo.repository

import android.content.Context
import com.example.villageevo.domain.map.MapDataEntity
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResourceEntity
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

class MapRepository(private val context: Context) {
    private val csvReader = csvReader()
    private val mapData = "map_data.csv"
    private val mapResource = "map_resource.csv"
    private val mapMeta = "map_meta.csv"


    fun getMapMetadata(): List<MapMetaData> {
        return context.assets.open(mapMeta).use { inputStream ->
            csvReader.readAllWithHeader(inputStream).map {
                MapMetaData(it["id"]!!.toInt(), it["title"]!!, it["description"]!!)
            }
        }
    }

    fun getMapData(): List<MapDataEntity> {
        return context.assets.open(mapData).use { inputStream ->
            csvReader.readAllWithHeader(inputStream).map {
                MapDataEntity(
                    it["id"]!!.toInt(),
                    it["id_map"]!!.toInt(),
                    it["name"]!!,
                    it["value"]!!.toDouble(),
                    it["x"]!!.toInt(),
                    it["y"]!!.toInt()
                )
            }
        }
    }

    fun getMapResource(): List<MapResourceEntity> {
        return context.assets.open(mapResource).use { inputStream ->
            csvReader.readAllWithHeader(inputStream).map {
                MapResourceEntity(
                        it["id"]!!.toInt(),
                        it["id_map"]!!.toInt(),
                        it["name"]!!,
                        it["sum"]!!.toInt()
                )
            }
        }
    }
}
