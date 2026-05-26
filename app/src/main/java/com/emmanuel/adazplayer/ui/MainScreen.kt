package com.emmanuel.adazplayer.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

data class Song(val title: String, val artist: String, val url: String)

@Composable
fun MainScreen(player: ExoPlayer) {
    val songs = listOf(
        Song("Great Are You Lord", "Sinach", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
        Song("Excess Love", "Mercy Chinwo", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"),
        Song("Onise Iyanu", "Nathaniel Bassey", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3")
    )
    var currentSong by remember { mutableStateOf(songs[0]) }
    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ADAZ_PLAYER", style = MaterialTheme.typography.headlineLarge)
        Text("Nigerian Gospel Music", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(24.dp))

        songs.forEach { song ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Button(onClick = {
                    val mediaItem = MediaItem.fromUri(song.url)
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                    currentSong = song
                    isPlaying = true
                }, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(song.title)
                        Text(song.artist, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Now Playing", style = MaterialTheme.typography.titleMedium)
                Text(currentSong.title)
                Text(currentSong.artist)
                Button(onClick = {
                    if (player.isPlaying) {
                        player.pause()
                        isPlaying = false
                    } else {
                        player.play()
                        isPlaying = true
                    }
                }) {
                    Text(if (isPlaying) "Pause" else "Play")
                }
            }
        }
    }
}