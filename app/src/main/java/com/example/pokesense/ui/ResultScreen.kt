package com.example.pokesense.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokesense.ui.theme.PressStart
import com.example.pokesense.viewmodel.EncounterViewModel

// ResultScreen = shows encounter result and navigation buttons
@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    viewModel: EncounterViewModel,
    onGoHome: () -> Unit,
    onGoCaughtList: () -> Unit
) {
    // uiState = gets catch result and Pokemon from ViewModel
    val uiState by viewModel.uiState.collectAsState()

    val pokemon = uiState.pokemon
    val catchResult = uiState.catchResult

    // Screen formatting
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = "Encounter Result",
//            modifier = Modifier.padding(20.dp),
//            fontSize = 34.sp
//        )

        if (catchResult == true) {
            Text(
                text = "Gotcha! ${pokemon?.name} was caught!",
                modifier = Modifier.padding(20.dp),
                fontSize = 22.sp
            )
        } else if (catchResult == false) {
            Text(
                text = "Oh no! ${pokemon?.name} escaped!",
                modifier = Modifier.padding(20.dp),
                fontSize = 22.sp
            )
        } else {
            Text(
                text = "No catch result",
                modifier = Modifier.padding(20.dp),
                fontSize = 22.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onGoHome,
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(3.dp, Color(0xFF001F5B)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF001F5B)
                )
            ) {
                Text(
                    text = "Return Home",
                    fontFamily = PressStart,
                    fontSize = 10.sp
                )
            }

            Button(
                onClick = onGoCaughtList,
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(3.dp, Color(0xFF001F5B)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF001F5B)
                )
            ) {
                Text(
                    text = "View Caught List",
                    fontFamily = PressStart,
                    fontSize = 10.sp
                )
            }
        }
    }
}