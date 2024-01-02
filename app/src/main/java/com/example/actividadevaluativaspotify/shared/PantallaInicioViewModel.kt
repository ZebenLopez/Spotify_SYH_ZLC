package com.example.actividadevaluativaspotify.shared

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.actividadevaluativaspotify.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PantallaInicioViewModel: ViewModel() {


    // El reproductor de musica, empieza a null
    private val _exoPlayer : MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    // La cancion actual que está sonando
    private val _actual  = MutableStateFlow(R.raw.songone)
    val actual = _actual.asStateFlow()

    // La duración de la canción
    private val _duracion  = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    // El progreso (en segundos) actual de la cancion
    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    fun crearExoPlayer(context: Context){
        /* TODO : Crear el _exoPlayer usando el build(), prepare() y playWhenReady */
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }


    fun hacerSonarMusica(context: Context){
        /* TODO: 1 - Crear un mediaItem con la cancion actual
         *  2 - Establecer dicho mediaItem
         *  3 - Activar el playWhenReady
         */
        val mediaItem = MediaItem.fromUri(obtenerRuta(context,R.raw.songone))
        _exoPlayer.value!!.setMediaItem(mediaItem)

        _exoPlayer.value!!.playWhenReady = true
        // Este listener se mantendrá mientras NO se librere el _exoPlayer
        // Asi que no hace falta crearlo más de una vez.
        _exoPlayer.value!!.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    // El Player está preparado para empezar la reproducción.
                    // Si playWhenReady es true, empezará a sonar la música.

                    _duracion.value = _exoPlayer.value!!.duration.toInt()

                    viewModelScope.launch {
                        /* TODO: Actualizar el progreso usando currentPosition cada segundo */
                        while(isActive){
                            _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                            delay(1000)
                        }

                    }
                }
                else if(playbackState == Player.STATE_BUFFERING){
                    // El Player está cargando el archivo, preparando la reproducción.
                    // No está listo, pero está en ello.
                }
                else if(playbackState == Player.STATE_ENDED){
                    // El Player ha terminado de reproducir el archivo.
                    CambiarCancion(context)

                }
                else if(playbackState == Player.STATE_IDLE){
                    // El player se ha creado, pero no se ha lanzado la operación prepared.
                }

            }
        }
        )


    }

    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun PausarOSeguirMusica() {
        /* TODO: Si el reproductor esta sonando, lo pauso. Si no, lo reproduzco */
        if(!_exoPlayer.value!!.isPlaying ){
            _exoPlayer.value!!.play()
        }else{
            _exoPlayer.value!!.pause()
        }
    }

    fun CambiarCancion(context: Context) {

        /* TODO: 1 - Cambiar la cancion actual y parar el mediaPlayer
         *  2 - Limpiar al _exoPlayer de los mediaItems que tenga
         *  3 - Crear mediaItem con la cancion actual
         *  4 - Establecer dicho mediaItem
         *  5 - Preparar el reproductor y activar el playWhenReady
        */
        if(_actual.value == R.raw.songone){
            _actual.value = R.raw.songtwo
        }else{
            _actual.value = R.raw.songone
        }

        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        val mediaItem = MediaItem.fromUri(obtenerRuta(context,_actual.value ))
        _exoPlayer.value!!.setMediaItem(mediaItem)

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