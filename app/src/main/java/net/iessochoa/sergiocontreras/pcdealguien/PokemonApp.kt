package net.iessochoa.sergiocontreras.pcdealguien

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.iessochoa.sergiocontreras.pcdealguien.ui.screens.PokemonScreen

@Composable
fun PokemonApp(modifier:Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        PokemonScreen(modifier = Modifier.padding(innerPadding))
    }
}