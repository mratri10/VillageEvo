package com.example.villageevo.repository

import android.content.Context
import com.example.villageevo.domain.soldier.MapSoldier
import com.example.villageevo.domain.soldier.Soldier
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

class SoldierRepository(private val context: Context) {
    private val csvReader = csvReader()
    private val mapSoldier = "map_soldier.csv"
    private val soldier = "soldier.csv"

    fun getMapSoldier():List<MapSoldier>{
        return context.assets.open(mapSoldier).use{
            inputStream ->
            csvReader.readAllWithHeader(inputStream).map{
                MapSoldier(it["id"]!!.toInt(), it["id_map"]!!.toInt(),
                    it["id_soldier"]!!.toInt(), it["sum"]!!.toInt())
            }
        }
    }

    fun getSoldier():List<Soldier>{
        return context.assets.open(soldier).use{
            inputStream ->
            csvReader.readAllWithHeader(inputStream).map{
                Soldier(it["id"]!!.toInt(), it["name"]!!, it["weak_id"]!!.toInt())
            }
        }
    }
}