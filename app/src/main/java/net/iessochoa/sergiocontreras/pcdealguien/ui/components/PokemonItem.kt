package net.iessochoa.sergiocontreras.pcdealguien.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.iessochoa.sergiocontreras.pcdealguien.network.PokemonDto

@Composable
fun PokemonItem(pokemon: PokemonDto) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp).fillMaxWidth()
        ) {
            // TODO 5: Construir la URL de la imagen
            // 1. Obtener el ID desde la URL del pokemon (pokemon.url)
            // 2. Usar: https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id}.png

            //Separamos la URL por / para obtener el id
            val splittedUrl = pokemon.url.split("/")
            val id = splittedUrl[splittedUrl.size - 2]

            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png" // <-- IMPLEMENTAR AQUÍ LOGICA

            AsyncImage(
                model = imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = pokemon.name.uppercase(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}