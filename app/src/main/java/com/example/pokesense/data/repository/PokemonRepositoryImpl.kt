package com.example.pokesense.data.repository

import com.example.pokesense.data.remote.PokemonRemoteDataSource
import com.example.pokesense.data.remote.dto.PokemonDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// PokemonRepositoryImpl = real worker that uses RemoteDataSource
class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource = PokemonRemoteDataSource()
) : PokemonRepository {

    // getRandomPokemonByType = get type list, pick random Pokemon, then get full detail
    override suspend fun getRandomPokemonByType(typeName: String): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {

            val typeResponse = remoteDataSource.fetchPokemonByType(typeName)

            val randomPokemonName = typeResponse.pokemon.random().pokemon.name

            remoteDataSource.fetchPokemonDetail(randomPokemonName)
        }
    }
}