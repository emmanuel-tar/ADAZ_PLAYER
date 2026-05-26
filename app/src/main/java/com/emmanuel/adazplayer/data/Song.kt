package com.emmanuel.adazplayer.data

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val url: String, // For demo, use public domain or placeholder URLs
    val albumArt: String? = null
)

object SongRepository {
    val gospelSongs = listOf(
        Song(1, "Excessive Praise", "Mercy Chinwo", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
        Song(2, "Okaaka", "Sinach", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"),
        Song(3, "Way Maker", "Sinach", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"),
        Song(4, "Imela", "Nathaniel Bassey", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"),
        Song(5, "Elohim", "Nathaniel Bassey", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3")
    )
}