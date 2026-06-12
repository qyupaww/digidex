package com.qyupaww.jetpackcomposedigidex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "digimon_detail_table")
data class DigimonDetailEntity(
    @PrimaryKey val name: String,
    val jsonString: String
)
