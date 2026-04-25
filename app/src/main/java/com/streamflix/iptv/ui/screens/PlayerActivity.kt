package com.streamflix.iptv.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import java.net.URI

class PlayerActivity : ComponentActivity() {
    
    private var player: ExoPlayer? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val videoUrl = intent.data?.toString() ?: run {
            finish()
            return
        }
        val channelName = intent.getStringExtra("channel_name") ?: "Unknown Channel"
        val channelLogo = intent.getStringExtra("channel_logo")
        
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme()
            ) {
                VideoPlayerScreen(
                    videoUrl = videoUrl,
                    channelName = channelName,
                    channelLogo = channelLogo,
                    onBackPress = { finish() },
                    getPlayer = { player }
                )
            }
        }
    }
    
    override fun onStart() {
        super.onStart()
        if (player == null) {
            player = ExoPlayer.Builder(this).build()
        }
    }
    
    override fun onStop() {
        super.onStop()
        player?.pause()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    channelName: String,
    channelLogo: String?,
    onBackPress: () -> Unit,
    getPlayer: () -> Player?
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    
    LaunchedEffect(videoUrl) {
        val player = getPlayer()
        player?.let {
            val mediaItem = MediaItem.fromUri(android.net.Uri.parse(videoUrl))
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
            isPlaying = true
            isLoading = false
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Video Player View
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    useController = true
                    showBuffering = PlayerView.SHOW_BUFFERING_ALWAYS
                    player = getPlayer()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        
        // Top Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        channelName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    if (isLoading) {
                        Text(
                            "Loading...",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackPress) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.5f),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White
            ),
            modifier = Modifier.align(Alignment.TopCenter)
        )
        
        // Loading indicator
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        }
    }
}
