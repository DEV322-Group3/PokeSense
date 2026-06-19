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


@Composable
fun CaughtListScreen(
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit
)



{
    Text( // Screen title
        text = "This is the Caught List screen",
        Modifier.padding(20.dp),
        fontSize = 44.sp
    )
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.padding(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button( // Home button
                onClick = onGoHome,
                Modifier.padding(30.dp)
            ) {
                Text("Go Home",
                    fontSize = 25.sp)
            }
        }
    }
}