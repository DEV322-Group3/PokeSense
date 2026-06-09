package com.example.pokesense.data.model

import com.google.gson.annotations.SerializedName        // Lets Kotlin read JSON names with underscores

// PokemonDetailResponse = Kotlin shape for /pokemon/{pokemonName} JSON
// We keep basic detail data for encounter screen and future detail screen
data class PokemonDetailResponse(
    val id: Int,                                         // id = Pokemon number
    val name: String,                                    // name = Pokemon name
    val height: Int,                                     // height = Pokemon height from API
    val weight: Int,                                     // weight = Pokemon weight from API
    val sprites: PokemonSprites,                         // sprites = image section
    val types: List<PokemonTypeEntry>                    // types = Pokemon type list
)

// PokemonSprites = sprite image section from PokeAPI
data class PokemonSprites(
    @SerializedName("front_default")                     // JSON says front_default, Kotlin uses frontDefault
    val frontDefault: String?                            // frontDefault = front sprite image URL
)

// PokemonTypeEntry = one item inside the types list
data class PokemonTypeEntry(
    val slot: Int,                                       // slot = type order number
    val type: NamedApiResource                           // type = small object with type name and URL
)








// ----- json visualize -------
//{
//  "id": 4,
//  "name": "charmander",
//  "height": 6,
//  "weight": 85,
//  "sprites": {
//    "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"
//  },
//  "types": [
//    {
//      "slot": 1,
//      "type": {
//        "name": "fire",
//        "url": "https://pokeapi.co/api/v2/type/10/"
//      }
//    }
//  ]
//}
//
// ---- shaped kotlin data class visual ---
//PokemonDetailResponse
//├── id = Pokemon number
//├── name = Pokemon name
//├── height = Pokemon height
//├── weight = Pokemon weight
//├── sprites = image section
//│   └── frontDefault = front sprite image URL
//└── types = type list
//    └── PokemonTypeEntry
//        ├── slot = type order
//        └── type = one type object
//            ├── name
//            └── url