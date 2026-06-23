package com.example.pokesense.data.remote

import com.example.pokesense.data.remote.dto.PokemonDetailResponse   // Kotlin shape for /pokemon/{name} JSON
import com.example.pokesense.data.remote.dto.TypeResponse            // Kotlin shape for /type/{typeName} JSON, only the Pokemon list part

import retrofit2.Retrofit                                       // fetch-like API caller
import retrofit2.converter.gson.GsonConverterFactory            // Turns JSON into Kotlin data classes
import retrofit2.http.GET                                       // Marks a function as a GET API call
import retrofit2.http.Path                                      // Puts a variable into the URL

// TypeResponse is for the first API call. It reads the /type/fire JSON and makes a Kotlin list of Pokémon names and URLs.
// PokemonDetailResponse is for the second API call. It reads /pokemon/{name} JSON and gives the selected Pokémon’s name and sprite.

// ------  PokeApiService (this here) = API call file ---------
interface PokeApiService {

    // getPokemonByType = fetch list of Pokémon for a type, like fire/water/grass
    @GET("type/{typeName}")
    suspend fun getPokemonByType(
        @Path("typeName") typeName: String
    ): TypeResponse

    // getPokemonDetail = fetch detail for one Pokémon, like name and sprite
    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetail(
        @Path("pokemonName") pokemonName: String
    ): PokemonDetailResponse
}

// PokeApiClient = builds the API caller
object PokeApiClient {

    // api = ready-to-use PokeAPI caller
    val api: PokeApiService = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")              // Base URL for PokeAPI
        .addConverterFactory(GsonConverterFactory.create()) // JSON to Kotlin data class
        .build()                                            // Build Retrofit object
        .create(PokeApiService::class.java)                 // Create the API service
}


// ----- Important note: ------
// React’s res.json() does the work behind the scenes to turn JSON into a JS object, so JavaScript can read the key-value pairs from the JSON easily.
// Kotlin is... So it needs us to turn the JSON into Kotlin data classes first. Then Kotlin knows where to look for the values, like the Pokemon name or URL.

// We first use a simple type string, like "fire", to call the type API. Then we use TypeResponse to read the returned JSON and get a Pokemon name.
// That name becomes a string we use for the second API call to get the specific Pokémon details.