package com.example.actividadevaluativaspotify.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.PictureInPictureAlt
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.actividadevaluativaspotify.R
import com.example.actividadevaluativaspotify.shared.ScaffoldViewModel

@Composable
fun ReproductorMusica(viewModelScaffold: ScaffoldViewModel,
                      funcionNavegarPlayer: () -> Unit,) {
    val contexto = LocalContext.current

    /* Variables de estado */
    val exoPlayerViewModel: ScaffoldViewModel = viewModelScaffold
    val duracion by exoPlayerViewModel.duracion.collectAsStateWithLifecycle()
    val posicion by exoPlayerViewModel.progreso.collectAsStateWithLifecycle()
    val reproductor by exoPlayerViewModel.exoPlayer.collectAsState()
    val estaReproduciendo by exoPlayerViewModel.estaReproduciendo.collectAsState()
    val reproduciendoAleatorio by exoPlayerViewModel.modoAleatorio.collectAsState()
    val reproduciendoRepetir by exoPlayerViewModel.modoRepetir.collectAsState()
    viewModelScaffold._mostrarBarraInferior.value = false


    if (reproductor == null) {
        exoPlayerViewModel.crearExoPlayer(contexto)
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }

    Image(
        painter = painterResource(id = R.drawable.cascos),
        contentDescription = "fondo",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        MostrarImagenCancion(imagenId = exoPlayerViewModel.imagenCancionActual.value)

        Box(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Column {
                FormatoTextoCancion(texto = "Canción: ${exoPlayerViewModel.nombreCancionActual.value}")
                FormatoTexto(texto = "Artista: ${exoPlayerViewModel.nombreAlbumActual.value}")
            }
        }

        Slider(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            value = posicion.toFloat(),
            onValueChange = { newValue ->
                exoPlayerViewModel.exoPlayer.value!!.seekTo((newValue).toLong())
            },
            valueRange = 0f..duracion.toFloat(),
            onValueChangeFinished = { /*TODO*/ }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FormatoTiempo(tiempo = posicion)
            FormatoTiempo(tiempo = duracion)
        }

        Box(modifier = Modifier
            .padding(top = 25.dp, bottom = 16.dp)){
            Row {
                IconButton(onClick = { exoPlayerViewModel.CambiarRepetir(contexto) }) {
                    if (reproduciendoRepetir) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Repetir", tint = Color.Green)
                    } else {
                        Icon(Icons.Filled.Autorenew, contentDescription = "Repetir", tint = Color.White)
                    }
                }
                IconButton(onClick = { exoPlayerViewModel.AnteriorCancion(contexto) }) {
                    Icon(Icons.Filled.SkipPrevious, contentDescription = "Anterior", tint = Color.White)
                }
                IconButton(onClick = { exoPlayerViewModel.PausarOSeguirMusica() }) {
                    if (estaReproduciendo) {
                        Icon(Icons.Filled.Pause, contentDescription = "Pausar", tint = Color.White)
                    } else {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Reproducir", tint = Color.White)
                    }
                }
                IconButton(onClick = { exoPlayerViewModel.SiguienteCancion(contexto) }) {
                    Icon(Icons.Filled.SkipNext, contentDescription = "Siguiente", tint = Color.White)
                }
                IconButton(onClick = { exoPlayerViewModel.CambiarAletorio(contexto) }) {
                    if (reproduciendoAleatorio) {
                        Icon(Icons.Filled.Shuffle, contentDescription = "Aleatorio", tint = Color.Green)
                    } else {
                        Icon(Icons.Filled.Repeat, contentDescription = "Aleatorio", tint = Color.White)
                    }
                }
                IconButton(onClick = funcionNavegarPlayer) {
                    Icon(Icons.Default.PictureInPictureAlt, contentDescription = "A reproductor", tint = Color.White)
                }
            }
        }

    }
}

@Composable
fun MostrarImagenCancion(imagenId: Int) {
    val tamanoImagen = 370.dp
    Image(
        painter = painterResource(id = imagenId),
        contentDescription = "Imagen de la canción actual",
        modifier = Modifier
            .size(tamanoImagen)
            .padding(top = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun FormatoTextoCancion(texto: String) {
    Text(
        text = texto,
        style = TextStyle(
            fontSize = 20.sp, // Tamaño de fuente
            fontWeight = FontWeight.Bold, // Peso de la fuente
            color = Color.White // Color de la fuente
        )
    )
}

@Composable
fun FormatoTexto(texto: String) {
    Text(
        text = texto,
        style = TextStyle(
            fontSize = 20.sp, // Tamaño de fuente
            color = Color.White // Color de la fuente
        )
    )
}

@Composable
fun FormatoTiempo(tiempo: Int) {
    Text(
        text = String.format("%02d:%02d", tiempo / 60000, (tiempo % 60000) / 1000),
        style = TextStyle(
            fontSize = 20.sp, // Tamaño de fuente
            color = Color.White // Color de la fuente
        ),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}