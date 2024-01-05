package com.example.actividadevaluativaspotify.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.actividadevaluativaspotify.models.BarraInferior
import com.example.actividadevaluativaspotify.models.BarraSuperior
import com.example.actividadevaluativaspotify.models.PantallaInicio
import com.example.actividadevaluativaspotify.models.ReproductorMusica
import com.example.actividadevaluativaspotify.rutas.Rutas
import com.example.actividadevaluativaspotify.shared.ScaffoldViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrafoNavegacion() {
    val navController = rememberNavController()

    // El parametro route nos devuelve, en formato string, la ruta actual.
    val entradaNavActual by navController.currentBackStackEntryAsState()

    val rutaActual = entradaNavActual?.destination?.route

    // View model general que controla diversos valores del Scaffold -> el título y la barra de navegación
    val viewModelScaffold: ScaffoldViewModel = viewModel()

    Scaffold(topBar = {
        BarraSuperior()
    },
        bottomBar = {
            if (viewModelScaffold.mostrarBarraInferior.value) {
                BarraInferior( funcionNavegarReproductor = {
                    navController.navigate(Rutas.Reproductor.ruta)
                }, viewModelScaffold = viewModelScaffold)
            }
        },
        content = {
            // paddingValues representa los dp que hay para evitar que el contenido se solape con las barras
                paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(navController = navController, startDestination = Rutas.Inicio.ruta) {

                    composable(Rutas.Inicio.ruta) {
                        PantallaInicio(viewModelScaffold)
                    }

                    composable(Rutas.Reproductor.ruta) {
                        ReproductorMusica(viewModelScaffold, funcionNavegarPlayer = {
                            navController.navigate(Rutas.Inicio.ruta)
                        })
                    }
                }
            }
        })
}
