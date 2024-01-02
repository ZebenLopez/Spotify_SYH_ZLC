package com.example.actividadevaluativaspotify.models

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.actividadevaluativaspotify.shared.PantallaInicioViewModel

@Composable
fun PantallaInicio(navController: NavHostController) {
    val contexto = LocalContext.current

    /* Variables de estado */
    val exoPlayerViewModel: PantallaInicioViewModel = viewModel()
    val duracion by exoPlayerViewModel.duracion.collectAsStateWithLifecycle()
    val posicion by exoPlayerViewModel.progreso.collectAsStateWithLifecycle()
    val reproductor by exoPlayerViewModel.exoPlayer.collectAsState()

    /* TODO: Llamar a crearExoPlayer y hacerSonarMusica */
    if(reproductor ==null){
        exoPlayerViewModel.crearExoPlayer(contexto)
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }


    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text("La duración  de este temazo es ${duracion/1000} " +
                "y vamos por el segundo ${posicion/1000}")
        Button(onClick = { exoPlayerViewModel.PausarOSeguirMusica() }) {
            Text("Play")
        }
        Button(onClick = {
            // En este caso modifico tanto la canción de este componente...
            exoPlayerViewModel.CambiarCancion(contexto);
        }) {
            Text("Next")
        }

    }
}