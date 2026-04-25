package com.tamilflix.iptv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tamilflix.iptv.data.M3uParser
import com.tamilflix.iptv.data.models.Channel
import com.tamilflix.iptv.ui.theme.TamilFlixTvTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TamilFlixTvTheme {
                TVApp()
            }
        }
    }
}

@Composable
fun TVApp() {
    var channels by remember { mutableStateOf<List<Channel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        scope.launch {
            channels = M3uParser.fetchChannels()
            isLoading = false
        }
    }
    
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFE50914))
            Text("Loading Channels...", color = Color.White, modifier = Modifier.padding(top = 80.dp))
        }
    } else {
        val grouped = channels.groupBy { it.group.ifEmpty { "Tamil Channels" } }
        
        Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(32.dp)) {
            Text("TamilFlix TV", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE50914), modifier = Modifier.padding(bottom = 24.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                grouped.forEach { (category, categoryChannels) ->
                    item {
                        Text(category, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
                    }
                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(categoryChannels) { channel ->
                                ChannelCard(channel = channel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChannelCard(channel: Channel) {
    var isFocused by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .width(200.dp)
            .focusable()
            .onFocusChanged { isFocused = it.isFocused }
            .clip(RoundedCornerShape(12.dp))
            .background(if (isFocused) Color(0xFFE50914) else Color(0xFF1A1A1A))
            .clickable { 
                // Play channel - show URL for now
                android.widget.Toast.makeText(
                    androidx.compose.ui.platform.LocalContext.current,
                    "Playing: ${channel.name}",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
            .padding(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(16f/9f).clip(RoundedCornerShape(8.dp)).background(Color(0xFF2A2A2A))) {
            if (channel.logoUrl != null && channel.logoUrl.startsWith("http")) {
                AsyncImage(model = channel.logoUrl, contentDescription = channel.name, modifier = Modifier.fillMaxSize())
            } else {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(channel.name.take(2).uppercase(), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE50914))
                }
            }
        }
        Text(channel.name, color = Color.White, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 8.dp))
        Text(channel.group, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
    }
}
