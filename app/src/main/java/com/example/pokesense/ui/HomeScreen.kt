package com.example.pokesense.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// HomeScreen = first screen user sees
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStartEncounter: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text("PokeSense")

        Button(
            onClick = onStartEncounter
        ) {
            Text("Initiate Encounter")
        }
    }
}