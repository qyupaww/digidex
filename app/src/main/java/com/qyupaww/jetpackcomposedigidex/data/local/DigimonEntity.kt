package com.qyupaww.jetpackcomposedigidex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "digidex_table")
data class DigimonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
