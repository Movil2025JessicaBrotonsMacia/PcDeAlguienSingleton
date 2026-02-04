package net.iessochoa.sergiocontreras.pcdealguien.network

import retrofit2.http.GET
import retrofit2.http.Path

//SEGUNDO, una fun para cada endPoint
interface PokemonApiService {
    //Decirle de donde vamos a obtener los datos. A la bas eurl, solo le tenemos que añadir /generation
    //Cuando haga una consulta lo que me va a devolver es un GenerationResponse, me va a devolver todas las generaciones que haya
    @GET("generation")
    suspend fun getGenerations(): GenerationResponse

    //Otro enpoint que necesitaremos consultar, devolvemos los pokemons de una generación
    @GET("generation/{id}/")
    suspend fun getPokemonsByGeneration(@Path("id")id: Int): PokemonsByGenerationRespone
}