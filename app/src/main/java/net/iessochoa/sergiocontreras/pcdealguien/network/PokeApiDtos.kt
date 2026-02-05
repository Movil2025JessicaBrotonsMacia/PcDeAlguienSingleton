package net.iessochoa.sergiocontreras.pcdealguien.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//PRIMERO, LO QUE PONE RESPONSE ES PARA DEVOLVERLO EN LA APISERVICE, LO DEMÁS PARA USARLO YO

//Lista de todas las generaciones
@Serializable
data class GenerationResponse(
    //results es el nombre del campo que muestra las generaciones por defecto
    @SerialName("results") val generations: List<GenerationDto> //Contenedor que captura todas las generaciones
)


//Lista de cada generación
@Serializable
data class GenerationDto(
    //No ponemos SerialName porque nos queremos quedar con el nombre por defecto.
    val name: String,
    val url: String
)


//Lista de pokemons de una generación
@Serializable
data class PokemonsByGenerationRespone(
    @SerialName("pokemon_species") val pokemons: List<PokemonDto>
)


//Cada pokemon
@Serializable
data class PokemonDto(
    val name: String,
    val url: String
)