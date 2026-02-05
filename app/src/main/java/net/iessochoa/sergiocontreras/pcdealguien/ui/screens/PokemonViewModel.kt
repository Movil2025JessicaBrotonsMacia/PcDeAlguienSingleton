package net.iessochoa.sergiocontreras.pcdealguien.ui.screens

import androidx.compose.runtime.currentComposer
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
import net.iessochoa.sergiocontreras.pcdealguien.data.PokemonRepository

class PokemonViewModel(): ViewModel() {

    //Inicializar las generaciones
    init {
        fetchGenerations()
    }

    // Estado para la lista de pokemons
    val _uiState = MutableStateFlow(PokemonScreenUiState())
    val uiState: StateFlow<PokemonScreenUiState> = _uiState.asStateFlow()
    //val repository = pokemonRepository


    // Función para cargar generaciones en la lista
    fun fetchGenerations(){
        viewModelScope.launch {
            try {
                val response = PokemonRepository.getGenerations()

                _uiState.update {
                    it.copy(
                        generations = response.generations
                    )
                }
            } catch (e: Exception){
                _uiState.update {
                    it.copy(currentState = RequestStatus.Error)
                }
            }
        }
    }

    // Función para cargar pokemons por generaciones, se ejecuta cuando elegimos una generación
    fun fetchPokemonByGeneration(generationId: Int) {

        viewModelScope.launch { //Esto se va a ejecutar de forma secuencial
            try {
                //Entre que hacemos la petición y llega, vamos a hacer que el request esté cargando
                _uiState.update { currentState ->
                    currentState.copy(
                        currentState = RequestStatus.IsLoading
                    )
                }

                val response = PokemonRepository.getPokemonsByGeneration(generationId)

                _uiState.update {
                    it.copy(
                        currentState = RequestStatus.Success(response.pokemons),
                        selectedGeneration = generationId
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace() // Esto te dirá la línea exacta del fallo
                _uiState.update { it ->
                    it.copy(
                        currentState = RequestStatus.Error
                    )
                }
            }
        }
    }

    fun setSelectedGeneration(generation: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedGeneration = generation.toInt()
            )
        }
    }


//    fun onValueChangedEvent(generation: String){
//        _uiState.update { currentState ->
//            currentState.copy(
//                selectedGeneration = generation.toInt()
//            )
//
//        }
//    }
//
//    companion object{
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as PokemonApplication)
//                val pokemonRepository = application.container.pokemonRepository
//                PokemonViewModel(pokemonRepository = pokemonRepository)
//            }
//        }
//    }
}