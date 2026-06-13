package com.example.pokesense.data.repository

import com.example.pokesense.data.remote.dto.PokemonDetailResponse // Pokemon detail data shape

// interface (to be expanded on in the impl)
interface PokemonRepository {

    // getRandomPokemonByType = promise to get one random Pokemon by type
    suspend fun getRandomPokemonByType(typeName: String): PokemonDetailResponse
}