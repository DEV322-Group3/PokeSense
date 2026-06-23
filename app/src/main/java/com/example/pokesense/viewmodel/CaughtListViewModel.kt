package com.example.pokesense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokesense.data.local.PokemonCatchEntity
import com.example.pokesense.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

// CaughtListViewModel = controls the caught list screen + which Pokemon is selected for detail view
class CaughtListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    // caughtList = live list of every saved catch, newest first
    val caughtList: StateFlow<List<PokemonCatchEntity>> =
        repository.getAllCaughtPokemon()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // _selectedPokemon = which catch is currently shown in the detail overlay
    private val _selectedPokemon = MutableStateFlow<PokemonCatchEntity?>(null)
    val selectedPokemon: StateFlow<PokemonCatchEntity?> = _selectedPokemon.asStateFlow()

    // selectPokemon = open the detail overlay for this catch
    fun selectPokemon(pokemon: PokemonCatchEntity) {
        _selectedPokemon.value = pokemon
    }

    // clearSelection = close the detail overlay
    fun clearSelection() {
        _selectedPokemon.value = null
    }
}