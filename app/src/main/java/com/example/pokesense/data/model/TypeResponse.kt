package com.example.pokesense.data.model

// TypeResponse = Kotlin shape for /type/{typeName} JSON
// We only keep the Pokemon list part
data class TypeResponse(
    val pokemon: List<TypePokemonEntry>     // pokemon = list of Pokémon under that type
)

// TypePokemonEntry = one item inside the pokemon list
data class TypePokemonEntry(
    val pokemon: NamedApiResource           // pokemon = small object with name and URL
)

// NamedApiResource = simple name + URL object from PokeAPI
data class NamedApiResource(
    val name: String,                       // name = Pokémon name
    val url: String                         // url = detail URL for that Pokémon
)








// ----- json visualize -------
//{
//  "pokemon": [
//    {
//      "pokemon": {
//        "name": "charmander",
//        "url": "https://pokeapi.co/api/v2/pokemon/4/"
//      },
//      "slot": 1
//    },
//    {
//      "pokemon": {
//        "name": "charmeleon",
//        "url": "https://pokeapi.co/api/v2/pokemon/5/"
//      },
//      "slot": 1
//    }
//  ]
//}
//
// ---- shaped kotlin data class visual ---
//TypeResponse
//└── pokemon = big list
//    └── TypePokemonEntry
//        └── pokemon = one Pokémon object
//            ├── name
//            └── url