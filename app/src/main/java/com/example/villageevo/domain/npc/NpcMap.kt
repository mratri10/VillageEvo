package com.example.villageevo.domain.npc

import androidx.room.ColumnInfo

data class NpcMap(
    val id:Int,
    val year:Int,
    val wooder:Int,
    val farmer:Int,
    val miner:Int,
    val infantry:Int,
    val archer:Int,
    val spearman:Int,
    val calvary:Int,
    val idMapUser:Int,

    @ColumnInfo(name = "last_na_id") // This is the fix!
    val lastNaId: Int?,
)
