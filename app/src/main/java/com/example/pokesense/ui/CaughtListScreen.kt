package com.example.pokesense.ui

import android.app.Dialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.pokesense.data.local.PokemonCatchEntity
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
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text( // Screen title
            text = "CAUGHT POKEMON",
            modifier = Modifier.padding(20.dp),
            fontSize = 28.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(caughtList) { pokemon ->
                AsyncImage(
                    model = pokemon.spriteUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { viewModel.selectPokemon(pokemon) }
                )
            }
        }

        Button( // Home button
            onClick = onGoHome,
            modifier = Modifier.padding(30.dp)
        ) {
            Text("Go Home", fontSize = 20.sp)
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
                        fontSize = 20.sp,
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