package com.example.actividadevaluativaspotify.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.actividadevaluativaspotify.R
import com.example.actividadevaluativaspotify.persistence.persistenceViewModel
import com.example.actividadevaluativaspotify.shared.ScaffoldViewModel

@Composable
fun PantallaInicio(viewModelScaffold: ScaffoldViewModel) {

    viewModelScaffold._mostrarBarraInferior.value = true
    val exoPlayerViewModel: ScaffoldViewModel = viewModel()
    val estaReproduciendo by exoPlayerViewModel.estaReproduciendo.collectAsState()
    val persistenceViewModel: persistenceViewModel = viewModel()

    Column {
        Column(
            Modifier
                .background(Color(33, 33, 33))
                .weight(9f),
        ) {

            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(Modifier.height(200.dp)) {
                persistenceViewModel.allsongs.forEach() {
                    item {
                        CardPlaylist(it, viewModelScaffold)
                    }
                }
            }
            Text(
                text = "Tus mejores Playlist",
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold
            )
            Row {
                cardsGrandes(titulo = "Canciones guapardas", imagen = R.drawable.bbo, list = persistenceViewModel.songs, viewModelScaffold)
                cardsGrandes(titulo = "Canciones meme", imagen = R.drawable.bobesponja, list = persistenceViewModel.funnysongs, viewModelScaffold)
            }
        }

    }

}

@Composable
fun cardsGrandes(
    titulo: String,
    imagen: Int,
    list: List<Pair<Pair<Pair<Int, String>, String>, Int>>? = null,
    viewModelScaffold: ScaffoldViewModel
) {
    var contexto = LocalContext.current

    Column(
        Modifier
            .padding(5.dp)
            .clickable {
                viewModelScaffold.cargarCanciones(contexto, list!!)
            }) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = "",
            Modifier
                .size(180.dp)
                .clip(RectangleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = titulo,
            fontSize = 20.sp,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPlaylist(cancion: Pair<Pair<Pair<Int, String>, String>, Int>, viewModelScaffold: ScaffoldViewModel) {
    val contexto = LocalContext.current
    Card(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(4.dp),
        onClick = { viewModelScaffold.playCancionSuelta(cancion, contexto) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(83, 83, 83)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = cancion.second),
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RectangleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop

            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = cancion.first.first.second,
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }

}