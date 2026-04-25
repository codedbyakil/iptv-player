package com.streamflix.iptv.player

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class StreamPlayer(private val player: ExoPlayer) {
    
    fun prepare(url: String) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        player.setMediaItem(mediaItem)
        player.prepare()
    }
    
    fun play() {
        player.play()
    }
    
    fun pause() {
        player.pause()
    }
    
    fun stop() {
        player.stop()
    }
    
    fun release() {
        player.release()
    }
    
    fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }
    
    fun getCurrentPosition(): Long = player.currentPosition
    
    fun isPlaying(): Boolean = player.isPlaying
    
    fun setVolume(volume: Float) {
        player.volume = volume.coerceIn(0f, 1f)
    }
}
