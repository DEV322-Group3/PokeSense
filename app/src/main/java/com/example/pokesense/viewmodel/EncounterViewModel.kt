package com.example.pokesense.viewmodel

import androidx.lifecycle.ViewModel                                 // ViewModel = holds screen logic/state
import androidx.lifecycle.viewModelScope                            // viewModelScope = coroutine owned by ViewModel (remember! don't use global, read the previous chapter!)
import com.example.pokesense.data.remote.dto.PokemonDetailResponse  // Pokemon detail data from repository
import com.example.pokesense.data.repository.PokemonRepository      // Repository promise
// import com.example.pokesense.data.repository.PokemonRepositoryImpl  // Real repository function logic

import kotlinx.coroutines.delay                                     // delay functionality, so we can show load screen if needed, make it look like we're looking for pokemon
import kotlinx.coroutines.flow.MutableStateFlow                     // Changeable state inside ViewModel
import kotlinx.coroutines.flow.StateFlow                            // Read-only state for UI
import kotlinx.coroutines.flow.asStateFlow                          // Exposes state safely (Remember! code private state first, then expose to UI)
import kotlinx.coroutines.launch                                    // Starts coroutine

import com.example.pokesense.data.sensor.PokemonSensorDataSource    // sensor value

// EncounterUiState = all data the EncounterScreen needs
data class EncounterUiState(
    val currentScreen: String = "home",                     // currentScreen = screen currently shown
    val isLoading: Boolean = false,                        // isLoading = true while API is working
    val isCatching: Boolean = false,                       // isCatching = true while catch/save is working
    val catchResult: Boolean? = null,                      // catchResult = true caught, false failed, null not decided yet
    val pokemon: PokemonDetailResponse? = null,            // pokemon = random Pokemon result
    val lightLevel: Float = 0f,                            // lightLevel = light used for this encounter
    val temperature: Float = 0f,                           // temperature = temperature used for this encounter
    val errorMessage: String? = null                       // errorMessage = message if API fails
)


// EncounterViewModel = controls the encounter screen
class EncounterViewModel(
    // repository = passed in from MainActivity
    private val repository: PokemonRepository
) : ViewModel() {


    // sensorDataSource = gets light, temperature, and chosen Pokemon type
    private val sensorDataSource = PokemonSensorDataSource()

    // _uiState = private state that this ViewModel can change, holds the encounter state various variables
    private val _uiState = MutableStateFlow(EncounterUiState())

    // goHome = changes screen to Home
    fun goHome() {
        _uiState.value = _uiState.value.copy(
            currentScreen = "home"
        )
    }

    // goToCaughtList = changes screen to Caught List
    fun goToCaughtList() {
        _uiState.value = _uiState.value.copy(
            currentScreen = "caughtlist"
        )
    }

    // goToResult = changes screen to Result
    fun goToResult() {
        _uiState.value = _uiState.value.copy(
            currentScreen = "result"
        )
    }

    // uiState = public state that the UI can read
    val uiState: StateFlow<EncounterUiState> = _uiState.asStateFlow()


    // catchPokemon = tries the 40% catch chance and saves to Room when successful
    fun catchPokemon() {
        val pokemon = _uiState.value.pokemon ?: return
        val lightLevel = _uiState.value.lightLevel
        val temperature = _uiState.value.temperature

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isCatching = true,
                catchResult = null,
                errorMessage = null
            )

            try {
                delay(1000)

                val didCatch = repository.tryCatchPokemon(
                    pokemon = pokemon,
                    lightLevel = lightLevel,
                    temperature = temperature
                )

                _uiState.value = _uiState.value.copy(
                    isCatching = false,
                    catchResult = didCatch
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isCatching = false,
                    errorMessage = "Could not save Pokemon"
                )
            }
        }
    }


    // startEncounter = starts one Pokemon encounter
    fun startEncounter() {

        viewModelScope.launch {

            // Show loading first
            _uiState.value = EncounterUiState(
                currentScreen = "encounter",
                isLoading = true
            )

            try {
                // Wait so the loading screen is visible
                delay(3000)

                // sensorData = gets light level, temperature, and the chosen type
                val sensorData = sensorDataSource.getPokemonSensorData()

                // result = gets a random Pokemon using the sensor-chosen type
                val result = repository.getRandomPokemonByType(sensorData.pokemonType)

                // Send result to UI
                _uiState.value = EncounterUiState(
                    currentScreen = "encounter",
                    isLoading = false,
                    pokemon = result,
                    lightLevel = sensorData.lightLevel,
                    temperature = sensorData.temperature
                )

            } catch (e: Exception) {

                // Send error to UI
                _uiState.value = EncounterUiState(
                    currentScreen = "encounter",
                    isLoading = false,
                    errorMessage = "Could not load Pokemon"
                )
            }
        }
    }
}