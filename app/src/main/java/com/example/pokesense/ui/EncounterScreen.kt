package com.example.pokesense.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage                          // Goofy ass Android can't display image from url, so we need this
import com.example.pokesense.viewmodel.EncounterViewModel

import androidx.compose.runtime.LaunchedEffect          // LaunchedEffect = reacts when catch result changes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.example.pokesense.ui.theme.PressStart

// EncounterScreen = shows loading, error, or Pokemon result
@Composable
fun EncounterScreen(
    modifier: Modifier = Modifier,
    viewModel: EncounterViewModel,
    onGoHome: () -> Unit,
    onResult: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val pokemon = uiState.pokemon
    val errorMessage = uiState.errorMessage

    // Open ResultScreen after catch result is ready
    LaunchedEffect(uiState.catchResult) {
        if (uiState.catchResult != null) {
            onResult()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading) {
            Text(
                "Searching...",
                Modifier.padding(40.dp),
                fontSize = 25.sp
            )
        } else if (uiState.isCatching) {
            Text(
                "Trying to catch Pokemon...",
                Modifier.padding(40.dp),
                fontSize = 25.sp
            )
        } else if (errorMessage != null) {
            Text(
                errorMessage,
                Modifier.padding(40.dp),
                fontSize = 25.sp
            )
        } else if (pokemon != null) {
            Text(
                "A wild ${pokemon.name} appeared!",
                Modifier.padding(40.dp),
                fontSize = 20.sp
            )

            AsyncImage(
                model = pokemon.sprites.frontDefault,
                contentDescription = pokemon.name,
                Modifier
                    .size(300.dp)
                    .padding(30.dp)
            )

            //Display encountered pokemon's type(s)
            Text(
                text = pokemon.types.joinToString(" / ") { it.type.name },
                fontSize = 12.sp
            )

            //Display sensor values captured at encounter time
            Text(
                "Light: ${uiState.lightLevel} lux",
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                "Temperature: ${uiState.temperature} °C",
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        } else {
            Text(
                "No encounter started",
                Modifier.padding(40.dp),
                fontSize = 22.sp
            )
        }
        Row(
            modifier = modifier.padding(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.catchPokemon()
                },
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(3.dp, Color(0xFF001F5B)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00A86B),
                    contentColor = Color(0xFF001F5B)
                ),
                modifier = Modifier.padding(30.dp),
                enabled = pokemon != null && !uiState.isCatching
            ) {
                Text(
                    "Catch",
                    fontFamily = PressStart,
                    fontSize = 14.sp
                )
            }
            Button(
                onClick = onGoHome,
                Modifier.padding(30.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(3.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCA3433),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Flee",
                    fontFamily = PressStart,
                    fontSize = 14.sp
                )
            }
        }
    }
}