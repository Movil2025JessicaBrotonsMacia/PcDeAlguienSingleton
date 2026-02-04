package net.iessochoa.sergiocontreras.pcdealguien.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import net.iessochoa.sergiocontreras.pcdealguien.PokemonApplication
import net.iessochoa.sergiocontreras.pcdealguien.data.PokemonRepository
import okhttp3.WebSocket

class PokemonViewModel(
    pokemonRepository: PokemonRepository
): ViewModel() {

    // Estado para la lista de pokemons
    val _uiState = MutableStateFlow(PokemonScreenUiState())
    val uiState: StateFlow<PokemonScreenUiState> = _uiState.asStateFlow()
    val repository = pokemonRepository


    // Función para llamar a la API
    fun fetchPokemonByGeneration(generationId: Int) {

        viewModelScope.launch { //Esto se va a ejecutar de forma secuencial
            try {
                //Entre que hacemos la petición y llega, vamos a hacer que el request esté cargando
                _uiState.update { currentState ->
                    currentState.copy(
                        currentState = RequestStatus.IsLoading
                    )
                }
                val response = repository.getPokemonsByGeneration(generationId)
                _uiState.update { currentState ->
                    currentState.copy(
                        currentState = RequestStatus.Success(response)
                    )
                }
                // TODO 4: Llamar a RetrofitClient y actualizar _pokemonList
                // val response = RetrofitClient.service.getGeneration(generationId)
                // _pokemonList.value = ...

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun onValueChangedEvent(generation: String){
        _uiState.update { currentState ->
            currentState.copy(
                selectedGeneration = generation.toInt()
            )

        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonApplication)
                val pokemonRepository = application.container.pokemonRepository
                PokemonViewModel(pokemonRepository = pokemonRepository)
            }
        }
    }
}