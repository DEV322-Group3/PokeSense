package com.example.pokesense.data.sensor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// SensorPokemonData = sensor values and the Pokemon type chosen from them
data class SensorPokemonData(
    val lightLevel: Float,
    val temperature: Float,
    val pokemonType: String
)

// PokemonSensorDataSource = gets sensor values and chooses a Pokemon type
class PokemonSensorDataSource {

    // getPokemonSensorData = returns light, temperature, and chosen Pokemon type
    suspend fun getPokemonSensorData(): SensorPokemonData {
        return withContext(Dispatchers.Default) {

            // TODO later: replace these test values with real phone sensor values
            val lightLevel = 600f
            val temperature = 25f

            val pokemonType = when {
                lightLevel >= 500f && temperature >= 20f -> "fire"
                lightLevel >= 500f && temperature < 20f -> "normal"
                lightLevel < 500f && temperature >= 20f -> "ghost"
                else -> "ice"
            }

            SensorPokemonData(
                lightLevel = lightLevel,
                temperature = temperature,
                pokemonType = pokemonType
            )
        }
    }
}