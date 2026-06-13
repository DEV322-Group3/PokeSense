package com.example.pokesense.viewmodel

import androidx.lifecycle.ViewModel                                 // ViewModel = holds screen logic/state
import androidx.lifecycle.viewModelScope                            // viewModelScope = coroutine owned by ViewModel (remember! don't use global, read the previous chapter!)
import com.example.pokesense.data.model.PokemonDetailResponse       // Pokemon detail data from repository
import com.example.pokesense.data.repository.PokemonRepository      // Repository promise
import com.example.pokesense.data.repository.PokemonRepositoryImpl  // Real repository function logic

import kotlinx.coroutines.delay                                     // delay functionality, so we can show load screen if needed, make it look like we're looking for pokemon
import kotlinx.coroutines.flow.MutableStateFlow                     // Changeable state inside ViewModel
import kotlinx.coroutines.flow.StateFlow                            // Read-only state for UI
import kotlinx.coroutines.flow.asStateFlow                          // Exposes state safely (Remember! code private state first, then expose to UI)
import kotlinx.coroutines.launch                                    // Starts coroutine

// EncounterUiState = all data the EncounterScreen needs
data class EncounterUiState(
    val isLoading: Boolean = false,                        // isLoading = true while API is working
    val pokemon: PokemonDetailResponse? = null,            // pokemon = random Pokemon result
    val errorMessage: String? = null                       // errorMessage = message if API fails
)


// EncounterViewModel = controls the encounter screen
class EncounterViewModel : ViewModel() {

    // repository = the ViewModel talks to this, then repo impl, not the API directly
    private val repository: PokemonRepository = PokemonRepositoryImpl()

    // _uiState = private state that this ViewModel can change, holds the encounter state various variables
    private val _uiState = MutableStateFlow(EncounterUiState())

    // uiState = public state that the UI can read
    val uiState: StateFlow<EncounterUiState> = _uiState.asStateFlow()

    // startEncounter = starts one Pokemon encounter
    fun startEncounter() {
        viewModelScope.launch {

            // Show loading first
            _uiState.value = EncounterUiState(isLoading = true)

            try {
                delay(3000)                                             // Wait so the loading screen is visible


                // =============================================================
                // ================== MODIFY LATER !! ==========================
                val result = repository.getRandomPokemonByType("fire") // Hardcoded type for now (CHANGE LATER AFTER SENSOR STUFF IS DONE!!!)
                // =============================================================
                // =============================================================

                // Send result to UI
                _uiState.value = EncounterUiState(
                    isLoading = false,
                    pokemon = result
                )

            } catch (e: Exception) {

                // Send error to UI
                _uiState.value = EncounterUiState(
                    isLoading = false,
                    errorMessage = "Could not load Pokemon"
                )
            }
        }
    }
}