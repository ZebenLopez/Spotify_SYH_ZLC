@file:OptIn(UnstableApi::class)

package com.example.actividadevaluativaspotify.shared

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import com.example.actividadevaluativaspotify.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ScaffoldViewModel : ViewModel() {

    private val _canciones = MutableStateFlow(initializarLista())
    val canciones = _canciones.asStateFlow()

    private fun initializarLista(): List<Pair<Pair<Pair<Int, String>, String>, Int>> {
        return listOf(
            R.raw.acelerao_luck to "Acelerao" to "Luck" to R.drawable.acelerao,
            R.raw.birrahumoyreflexiones_hardgz to "Birra, Humo y Reflexiones" to "Hard GZ" to R.drawable.kaosnomada,
            R.raw.cerocumplidos_luck to "Cero Cumplidos" to "Luck" to R.drawable._cumplidos,
            R.raw.ojodehalcon_hoke to "Ojo de Halcón" to "Hoke" to R.drawable.bbo,
            R.raw.malviviendo_sfdk to "Malviviendo" to "SFDK" to R.drawable.malviviendo,
            R.raw.sharif_apoloydafne to "Apolo y Dafne" to "Sharif" to R.drawable.apoloydafne,
            R.raw.songtwo to "Perro Salchicha" to "Falso Bad Bunny" to R.drawable.perrosalchicha,
            R.raw.songone to "Bob Esponja" to "Nickelodeon" to R.drawable.bobesponja
        )
    }

    var cancionesAleatorias = _canciones.value.shuffled()

    val _mostrarBarraInferior = MutableStateFlow(true)

    val mostrarBarraInferior = _mostrarBarraInferior.asStateFlow()

    private val _nombreAlbumActual = MutableStateFlow("")
    val nombreAlbumActual = _nombreAlbumActual.asStateFlow()

    private val _nombreCancionActual = MutableStateFlow("")
    val nombreCancionActual = _nombreCancionActual.asStateFlow()

    private val _imagenCancionActual = MutableStateFlow(0)
    val imagenCancionActual = _imagenCancionActual.asStateFlow()

    private val _indiceActual = MutableStateFlow(0)
    val indiceActual = _indiceActual.asStateFlow()


    // El reproductor de musica, empieza a null
    val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    // La cancion actual que está sonando
    private val _actual = MutableStateFlow(_canciones.value[0])
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

    fun cargarCanciones(
        context: Context, canciones: List<Pair<Pair<Pair<Int, String>, String>, Int>>
    ) {
        _canciones.value = canciones

        if (indiceActual.value >= 0 && indiceActual.value < _canciones.value.size) {
            _nombreAlbumActual.value = _canciones.value[indiceActual.value].first.second
            _nombreCancionActual.value = _canciones.value[indiceActual.value].first.first.second
            _imagenCancionActual.value = (_canciones.value[indiceActual.value]).second
            _estaReproduciendo.value = true

        } else {
            _nombreAlbumActual.value = _canciones.value[0].first.second
            _nombreCancionActual.value = _canciones.value[0].first.first.second
            _imagenCancionActual.value = (_canciones.value[0]).second
            _estaReproduciendo.value = true
        }


        if (_exoPlayer.value != null) {
            _exoPlayer.value!!.stop()
            _exoPlayer.value!!.clearMediaItems()

            val mediaItem =
                MediaItem.fromUri(obtenerRuta(context, _canciones.value[0].first.first.first))
            _exoPlayer.value!!.setMediaItem(mediaItem)

            _exoPlayer.value!!.prepare()
            _exoPlayer.value!!.playWhenReady = true
        } else {
            _exoPlayer.value!!.stop()
            _exoPlayer.value!!.clearMediaItems()


            crearExoPlayer(context)

            val mediaItem =
                MediaItem.fromUri(obtenerRuta(context, _canciones.value[0].first.first.first))
            _exoPlayer.value!!.setMediaItem(mediaItem)

            _exoPlayer.value!!.prepare()
            _exoPlayer.value!!.playWhenReady = true

            println("No hay reproductor")
        }


    }

    fun playCancionSuelta(cancion: Pair<Pair<Pair<Int, String>, String>, Int>, context: Context) {
        _nombreAlbumActual.value = cancion.first.second
        _nombreCancionActual.value = cancion.first.first.second
        _imagenCancionActual.value = cancion.second
        _estaReproduciendo.value = true

        val exoPlayerValue = _exoPlayer.value

        if (exoPlayerValue != null) {

            _exoPlayer.value!!.stop()
            _exoPlayer.value!!.clearMediaItems()

            val mediaItem = MediaItem.fromUri(obtenerRuta(context, cancion.first.first.first))
            _exoPlayer.value!!.setMediaItem(mediaItem)

            _exoPlayer.value!!.prepare()
            _exoPlayer.value!!.playWhenReady = true
        } else {
            _exoPlayer.value = SimpleExoPlayer.Builder(context).build()

            val newExoPlayer = _exoPlayer.value
            newExoPlayer?.stop()
            newExoPlayer?.clearMediaItems()

            val mediaItem = MediaItem.fromUri(obtenerRuta(context, cancion.first.first.first))
            newExoPlayer?.setMediaItem(mediaItem)

            newExoPlayer?.prepare()
            newExoPlayer?.playWhenReady = true
        }
    }

    fun hacerSonarMusica(context: Context) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()
        _exoPlayer.value!!.prepare()

        _nombreAlbumActual.value = _canciones.value[indiceActual.value].first.second
        _nombreCancionActual.value = _canciones.value[indiceActual.value].first.first.second
        _imagenCancionActual.value = (_canciones.value[indiceActual.value]).second

        val mediaItem = MediaItem.fromUri(
            obtenerRuta(
                context,
                _canciones.value[indiceActual.value].first.first.first
            )
        )
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

    fun PausarOSeguirMusica() {/* TODO: Si el reproductor esta sonando, lo pauso. Si no, lo reproduzco */
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
        } else if (indiceActual.value == _canciones.value.size - 1) {
            _indiceActual.value = 0
        } else {
            _indiceActual.value = (_indiceActual.value + 1) % _canciones.value.size
        }

        if (!modoAleatorio.value) {
            _nombreAlbumActual.value = _canciones.value[indiceActual.value].first.second
            _nombreCancionActual.value = _canciones.value[indiceActual.value].first.first.second
            _imagenCancionActual.value = _canciones.value[indiceActual.value].second

            _exoPlayer.value!!.stop()
            _exoPlayer.value!!.clearMediaItems()

            val mediaItem = MediaItem.fromUri(
                obtenerRuta(
                    context, _canciones.value[indiceActual.value].first.first.first
                )
            )
            _exoPlayer.value!!.setMediaItem(mediaItem)

            _exoPlayer.value!!.prepare()
            _exoPlayer.value!!.playWhenReady = true
        }

    }

    fun AnteriorCancion(context: Context) {
        _estaReproduciendo.value = true

        if (modoAleatorio.value) {
            ReproducirCancionAleatoria(context)
        } else if (modoRepetir.value) {
            // No avanza al siguiente ítem si está en modo repetir y es la última canción
        } else if (indiceActual.value == 0) {
            _indiceActual.value = _canciones.value.size - 1
        } else {
            _indiceActual.value = (_indiceActual.value - 1) % _canciones.value.size
        }

        _nombreAlbumActual.value = _canciones.value[indiceActual.value].first.second
        _nombreCancionActual.value = _canciones.value[indiceActual.value].first.first.second
        _imagenCancionActual.value = _canciones.value[indiceActual.value].second

        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        val mediaItem =
            MediaItem.fromUri(
                obtenerRuta(
                    context,
                    _canciones.value[indiceActual.value].first.first.first
                )
            )
        _exoPlayer.value!!.setMediaItem(mediaItem)

        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true

    }

    fun CambiarAletorio(context: Context) {
        _modoAleatorio.value = !_modoAleatorio.value
        _modoRepetir.value = false
        cancionesAleatorias = cancionesAleatorias.shuffled()
    }

    fun CambiarRepetir(context: Context) {
        _modoRepetir.value = !_modoRepetir.value
        _modoAleatorio.value = false
    }

    fun ReproducirCancionAleatoria(context: Context) {

        cancionesAleatorias = _canciones.value.shuffled()

        if (modoRepetir.value) {

        } else if (indiceActual.value == cancionesAleatorias.size - 1) {
            _indiceActual.value = 0
        } else {
            _indiceActual.value = (_indiceActual.value + 1) % cancionesAleatorias.size
        }

        _nombreAlbumActual.value = cancionesAleatorias[_indiceActual.value].first.second
        _nombreCancionActual.value = cancionesAleatorias[_indiceActual.value].first.first.second
        _imagenCancionActual.value = cancionesAleatorias[_indiceActual.value].second

        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        val mediaItem = MediaItem.fromUri(
            obtenerRuta(
                context, cancionesAleatorias[_indiceActual.value].first.first.first
            )
        )
        _exoPlayer.value!!.setMediaItem(mediaItem)

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
        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(resId) + '/' + res.getResourceTypeName(
            resId
        ) + '/' + res.getResourceEntryName(resId)
    )
}