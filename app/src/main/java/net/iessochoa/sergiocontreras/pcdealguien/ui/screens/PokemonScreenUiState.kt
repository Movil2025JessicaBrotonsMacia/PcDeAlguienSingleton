package net.iessochoa.sergiocontreras.pcdealguien.ui.screens

import net.iessochoa.sergiocontreras.pcdealguien.network.GenerationDto
import net.iessochoa.sergiocontreras.pcdealguien.network.GenerationResponse
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonDto

//Guardamos el estado de lo que nos hace falta pintar por pantalla
data class PokemonScreenUiState (
    //val pokemonList: List<PokemonDto> = emptyList(), //Lista de pokemons
    //val totalGenerations: Int = 1, //Cuántas generaciones hay, valor por defecto 1

    //Lista de generaciones que hay para llenar el dropDown (1,2,3...)
    val generations: List<GenerationDto> = emptyList(),
    val selectedGeneration: Int = 1,
    val currentState: RequestStatus = RequestStatus.Idle

)

//Depende del estado nos va a pintar un estado u otro.
sealed interface RequestStatus{
    object IsLoading: RequestStatus
    data class Success(val pokemonList: List<PokemonDto>): RequestStatus
    object Error : RequestStatus
    object Idle: RequestStatus
}