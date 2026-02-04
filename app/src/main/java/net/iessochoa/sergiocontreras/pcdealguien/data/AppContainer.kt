package net.iessochoa.sergiocontreras.pcdealguien.data

import kotlinx.serialization.json.Json
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonApiService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.getValue

//CUARTO
interface AppContainer {
    val pokemonRepository: PokemonRepository //Primera dependencia, el repositorio
}

class DefaultAppContainer: AppContainer { //Implementa AppContainer
    private val baseUrl = "https://pokeapi.co/api/v2/"

    //Empezamos a inicializar primero Retrofit, siempre lazy.
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder() //Instancia un retrofit básico y con los .add, .as... lo vamos modificando
            .addConverterFactory(Json {
                ignoreUnknownKeys = true //Sin esto tendríamos que mapear el JSON tal cual nos viene con los campos
            }.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    //Esto en el singleton no lo necesitaríamos, lo de arriba si
    private val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

    override val pokemonRepository: PokemonRepository by lazy {
        NetworkPokemonRepository(retrofitService)

    }
}