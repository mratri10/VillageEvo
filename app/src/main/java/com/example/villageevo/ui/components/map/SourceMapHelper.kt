package com.example.villageevo.ui.components.map

import com.example.villageevo.R
import kotlin.compareTo

object SourceMapHelper {
    fun forestImage(sum:Double):Int{
        val imageSource =
            when {
                sum < 60 -> R.drawable.forest_l1
                sum < 120 -> R.drawable.forest_l2
                sum < 180 -> R.drawable.forest_l3
                sum < 240 -> R.drawable.forest_l4
                else -> R.drawable.forestf
            }
        return imageSource
    }
    fun forestWidth(sum:Double): Float{
        val widthSource =
            when {
                sum < 60 -> 2 / 5f
                sum < 120 -> 3 / 5f
                sum < 180 -> 1f
                sum < 240 -> 1f
                sum < 300 -> 1f
                else -> 1f
            }
        return widthSource
    }
    fun forestHeight(sum: Double): Float{
        val heightSource =
            when {
                sum < 60 -> 3 / 5f
                sum < 120 -> 1f
                sum < 180 -> 1f
                sum < 240 -> 1f
                sum < 300 -> 1f
                else -> 1f
            }
        return heightSource
    }

    fun wildImage(sum:Double):Int{
        val imageSource =
            when {
                sum < 60 -> R.drawable.wild_l1
                sum < 120 -> R.drawable.wild_l2
                sum < 180 -> R.drawable.wild_l3
                sum < 240 -> R.drawable.wild_l4
                else -> R.drawable.wildf
            }
        return imageSource
    }
    fun widthWild(sum:Int): Float{
        val widthSource =
            when {
                sum < 60 -> 2 / 5f
                sum < 120 -> 3 / 5f
                sum < 180 -> 1f
                sum < 240 -> 1f
                sum < 300 -> 1f
                else -> 1f
            }
        return widthSource
    }

    fun heightWild(sum:Int): Float{
        val heightSource =
            when {
                sum < 60 -> 3 / 5f
                sum < 120 -> 1f
                sum < 180 -> 1f
                sum < 240 -> 1f
                sum < 300 -> 1f
                else -> 1f
            }
        return heightSource
    }
}