package net.iessochoa.sergiocontreras.pcdealguien.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonDto
import net.iessochoa.sergiocontreras.pcdealguien.ui.components.DynamicSelectTextField
import net.iessochoa.sergiocontreras.pcdealguien.ui.theme.PCdeAlguienTheme
import net.iessochoa.sergiocontreras.pcdealguien.ui.theme.Typography

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = viewModel(factory = PokemonViewModel.Factory),
    modifier: Modifier = Modifier) {
    // Observamos el estado del ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedGen = uiState.selectedGeneration
    val currentState = uiState.currentState
    val totalGenerations = uiState.totalGenerations

    //val pokemonList = uiState.pokemonList

    // Variables para el Dropdown (UI ya resuelta)
    var expanded by remember { mutableStateOf(false) }
    //var selectedGen by remember { mutableStateOf(1) }
    //val generations = (1..8).toList() // 8 Generaciones

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pokedex Retrofit", style = Typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // --- SELECTOR DE GENERACIÓN (Ya implementado) ---
        DynamicSelectTextField(
            selectedValue = selectedGen.toString(),
            options = (1..totalGenerations).toList().map { it.toString() },
            label = "Generación",
            onValueChangedEvent =  { viewModel.onValueChangedEvent(it) }
        )


        Spacer(modifier = Modifier.height(10.dp))

        // --- BOTÓN DE BÚSQUEDA ---
        Button(
            onClick = {
                // Llama al ViewModel
                viewModel.fetchPokemonByGeneration(selectedGen)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recuperar Pokémon")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- LISTA DE RESULTADOS ---
        when (val status = currentState) {
            is RequestStatus.Success -> {
                // Aquí pintamos la lista
                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(2), // 2 columnas
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(status.pokemonList) { pokemon ->
                        PokemonItem(pokemon)
                    }
                }
            }
            is RequestStatus.Error -> Text("Error")
            is RequestStatus.Idle -> Text("Selecciona una generación y pulsa buscar")
            RequestStatus.IsLoading -> CircularProgressIndicator()
        }

    }
}

@Composable
fun PokemonItem(pokemon: PokemonDto) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp).fillMaxWidth()
        ) {
            // TODO 5: Construir la URL de la imagen
            // 1. Obtener el ID desde la URL del pokemon (pokemon.url)
            // 2. Usar: https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id}.png

            //Separamos la URL por / para obtener el id
            val splittedUrl = pokemon.url.split("/")
            val id = splittedUrl[splittedUrl.size - 2]

            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png" // <-- IMPLEMENTAR AQUÍ LOGICA

            AsyncImage(
                model = imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = pokemon.name.uppercase(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PCdeAlguienTheme {
        PokemonScreen()
    }
}