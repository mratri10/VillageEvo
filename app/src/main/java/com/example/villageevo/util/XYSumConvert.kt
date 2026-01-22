package com.example.villageevo.util

import com.example.villageevo.domain.building.BuildingType
import com.example.villageevo.domain.building.XYData
import kotlin.math.abs
import kotlin.math.floor

fun XYSumConvert(sum:Int, type: BuildingType): XYData{
    var diff = 99
    var xyData: XYData= XYData(0,0,0)
    var xSum:Int=0
    var ySum:Int=0
    if(type == BuildingType.FOREST) {
        for (cal in 1 until 10){
            val num = floor((sum / cal).toDouble())
            ySum = cal
            xSum = num.toInt()
            if(abs(xSum - ySum) <diff){
                diff = abs(xSum - ySum)
                val rest  = sum - (cal*num.toInt())
                xyData = XYData(xSum,ySum,rest)
            }
        }
    }
    println("yyyyyyyyy $ySum")
    println("xxxxxxxxx $xSum")
    println("rrrrrrrrr ${xyData}")
    println("sssssssss $sum")
    return xyData

}