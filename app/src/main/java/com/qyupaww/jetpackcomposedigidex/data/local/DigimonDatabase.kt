package com.qyupaww.jetpackcomposedigidex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DigimonEntity::class, DigimonDetailEntity::class],
    version = 2,
    exportSchema = false
)
abstract class DigimonDatabase : RoomDatabase() {
    abstract val dao: DigimonDao
}
