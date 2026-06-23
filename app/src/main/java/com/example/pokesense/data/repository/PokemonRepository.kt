package com.example.pokesense.data.repository

import com.example.pokesense.data.local.PokemonCatchEntity // Entity = shape of one saved catch in Room
import com.example.pokesense.data.remote.dto.PokemonDetailResponse // Pokemon detail data shape
import kotlinx.coroutines.flow.Flow // Flow = stream of caught-list updates over time

// interface (to be expanded on in the impl)
interface PokemonRepository {

    // getRandomPokemonByType = promise to get one random Pokemon by type
    suspend fun getRandomPokemonByType(typeName: String): PokemonDetailResponse

    // tryCatchPokemon = tries to catch and save the Pokemon
    suspend fun tryCatchPokemon(
        pokemon: PokemonDetailResponse,
        lightLevel: Float,
        temperature: Float
    ): Boolean

    // getAllCaughtPokemon = promise to stream every saved catch, newest first
    fun getAllCaughtPokemon(): Flow<List<PokemonCatchEntity>>
}