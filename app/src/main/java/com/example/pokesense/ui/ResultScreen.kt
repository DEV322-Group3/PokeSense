package com.example.pokesense.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ResultScreen = shows encounter result and navigation buttons
@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit,
    onGoCaughtList: () -> Unit
) {
    // Screen formatting
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO later: change this based on caught / failed / fled / timer expired
        Text(
            text = "Encounter Result",
            modifier = Modifier.padding(20.dp),
            fontSize = 34.sp
        )

        Text(
            text = "Catch result will show here",
            modifier = Modifier.padding(20.dp),
            fontSize = 22.sp
        )

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onGoHome, // Home button
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Return Home",
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = onGoCaughtList, // Caught List button
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Caught List",
                    fontSize = 20.sp
                )
            }
        }
    }
}

