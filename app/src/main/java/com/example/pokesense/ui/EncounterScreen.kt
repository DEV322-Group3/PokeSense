package com.example.pokesense.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.pokesense.viewmodel.EncounterViewModel

// EncounterScreen = shows loading, error, or Pokemon result
@Composable
fun EncounterScreen(
    modifier: Modifier = Modifier,
    viewModel: EncounterViewModel,
    onGoHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val pokemon = uiState.pokemon
    val errorMessage = uiState.errorMessage

    Column(
        modifier = modifier
    ) {
        if (uiState.isLoading) {
            Text("Looking for Pokemon...")
        }
        else if (errorMessage != null) {
            Text(errorMessage)
        }
        else if (pokemon != null) {
            Text("A wild ${pokemon.name} appeared!")

            AsyncImage(
                model = pokemon.sprites.frontDefault,
                contentDescription = pokemon.name
            )
        }
        else {
            Text("No encounter started")
        }

        Button(
            onClick = onGoHome
        ) {
            Text("Back Home")
        }
    }
}