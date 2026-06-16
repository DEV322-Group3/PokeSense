package com.example.pokesense.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonCatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaught(pokemon: PokemonCatchEntity)

    @Query("SELECT * FROM caught_pokemon ORDER BY timestampCaught DESC")
    fun getAllCaught(): Flow<List<PokemonCatchEntity>>

    @Query("DELETE FROM caught_pokemon WHERE catchId = :id")
    suspend fun deleteCaughtById(id: Int)
}