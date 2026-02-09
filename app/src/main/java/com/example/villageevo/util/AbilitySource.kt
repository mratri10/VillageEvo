package com.example.villageevo.util

import com.example.villageevo.domain.map.ParamSource

object AbilitySource {
    val forest = ParamSource("forest",1, "wood",10)
    val wild = ParamSource("wild",2,"food",7)
    val farm = ParamSource("farm",2, "food",15)
    val stone = ParamSource("stone",3, "stone",10)
    val gold = ParamSource("gold",3, "gold",4)
    val iron = ParamSource("iron",3, "iron",6)
}