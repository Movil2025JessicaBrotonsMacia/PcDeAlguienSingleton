package net.iessochoa.sergiocontreras.pcdealguien.ui

import net.iessochoa.sergiocontreras.pcdealguien.network.GenerationDto
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonDto

//Guardamos el estado de lo que nos hace falta pintar por pantalla
data class PokemonScreenUiState (
    //val pokemonList: List<PokemonDto> = emptyList(), //Lista de pokemons
    val selectedGeneration: Int = 1,
    val totalGenerations: Int = 1, //Cuántas generaciones hay, valor por defecto 1
    val currentState: RequestStatus = RequestStatus.Idle

)

//Depende del estado nos va a pintar un estado u otro.
sealed interface RequestStatus{
    object IsLoading: RequestStatus
    data class Success(val pokemonList: List<PokemonDto>): RequestStatus
    object Error : RequestStatus
    object Idle: RequestStatus
}