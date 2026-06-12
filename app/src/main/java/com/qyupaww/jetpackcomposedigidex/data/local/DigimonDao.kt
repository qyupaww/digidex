package com.qyupaww.jetpackcomposedigidex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DigimonDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDigimons(digimons: List<DigimonEntity>)

    @Query("SELECT * FROM digidex_table WHERE name LIKE '%' || :searchQuery || '%' OR id = :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getDigimons(limit: Int, offset: Int, searchQuery: String): List<DigimonEntity>

    @Query("SELECT COUNT(*) FROM digidex_table WHERE name LIKE '%' || :searchQuery || '%' OR id = :searchQuery")
    suspend fun getDigimonCount(searchQuery: String): Int

    @Query("DELETE FROM digidex_table")
    suspend fun clearAll()

    // Offline Detail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDigimonDetail(detail: DigimonDetailEntity)

    @Query("SELECT * FROM digimon_detail_table WHERE name = :name")
    suspend fun getDigimonDetail(name: String): DigimonDetailEntity?
}
