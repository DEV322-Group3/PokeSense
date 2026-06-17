package com.example.pokesense.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading) {
            Text("Looking for Pokemon...",
                Modifier.padding(40.dp),
                fontSize = 22.sp
            )
        }
        else if (errorMessage != null) {
            Text(errorMessage,
                Modifier.padding(40.dp),
                fontSize = 22.sp
            )
        }
        else if (pokemon != null) {
            Text("A wild ${pokemon.name} appeared!",
                Modifier.padding(40.dp),
                fontSize = 22.sp
            )

            AsyncImage(
                model = pokemon.sprites.frontDefault,
                contentDescription = pokemon.name,
                Modifier.size(200.dp)
                    .padding (30.dp)
            )
        }
        else {
            Text("No encounter started",
                Modifier.padding(40.dp),
                fontSize = 22.sp
            )
        }
        Row(
            modifier = modifier.padding(),
            horizontalArrangement = Arrangement.Center
            ) {
            Button(
                onClick = onGoHome,
                Modifier.padding(30.dp)
            ) {
                Text("Catch",
                    fontSize = 25.sp)
            }
            Button(
                onClick = onGoHome,
                Modifier.padding(30.dp)
            ) {
                Text("Flee",
                    fontSize = 25.sp)
            }
        }
    }
}