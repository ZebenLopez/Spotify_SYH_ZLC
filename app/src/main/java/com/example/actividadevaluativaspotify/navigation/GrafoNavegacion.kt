package com.example.actividadevaluativaspotify.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.actividadevaluativaspotify.models.PantallaInicio
import com.example.actividadevaluativaspotify.rutas.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrafoNavegacion(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.Inicio.ruta){

        composable(Rutas.Inicio.ruta){
            PantallaInicio(navController = navController)
        }
    }

}
