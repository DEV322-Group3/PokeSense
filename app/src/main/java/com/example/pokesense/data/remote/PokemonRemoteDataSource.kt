package com.example.pokesense.data.remote

import com.example.pokesense.data.remote.dto.PokemonDetailResponse
import com.example.pokesense.data.remote.dto.TypeResponse

// PokemonRemoteDataSource = small worker that calls PokeApiService
class PokemonRemoteDataSource(
    private val api: PokeApiService = PokeApiClient.api
) {
    // fetchPokemonByType = call /type/{typeName}
    suspend fun fetchPokemonByType(typeName: String): TypeResponse {
        return api.getPokemonByType(typeName)
    }

    // fetchPokemonDetail = call /pokemon/{pokemonName}
    suspend fun fetchPokemonDetail(pokemonName: String): PokemonDetailResponse {
        return api.getPokemonDetail(pokemonName)
    }
}