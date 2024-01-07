package com.example.actividadevaluativaspotify.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.actividadevaluativaspotify.R
import com.example.actividadevaluativaspotify.shared.ScaffoldViewModel


@Composable
fun BarraInferior( funcionNavegarReproductor: () -> Unit,
                  viewModelScaffold: ScaffoldViewModel){

    val contexto = LocalContext.current

    /* Variables de estado */
    val exoPlayerViewModel: ScaffoldViewModel = viewModelScaffold
    val duracion by exoPlayerViewModel.duracion.collectAsStateWithLifecycle()
    val posicion by exoPlayerViewModel.progreso.collectAsStateWithLifecycle()
    val reproductor by exoPlayerViewModel.exoPlayer.collectAsState()
    val estaReproduciendo by exoPlayerViewModel.estaReproduciendo.collectAsState()
    val reproduciendoAleatorio by exoPlayerViewModel.modoAleatorio.collectAsState()
    val reproduciendoRepetir by exoPlayerViewModel.modoRepetir.collectAsState()
    val nombreCancionActual by exoPlayerViewModel.nombreCancionActual.collectAsState()
    val nombreAlbumActual by exoPlayerViewModel.nombreAlbumActual.collectAsState()

    if (reproductor == null) {
        println("Creando reproductor")
        exoPlayerViewModel.crearExoPlayer(contexto)
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }

    BottomAppBar(modifier = Modifier
        .fillMaxWidth(),
    containerColor = Color.DarkGray,) {
        Row {
            Box(modifier = Modifier
                .size(100.dp)
                .padding(start = 10.dp, end = 10.dp),
                contentAlignment = Alignment.Center){
                MostrarImagenCancionScafold(imagenId = exoPlayerViewModel.imagenCancionActual.value)
            }
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.Center,
                ){

                Text(text = nombreCancionActual,
                    style = TextStyle(
                        fontSize = 20.sp, // Tamaño de fuente
                        fontWeight = FontWeight.Bold, // Peso de la fuente
                        color = Color.White // Color de la fuente
                    )
                )

                Box(){
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
                        IconButton(onClick = funcionNavegarReproductor) {
                            Icon(Icons.Default.PictureInPicture, contentDescription = "A reproductor", tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagen de la barra superior",
                modifier = Modifier.size(60.dp))
            Text(text = "SoundSculpt",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun MostrarImagenCancionScafold(imagenId: Int) {
    Image(
        painter = painterResource(id = imagenId),
        contentDescription = "Imagen de la canción actual",
    )
}