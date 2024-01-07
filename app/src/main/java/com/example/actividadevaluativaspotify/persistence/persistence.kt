package com.example.actividadevaluativaspotify.persistence

import androidx.lifecycle.ViewModel
import com.example.actividadevaluativaspotify.R

class persistenceViewModel : ViewModel() {
    val allsongs = listOf(
        R.raw.acelerao_luck to "Acelerao" to "Luck" to R.drawable.acelerao,
        R.raw.birrahumoyreflexiones_hardgz to "Birra, Humo y Reflexiones" to "Hard GZ" to R.drawable.kaosnomada,
        R.raw.cerocumplidos_luck to "Cero Cumplidos" to "Luck" to R.drawable._cumplidos,
        R.raw.ojodehalcon_hoke to "Ojo de Halcón" to "Hoke" to R.drawable.bbo,
        R.raw.malviviendo_sfdk to "Malviviendo" to "SFDK" to R.drawable.malviviendo,
        R.raw.sharif_apoloydafne to "Apolo y Dafne" to "Sharif" to R.drawable.apoloydafne,
        R.raw.songtwo to "Perro Salchicha" to "Falso Bad Bunny" to R.drawable.perrosalchicha,
        R.raw.songone to "Bob Esponja" to "Nickelodeon" to R.drawable.bobesponja
    )

    val songs = listOf(
        R.raw.acelerao_luck to "Acelerao" to "Luck" to R.drawable.acelerao,
        R.raw.birrahumoyreflexiones_hardgz to "Birra, Humo y Reflexiones" to "Hard GZ" to R.drawable.kaosnomada,
        R.raw.cerocumplidos_luck to "Cero Cumplidos" to "Luck" to R.drawable._cumplidos,
        R.raw.ojodehalcon_hoke to "Ojo de Halcón" to "Hoke" to R.drawable.bbo,
        R.raw.relsb_cruzcafune_ellegas_lomejore to "Los Mejore" to "Cruz Cafuné" to R.drawable.lomejore,
        R.raw.malviviendo_sfdk to "Malviviendo" to "SFDK" to R.drawable.malviviendo,
        R.raw.sharif_apoloydafne to "Apolo y Dafne" to "Sharif" to R.drawable.apoloydafne,
    )

    val funnysongs = listOf(
        R.raw.songtwo to "Perro Salchicha" to "Falso Bad Bunny" to R.drawable.perrosalchicha,
        R.raw.songone to "Bob Esponja" to "Nickelodeon" to R.drawable.bobesponja
    )
}



