package com.example.pokesense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue                  // getValue = needed for "by"
import androidx.compose.runtime.mutableStateOf           // mutableStateOf = simple screen state
import androidx.compose.runtime.remember                 // remember = keeps screen state
import androidx.compose.runtime.setValue                  // setValue = needed for "by"
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel     // viewModel = gets EncounterViewModel
import com.example.pokesense.ui.EncounterScreen           // Encounter screen UI
import com.example.pokesense.ui.HomeScreen                // Home screen UI
//import com.example.pokesense.ui.ResultScreen              // Result screen UI
//import com.example.pokesense.ui.CaughtListScreen          // CaughtList screen UI
//import com.example.pokesense.ui.PokemonDetailScreen       // PokémonDetail screen UI
import com.example.pokesense.ui.theme.PokeSenseTheme
import com.example.pokesense.viewmodel.EncounterViewModel // Encounter screen logic/state

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PokeSenseTheme {

                // currentScreen = decides which screen is showing
                var currentScreen by remember { mutableStateOf("home") }

                // encounterViewModel = holds encounter state and starts API call
                val encounterViewModel: EncounterViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (currentScreen == "home") {

                        // HomeScreen = first screen with start button
                        HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onStartEncounter = {
                                currentScreen = "encounter"             // Move to encounter screen
                                encounterViewModel.startEncounter()     // Start Pokémon encounter
                            },
                            onCaughtList = {
                                currentScreen = "caughtlist"            // Move to caught list screen
//                                caughtListViewModel.viewList()     // Show list of caught Pokémon
                            }
                        )

                    } else {

                        // EncounterScreen = shows loading, error, or Pokémon result
                        EncounterScreen(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = encounterViewModel,
                            onGoHome = {
                                currentScreen = "home"                  // Go back to home screen
                            },
                            onResult = {
                                currentScreen = "result"            // Show catch result
                            }
                        )
                    }
//                    } else {
//
//                        // ResultScreen = shows error or Pokémon result
//                        ResultScreen(
//                            modifier = Modifier.padding(innerPadding),
//                            onStartEncounter = {
//                                currentScreen = "encounter"             // Move to encounter screen
//                                encounterViewModel.startEncounter()     // Start Pokémon encounter
//                            },
//                            onGoHome = {
//                            currentScreen = "home"                  // Go back to home screen
//                        }
//                    )
//                    } else {
//
//                    // CaughtListScreen = shows list of caught Pokémon
//                        CaughtListScreen(
//                        modifier = Modifier.padding(innerPadding),
//                        viewModel = caughtListViewModel,
//                        onGoHome = {
//                            currentScreen = "home"                  // Go back to home screen
//                        }
//                    )
//                } else {
//
//                    // PokemonDetailScreen = shows Pokémon details
//                    PokemonDetailScreen(
//                        modifier = Modifier.padding(innerPadding),
//                        viewModel = pokemonDetailViewModel,
//                        onGoHome = {
//                            currentScreen = "home"                  // Go back to home screen
//                        }
//                    )
//                }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokeSenseTheme {
        Greeting("Android")
    }
}