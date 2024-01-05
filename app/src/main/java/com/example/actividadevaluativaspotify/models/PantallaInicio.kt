package com.example.actividadevaluativaspotify.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.actividadevaluativaspotify.R
import com.example.actividadevaluativaspotify.shared.ScaffoldViewModel

@Composable
fun PantallaInicio(viewModelScaffold: ScaffoldViewModel) {

    viewModelScaffold._mostrarBarraInferior.value = true
    val exoPlayerViewModel: ScaffoldViewModel = viewModel()
    val estaReproduciendo by exoPlayerViewModel.estaReproduciendo.collectAsState()

    Column {
        Column(
            Modifier
                .background(Color(33, 33, 33))
                .weight(9f),
        ) {
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .height(70.dp)
//                    .background(Color(18, 18, 18)), verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.logo),
//                    contentDescription = "",
//                )
//                Spacer(modifier = Modifier.width(20.dp))
//                Text(
//                    text = "SoundSculpt",
//                    fontSize = 30.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
//            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Column {
                    CardPlaylist()
                    CardPlaylist()
                    CardPlaylist()
                }
                Column {
                    CardPlaylist()
                    CardPlaylist()
                    CardPlaylist()
                }
            }
            Text(
                text = "Tus mejores Playlist",
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold
            )
            LazyRow(Modifier.padding(10.dp)) {
                items(10) {
                    cardsGrandes()
                    cardsGrandes()
                    cardsGrandes()
                    cardsGrandes()
                    cardsGrandes()
                    cardsGrandes()
                }
            }
        }

//        Row(
//            Modifier
//                .fillMaxWidth()
//                .height(70.dp)
//                .weight(1f)
//                .background(Color(18, 18, 18)),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Image(
//                    painter = painterResource(id = R.drawable.apoloydafne),
//                    contentDescription = "",
//                )
//                Spacer(modifier = Modifier.width(20.dp))
//                Column {
//                    Text(
//                        text = "Apolo y Dafne",
//                        fontSize = 20.sp,
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(text = "Sharif", fontSize = 15.sp, color = Color.White)
//                }
//            }
//            Spacer(modifier = Modifier.width(20.dp))
//            IconButton(onClick = { exoPlayerViewModel.PausarOSeguirMusica() }) {
//                if (estaReproduciendo) {
//                    Icon(
//                        Icons.Filled.Pause,
//                        contentDescription = "Pausar",
//                        tint = Color.White,
//                        modifier = Modifier.size(35.dp)
//                    )
//                } else {
//                    Icon(
//                        Icons.Filled.PlayArrow,
//                        contentDescription = "Reproducir",
//                        tint = Color.White,
//                        modifier = Modifier.size(35.dp)
//                    )
//                }
//            }
//        }

    }

}

@Composable
fun cardsGrandes() {

    Column(Modifier.padding(5.dp)) {
        Image(
            painter = painterResource(id = R.drawable.malviviendo),
            contentDescription = "",
            Modifier
                .size(180.dp)
                .clip(RectangleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Buena Onda",
            fontSize = 20.sp,
            color = Color.White,
        )
    }
}

@Composable
fun CardPlaylist() {
    Card(
        modifier = Modifier
            .height(80.dp)
            .width(200.dp)
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(83, 83, 83)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.lomejore),
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RectangleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop

            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Playlist Wapa",
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }

}