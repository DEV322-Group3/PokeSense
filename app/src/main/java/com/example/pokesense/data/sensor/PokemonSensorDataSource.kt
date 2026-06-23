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
    suspend fun getPokemonSensorData(lightLevel: Float, temperature: Float): SensorPokemonData {
        return withContext(Dispatchers.Default) {



            val pokemonType = when {
                //outside and warm
                lightLevel >= 500f && temperature >= 20f -> "fire"

                //outside and cold
                lightLevel >= 500f && temperature < 20f -> "normal"

                //inside and warm
                lightLevel < 500f && temperature >= 20f -> "ghost"

                //inside and cold
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