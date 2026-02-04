package net.iessochoa.sergiocontreras.pcdealguien.data

import net.iessochoa.sergiocontreras.pcdealguien.network.GenerationDto
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonApiService
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonDto

//TERCERO
// Interfaz para facilitar testing (Best Practice)
interface PokemonRepository{
    suspend fun getPokemonsByGeneration(generation: Int): List<PokemonDto> //Devuelve lista de pokemons, se parece a PokemonApiService
    suspend fun getGenerations(): List<GenerationDto> //Devuelve lista de generaciones
}

// Implementación concreta. Inyectamos el servicio, no lo creamos aquí
class NetworkPokemonRepository(
    private val apiService: PokemonApiService
): PokemonRepository {
    override suspend fun getPokemonsByGeneration(generation: Int): List<PokemonDto> {
        return apiService.getPokemonsByGeneration(generation).pokemons
    }

    override suspend fun getGenerations(): List<GenerationDto> {
        return apiService.getGenerations().generations
    }


}