package com.example.pokesense.data.repository

import com.example.pokesense.data.api.PokeApiClient           // PokeAPI caller
import com.example.pokesense.data.api.PokeApiService          // API call rules
import com.example.pokesense.data.model.PokemonDetailResponse // Pokemon detail data shape
import kotlinx.coroutines.Dispatchers                         // IO thread helper
import kotlinx.coroutines.withContext                         // Switch work to IO thread

// PokemonRepositoryImpl = real worker that calls PokeAPI
class PokemonRepositoryImpl(
    private val api: PokeApiService = PokeApiClient.api        // api = actual PokeAPI caller
) : PokemonRepository {

    // getRandomPokemonByType = get type list, pick random Pokemon, then get full detail
    override suspend fun getRandomPokemonByType(typeName: String): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {

            val typeResponse = api.getPokemonByType(typeName)                   // Call /type/fire

            val randomPokemonName = typeResponse.pokemon.random().pokemon.name  // Pick random Pokemon name

            api.getPokemonDetail(randomPokemonName)                             // Call /pokemon/{name}
        }
    }
}