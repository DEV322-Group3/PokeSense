package com.example.pokesense.ui

import android.app.Dialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.pokesense.data.local.PokemonCatchEntity
import com.example.pokesense.ui.theme.PressStart
import com.example.pokesense.viewmodel.CaughtListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CaughtListScreen(
    viewModel: CaughtListViewModel,
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit
) {
    val caughtList by viewModel.caughtList.collectAsState()
    val selectedPokemon by viewModel.selectedPokemon.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text( // Screen title
            text = "CAUGHT POKÉMON",
            modifier = Modifier.padding(20.dp),
            fontSize = 28.sp,
            lineHeight = 28.sp
        )

        if (caughtList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You haven't caught any Pokémon yet!!",
                    fontSize = 16.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(400.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(caughtList) { pokemon ->
                    AsyncImage(
                        model = pokemon.spriteUrl,
                        contentDescription = pokemon.name,
                        modifier = Modifier
                            .size(96.dp)
                            .clickable { viewModel.selectPokemon(pokemon) }
                    )
                }
            }
        }


        Button( // Home button
            onClick = onGoHome,
            modifier = Modifier.padding(
                30.dp,
            ),
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(3.dp, Color(0xFF001F5B)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF001F5B)
            )
        ) {
            Text(
                "Go Home",
                fontFamily = PressStart,
                fontSize = 10.sp
            )
        }
    }

    // Overlay shows on top of the grid when a pokemon is selected
    selectedPokemon?.let { pokemon ->
        PokemonDetailDialog(
            pokemon = pokemon,
            onDismiss = { viewModel.clearSelection() }
        )
    }
}

@Composable
fun PokemonDetailDialog(
    pokemon: PokemonCatchEntity,
    onDismiss: () -> Unit
) {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val formattedDate = formatter.format(Date(pokemon.timestampCaught))

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = pokemon.spriteUrl,
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = pokemon.name.uppercase(),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "CAUGHT: $formattedDate",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}