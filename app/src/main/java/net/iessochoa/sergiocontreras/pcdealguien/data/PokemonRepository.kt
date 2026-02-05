package net.iessochoa.sergiocontreras.pcdealguien.data

import kotlinx.serialization.json.Json
import net.iessochoa.sergiocontreras.pcdealguien.network.GenerationDto
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonApiService
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonDto
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

//TERCERO
// Interfaz para facilitar testing (Best Practice)
object PokemonRepository{
    private val baseUrl = "https://pokeapi.co/api/v2/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json {
                ignoreUnknownKeys = true //Para ignorar cosas del json que no vamos a usar
                coerceInputValues = true
            }.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    private val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

    //Devuelve lista de pokemons según generación
    suspend fun getPokemonsByGeneration(generation: Int) = retrofitService.getPokemonsByGeneration(generation)

    //Devuelve lista de generaciones, esto lo uso para mostrarlo en el dropDown
    suspend fun getGenerations() = retrofitService.getGenerations()

}

