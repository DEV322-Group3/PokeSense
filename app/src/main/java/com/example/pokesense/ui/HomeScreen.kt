package com.example.pokesense.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokesense.ui.theme.PressStart

// HomeScreen = first screen user sees
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStartEncounter: () -> Unit,
    onCaughtList: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PokéSense",
            Modifier.padding(20.dp),
            fontSize = 40.sp
        )

        Button(
            onClick = onStartEncounter,
            Modifier.padding(10.dp),
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(3.dp, Color(0xFF001F5B)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF001F5B)
            )
        ) {
            Text(
                text = "Find a Pokemon!",
                fontFamily = PressStart,
                fontSize = 10.sp
            )
        }
        Button(
            onClick = onCaughtList, //loadCaughtPokemon()
            Modifier.padding(10.dp),
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(3.dp, Color(0xFF001F5B)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF001F5B)
            )
        ) {
            Text(
                text = "View Caught Pokemon",
                fontFamily = PressStart,
                fontSize = 10.sp
            )
        }
    }
}