package com.example.actividadevaluativaspotify.shared

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.actividadevaluativaspotify.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class PantallaInicioViewModel : ViewModel() {

    val cancionesIds = listOf(
        R.raw.songone,
        R.raw.songtwo,
        R.raw.malviviendo_sfdk,
        R.raw.sharif_apoloydafne,
        R.raw.relsb_cruzcafune_ellegas_lomejore
    )

    val cancionesNombres = listOf(
        "Bob Esponja - La canción",
        "Perro Salchicha - Falso Bad Bunny",
        "Malviviendo - SFDK",
        "Apolo y Dafne - Sharif",
        "Lo Mejor de Mi - Cruz Cafuné"
    )
    private val _nombreCancionActual = MutableStateFlow("")
    val nombreCancionActual = _nombreCancionActual.asStateFlow()

    val cancionesImagenes = listOf(
        R.drawable.bobesponja,
        R.drawable.perrosalchicha,
        R.drawable.malviviendo,
        R.drawable.apoloydafne,
        R.drawable.lomejore
    )
    private val _imagenCancionActual = MutableStateFlow(0)
    val imagenCancionActual = _imagenCancionActual.asStateFlow()

    private val _indiceActual = MutableStateFlow(0)
    val indiceActual = _indiceActual.asStateFlow()


    // El reproductor de musica, empieza a null
    val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    // La cancion actual que está sonando
    private val _actual = MutableStateFlow(R.raw.songone)
    val actual = _actual.asStateFlow()

    // La duración de la canción
    private val _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    // El progreso (en segundos) actual de la cancion
    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    //El slider
    val _posicionSlider = MutableStateFlow(0f)
    val posicionSlider = _posicionSlider.asStateFlow()

    //Cambiar el valor del boton play
    private val _estaReproduciendo = MutableStateFlow(false)
    val estaReproduciendo = _estaReproduciendo.asStateFlow()

    //Cambiar el valor del boton aleatorio
    private val _modoAleatorio = MutableStateFlow(false)
    val modoAleatorio = _modoAleatorio.asStateFlow()

    //Cambiar el valor del boton repetir
    private val _modoRepetir = MutableStateFlow(false)
    val modoRepetir = _modoRepetir.asStateFlow()

    fun crearExoPlayer(context: Context) {
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }


    fun hacerSonarMusica(context: Context) {
        _nombreCancionActual.value = cancionesNombres[indiceActual.value]
        _imagenCancionActual.value = cancionesImagenes[indiceActual.value]

        val mediaItem = MediaItem.fromUri(obtenerRuta(context, R.raw.songone))
        _exoPlayer.value!!.setMediaItem(mediaItem)

        _exoPlayer.value!!.playWhenReady = false

        _exoPlayer.value!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    _duracion.value = _exoPlayer.value!!.duration.toInt()

                    viewModelScope.launch {
                        while (isActive) {
                            _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                            _posicionSlider.value = _exoPlayer.value!!.currentPosition / 1000f
                            delay(1000)
                        }
                    }

                } else if (playbackState == Player.STATE_BUFFERING) {
                    // El Player está cargando el archivo, preparando la reproducción.
                    // No está listo, pero está en ello.
                } else if (playbackState == Player.STATE_ENDED) {
                    // El Player ha terminado de reproducir el archivo.
                    SiguienteCancion(context)
                } else if (playbackState == Player.STATE_IDLE) {
                    // El player se ha creado, pero no se ha lanzado la operación prepared.
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                if (modoRepetir.value) {
                    SiguienteCancion(context)
                }
            }
        })
    }

    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun PausarOSeguirMusica() {
        /* TODO: Si el reproductor esta sonando, lo pauso. Si no, lo reproduzco */
        if (!_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.play()
            _estaReproduciendo.value = true
        } else {
            _exoPlayer.value!!.pause()
            _estaReproduciendo.value = false
        }
    }

    fun SiguienteCancion(context: Context) {
        _estaReproduciendo.value = true

        if (modoAleatorio.value) {
            ReproducirCancionAleatoria(context)
        } else if (modoRepetir.value) {
            // No avanza al siguiente ítem si está en modo repetir y es la última canción
        } else if (indiceActual.value == cancionesIds.size - 1) {
            _indiceActual.value = 0
        } else {
            _indiceActual.value = (_indiceActual.value + 1) % cancionesIds.size
        }
        _nombreCancionActual.value = cancionesNombres[indiceActual.value]
        _imagenCancionActual.value = cancionesImagenes[indiceActual.value]

        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        val mediaItem =
            MediaItem.fromUri(obtenerRuta(context, cancionesIds[indiceActual.value]))
        _exoPlayer.value!!.setMediaItem(mediaItem)

        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true

    }

    fun AnteriorCancion(context: Context) {
        _estaReproduciendo.value = true

        if (modoAleatorio.value) {
            ReproducirCancionAleatoria(context)
        } else if (indiceActual.value == 0) {
            _indiceActual.value = cancionesIds.size - 1
        } else {
            _indiceActual.value = (_indiceActual.value - 1) % cancionesIds.size
        }

        _nombreCancionActual.value = cancionesNombres[indiceActual.value]
        _imagenCancionActual.value = cancionesImagenes[indiceActual.value]

        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        val mediaItem =
            MediaItem.fromUri(obtenerRuta(context, cancionesIds[indiceActual.value]))
        _exoPlayer.value!!.setMediaItem(mediaItem)

        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true

    }

    fun CambiarAletorio(context: Context) {
        _modoAleatorio.value = !_modoAleatorio.value
    }

    fun CambiarRepetir(context: Context) {
        _modoRepetir.value = !_modoRepetir.value
    }

    fun ReproducirCancionAleatoria(context: Context) {
        _estaReproduciendo.value = true

        val numeroAleatorio = (0 until cancionesIds.size).random()
        _indiceActual.value = numeroAleatorio
        val idCanciónActual = cancionesIds[_indiceActual.value]

        // Crea un nuevo MediaItem con la canción seleccionada
        val mediaItem = MediaItem.fromUri(obtenerRuta(context, idCanciónActual))
        _exoPlayer.value!!.setMediaItem(mediaItem)

        _nombreCancionActual.value = cancionesNombres[indiceActual.value]
        _imagenCancionActual.value = cancionesImagenes[indiceActual.value]

        // Prepara el reproductor y activa el playWhenReady
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }
}

// Funcion auxiliar que devuelve la ruta de un fichero a partir de su ID
@Throws(Resources.NotFoundException::class)
fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
    val res: Resources = context.resources
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId)
    )
}

