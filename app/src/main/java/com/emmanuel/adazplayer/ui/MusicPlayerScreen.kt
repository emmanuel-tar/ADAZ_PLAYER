package com.emmanuel.adazplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.emmanuel.adazplayer.data.Song
import com.emmanuel.adazplayer.data.SongRepository

@Composable
fun MusicPlayerScreen() {
    var currentSong by remember { mutableStateOf<Song?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    val player = remember { ExoPlayer.Builder(LocalContext.current).build() }

    LaunchedEffect(player) {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        })
    }

    DisposableEffect(player) {
        onDispose { player.release() }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("ADAZ Player", style = MaterialTheme.typography.headlineMedium)
        Text("Nigerian Gospel", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

        Spacer(Modifier.height(16.dp))

        // Now Playing
        currentSong?.let { song ->
            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                    // Album Art placeholder
                    Box(modifier = Modifier.size(200.dp)) {
                        Image(Icons.Default.MusicNote, contentDescription = null, modifier = Modifier.size(150.dp))
                    }
                    Text(song.title, style = MaterialTheme.typography.titleLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(song.artist, style = MaterialTheme.typography.bodyMedium)

                    Slider(value = progress, onValueChange = { progress = it }, modifier = Modifier.fillMaxWidth())

                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = { /* Previous */ }) { Icon(Icons.Default.SkipPrevious, null) }
                        IconButton(onClick = {
                            if (isPlaying) player.pause() else player.play()
                        }) {
                            Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, null, modifier = Modifier.size(48.dp))
                        }
                        IconButton(onClick = { /* Next */ }) { Icon(Icons.Default.SkipNext, null) }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Song List
        Text("Playlist", style = MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(SongRepository.gospelSongs) { song ->
                ListItem(
                    headlineContent = { Text(song.title) },
                    supportingContent = { Text(song.artist) },
                    leadingContent = { Icon(Icons.Default.MusicNote, null) },
                    onClick = {
                        currentSong = song
                        val mediaItem = MediaItem.fromUri(song.url)
                        player.setMediaItem(mediaItem)
                        player.prepare()
                        player.play()
                    }
                )
            }
        }
    }
}