package com.example.actividadevaluativaspotify.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.actividadevaluativaspotify.shared.PantallaInicioViewModelDos

@Composable
@Preview
fun PantallaInicio() {
    val contexto = LocalContext.current

    /* Variables de estado */
    val exoPlayerViewModel: PantallaInicioViewModelDos = viewModel()
    val duracion by exoPlayerViewModel.duracion.collectAsStateWithLifecycle()
    val posicion by exoPlayerViewModel.progreso.collectAsStateWithLifecycle()
    val reproductor by exoPlayerViewModel.exoPlayer.collectAsState()
    val estaReproduciendo by exoPlayerViewModel.estaReproduciendo.collectAsState()
    val reproduciendoAleatorio by exoPlayerViewModel.modoAleatorio.collectAsState()
    val reproduciendoRepetir by exoPlayerViewModel.modoRepetir.collectAsState()


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
            onValueChange = { newValue ->
                exoPlayerViewModel.exoPlayer.value!!.seekTo((newValue).toLong())
            },
            valueRange = 0f..duracion.toFloat(),
            onValueChangeFinished = { /*TODO*/ }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = String.format("%02d:%02d", posicion / 60000, (posicion % 60000) / 1000)
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = String.format("%02d:%02d", duracion / 60000, (duracion % 60000) / 1000)
            )
        }

        Row {
            IconButton(onClick = { exoPlayerViewModel.CambiarRepetir(contexto) }) {
                if (reproduciendoRepetir) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Repetir")
                } else {
                    Icon(Icons.Filled.Autorenew, contentDescription = "Repetir")
                }
            }
            IconButton(onClick = { exoPlayerViewModel.AnteriorCancion(contexto) }) {
                Icon(Icons.Filled.SkipPrevious, contentDescription = "Anterior")
            }
            IconButton(onClick = { exoPlayerViewModel.PausarOSeguirMusica() }) {
                if (estaReproduciendo) {
                    Icon(Icons.Filled.Pause, contentDescription = "Pausar")
                } else {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Reproducir")
                }
            }
            IconButton(onClick = { exoPlayerViewModel.SiguienteCancion(contexto) }) {
                Icon(Icons.Filled.SkipNext, contentDescription = "Siguiente")
            }
            IconButton(onClick = { exoPlayerViewModel.CambiarAletorio(contexto) }) {
                if (reproduciendoAleatorio) {
                    Icon(Icons.Filled.Shuffle, contentDescription = "Aleatorio")
                } else {
                    Icon(Icons.Filled.Repeat, contentDescription = "Aleatorio")
                }
            }
        }

    }
}

@Composable
fun MostrarImagenCancion(imagenId: Int) {
    val tamanoImagen = 200.dp
    Image(
        painter = painterResource(id = imagenId),
        contentDescription = "Imagen de la canción actual",
        modifier = Modifier.size(tamanoImagen)
    )
}