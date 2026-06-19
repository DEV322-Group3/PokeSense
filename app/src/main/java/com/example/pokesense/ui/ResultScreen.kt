package com.example.pokesense.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit
)


{ // Screen formatting
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This is the results screen",
            Modifier.padding(20.dp),
            fontSize = 44.sp
        )

        Button(
            onClick = onGoHome,// Home button
            Modifier.padding(10.dp)
        ) {
            Text(
                text = "Return Home",
                fontSize = 25.sp
            )
        }
    }
}