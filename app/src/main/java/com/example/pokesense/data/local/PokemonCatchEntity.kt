package com.example.pokesense.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "caught_pokemon")
data class PokemonCatchEntity(
    @PrimaryKey(autoGenerate = true)
    val catchId: Int = 0,
    val pokedexId: Int,
    val name: String,
    val spriteUrl: String,
    val type: String,
    val timestampCaught: Long,
    val lightLevel: Float,
    val motionLevel: Float
)