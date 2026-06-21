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
import com.example.pokesense.ui.CaughtListScreen          // CaughtList screen UI
import com.example.pokesense.ui.ResultScreen                // Result screen after catch
import com.example.pokesense.ui.theme.PokeSenseTheme
import com.example.pokesense.viewmodel.EncounterViewModel // Encounter screen logic/state

import androidx.lifecycle.ViewModel                           // ViewModel = base ViewModel type for the factory
import androidx.lifecycle.ViewModelProvider                   // ViewModelProvider = creates ViewModel with repository
import com.example.pokesense.data.repository.PokemonRepositoryImpl // Real repository worker
import com.example.pokesense.data.local.AppDatabase              // AppDatabase = Room database setup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // database = gets the app Room database
        val database = AppDatabase.getInstance(applicationContext)

        // pokemonCatchDao = gets Room save/read commands
        val pokemonCatchDao = database.pokemonCatchDao()

        // repository = real worker for API and Room save work
        val repository = PokemonRepositoryImpl(
            pokemonCatchDao = pokemonCatchDao
        )

        // encounterViewModelFactory = creates EncounterViewModel with repository
        val encounterViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return EncounterViewModel(repository) as T
            }
        }

        setContent {
            PokeSenseTheme {
                // currentScreen = decides which screen is showing
                var currentScreen by remember { mutableStateOf("home") }

                // encounterViewModel = holds encounter state and starts API call
                val encounterViewModel: EncounterViewModel = viewModel(
                    factory = encounterViewModelFactory
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "home" -> {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                onStartEncounter = {
                                    currentScreen = "encounter"
                                    encounterViewModel.startEncounter()
                                },
                                onCaughtList = {
                                    currentScreen = "caughtlist"
                                }
                            )
                        }
                        "encounter" -> {
                            EncounterScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = encounterViewModel,
                                onGoHome = {
                                    currentScreen = "home"
                                },
                                onResult = {
                                    currentScreen = "result"
                                }
                            )
                        }
                        "caughtlist" -> {
                            CaughtListScreen(
                                modifier = Modifier.padding(innerPadding),
                                onGoHome = {
                                    currentScreen = "home"
                                }
                            )
                        }
                        "result" -> {
                            ResultScreen(
                                modifier = Modifier.padding(innerPadding),
                                onGoHome = {
                                    currentScreen = "home"
                                },
                                onGoCaughtList = {
                                    currentScreen = "caughtlist"
                                }
                            )
                        }
                    }
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