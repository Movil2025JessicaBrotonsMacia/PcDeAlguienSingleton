package net.iessochoa.sergiocontreras.pcdealguien.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iessochoa.sergiocontreras.pcdealguien.ui.components.DynamicSelectTextField
import net.iessochoa.sergiocontreras.pcdealguien.ui.components.PokemonItem
import net.iessochoa.sergiocontreras.pcdealguien.ui.theme.PCdeAlguienTheme
import net.iessochoa.sergiocontreras.pcdealguien.ui.theme.Typography

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier
) {

    // Observamos el estado del ViewModel
    val viewModel: PokemonViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedGen = uiState.selectedGeneration
    val currentState = uiState.currentState
    val generations = uiState.generations //Total de generaciones, esto es una List<GenerationDto>

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
            options = (1..generations.size).map { it.toString() },
            label = "Generación",
            onValueChangedEvent =  { viewModel.setSelectedGeneration(it) }
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



@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PCdeAlguienTheme {
        PokemonScreen()
    }
}