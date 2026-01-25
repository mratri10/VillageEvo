package com.example.villageevo.repository

import android.content.Context
import com.example.villageevo.domain.map.MapData
import com.example.villageevo.domain.map.MapMetaData
import com.example.villageevo.domain.map.MapResource
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

class MapRepository(private val context: Context) {
    private val csv = csvReader()

    fun getMapMetadata(): List<MapMetaData>{
        return context.assets.open("map_metadata.csv").use { inputStream ->
            csv.readAllWithHeader(inputStream).map {
                MapMetaData(it["id"]!!.toInt(), it["title"]!!, it["description"]!!)
            }
        }
    }

    fun getMapData(): List<MapData>{
        return context.assets.open("map_data.csv").use { inputStream ->
            csv.readAllWithHeader(inputStream).map {
                MapData(
                    it["id"]!!.toInt(),
                    it["id_map"]!!.toInt(),
                    it["name"]!!,
                    it["value"]!!.toInt(),
                    it["x"]!!.toInt(), it["y"]!!.toInt()
                )
            }
        }
    }

    fun getMapResource(): List<MapResource>{
        return context.assets.open("map_resource.csv").use { inputStream ->
            csv.readAllWithHeader(inputStream).map {
                MapResource(
                    it["id"]!!.toInt(),
                    it["id_map"]!!.toInt(),
                    it["description"]!!,
                    it["sum"]!!.toInt()
                )
            }
        }
    }
}