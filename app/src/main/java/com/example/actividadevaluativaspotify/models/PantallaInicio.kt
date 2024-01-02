package com.example.actividadevaluativaspotify.models

import android.graphics.drawable.Icon
import androidx.collection.intFloatMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    if (reproductor == null) {
        exoPlayerViewModel.crearExoPlayer(contexto)
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("${exoPlayerViewModel.nombreCancionActual.value}")
        MostrarImagenCancion(imagenId = exoPlayerViewModel.imagenCancionActual.value)

        Slider(
            value = posicion.toFloat(),
            onValueChange = { /*TODO*/ },
            valueRange = 0f..duracion.toFloat(),
            onValueChangeFinished = { /*TODO*/ }
        )

        Row (horizontalArrangement = Arrangement.SpaceEvenly){
            Text("${posicion / 1000}")
            Text("${duracion / 1000}")
        }

        Row {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Repeat, contentDescription = "Repetir")
            }
            IconButton(onClick = { exoPlayerViewModel.AnteriorCancion(contexto) }) {
                Icon(Icons.Filled.SkipPrevious, contentDescription = "Anterior")
            }
            IconButton(onClick = { exoPlayerViewModel.PausarOSeguirMusica() }) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
            }
            IconButton(onClick = { exoPlayerViewModel.SiguienteCancion(contexto) }) {
                Icon(Icons.Filled.SkipNext, contentDescription = "Siguiente")
            }
            IconButton(onClick = { exoPlayerViewModel.ReproducirCancionAleatoria(contexto) }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Aleatorio")
            }
        }

    }
}

@Composable
fun MostrarImagenCancion(imagenId: Int) {
    val tamañoImagen = 200.dp
    Image(
        painter = painterResource(id = imagenId),
        contentDescription = "Imagen de la canción actual",
        modifier = Modifier.size(tamañoImagen)
    )
}