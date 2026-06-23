package com.example.pokesense.data.repository

import com.example.pokesense.data.local.PokemonCatchDao     // DAO = Room save commands
import com.example.pokesense.data.local.PokemonCatchEntity  // Entity = Pokemon data saved in Room
import com.example.pokesense.data.remote.PokemonRemoteDataSource
import com.example.pokesense.data.remote.dto.PokemonDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random                                // Random = gives the 40% catch chance

// PokemonRepositoryImpl = real worker that uses RemoteDataSource and Room DAO
class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource = PokemonRemoteDataSource(),
    private val pokemonCatchDao: PokemonCatchDao
) : PokemonRepository {

    // getRandomPokemonByType = get type list, pick random Pokemon, then get full detail
    override suspend fun getRandomPokemonByType(typeName: String): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {
            val typeResponse = remoteDataSource.fetchPokemonByType(typeName)

            val randomPokemonName = typeResponse.pokemon.random().pokemon.name

            remoteDataSource.fetchPokemonDetail(randomPokemonName)
        }
    }

    // tryCatchPokemon = gives a 40% catch chance, then saves caught Pokemon to Room
    override suspend fun tryCatchPokemon(
        pokemon: PokemonDetailResponse,
        lightLevel: Float,
        temperature: Float
    ): Boolean {
        return withContext(Dispatchers.IO) {

            // didCatch = true 40% of the time
            val didCatch = Random.nextInt(100) < 40

            // Stop here when the catch fails
            if (!didCatch) {
                return@withContext false
            }

            // pokemonCatch = turn API Pokemon data into Room saved data
            val pokemonCatch = PokemonCatchEntity(
                pokedexId = pokemon.id,
                name = pokemon.name,
                spriteUrl = pokemon.sprites.frontDefault ?: "",
                type = pokemon.types.firstOrNull()?.type?.name ?: "unknown",
                timestampCaught = System.currentTimeMillis(),
                lightLevel = lightLevel,
                temperature = temperature
            )

            // Save caught Pokemon into Room
            pokemonCatchDao.insertCaught(pokemonCatch)

            true
        }
    }

    // getAllCaughtPokemon = just hands back the DAO's flow
    override fun getAllCaughtPokemon(): Flow<List<PokemonCatchEntity>> {
        return pokemonCatchDao.getAllCaught()
    }
}