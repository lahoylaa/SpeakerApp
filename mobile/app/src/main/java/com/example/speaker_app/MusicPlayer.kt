package com.example.speaker_app

import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun MusicPlayer(url: String){
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null)}
    var isPlaying by remember { mutableStateOf(false)}

    DisposableEffect(url){
        mediaPlayer = MediaPlayer().apply{
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() ; isPlaying = true }
            setOnCompletionListener { isPlaying = false }
        }

        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }


    // Maybe add controls here instead of main screen?

}