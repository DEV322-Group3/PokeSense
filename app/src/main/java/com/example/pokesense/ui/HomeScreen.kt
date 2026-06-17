package com.example.pokesense.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// HomeScreen = first screen user sees
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStartEncounter: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PokeSense",
            Modifier.padding(20.dp),
            fontSize = 44.sp
        )

        Button(
            onClick = onStartEncounter,
            Modifier.padding(10.dp)

        ) {
            Text(
                text = "Initiate Encounter",
                fontSize = 25.sp
            )
        }
        Button(
            onClick = onStartEncounter, //loadCaughtPokemon()
            Modifier.padding(10.dp)
        ) {
            Text(
                text = "Caught Pokemon",
                fontSize = 25.sp
            )
        }
    }
}