package com.tamilflix.iptv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import java.net.URL

data class Channel(val name: String, val url: String, val group: String, val logoUrl: String?)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
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
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val m3uUrl = "https://raw.githubusercontent.com/codedbyakil/Tamil-TV/main/local.m3u"
                val content = URL(m3uUrl).readText()
                val channelList = mutableListOf<Channel>()
                var currentName = ""
                var currentGroup = ""
                var currentLogo: String? = null
                
                for (line in content.lines()) {
                    when {
                        line.startsWith("#EXTINF:") -> {
                            currentName = line.substringAfterLast(",").trim()
                            currentGroup = Regex("group-title=\"([^\"]+)\"").find(line)?.groupValues?.get(1) ?: "Tamil Channels"
                            currentLogo = Regex("tvg-logo=\"([^\"]+)\"").find(line)?.groupValues?.get(1)
                        }
                        line.startsWith("http") && currentName.isNotEmpty() -> {
                            channelList.add(Channel(currentName, line.trim(), currentGroup, currentLogo))
                            currentName = ""
                        }
                    }
                }
                channels = channelList.filter { it.url.startsWith("http") }.take(50)
                isLoading = false
            } catch (e: Exception) {
                isLoading = false
                Toast.makeText(context, "Error loading channels: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color(0xFFE50914))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Loading Tamil Channels...", color = Color.White, fontSize = 16.sp)
            }
        }
    } else {
        val grouped = channels.groupBy { it.group }
        
        Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(24.dp)) {
            Text(
                "TamilFlix TV", 
                fontSize = 32.sp, 
                fontWeight = FontWeight.Bold, 
                color = Color(0xFFE50914),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                grouped.forEach { (category, categoryChannels) ->
                    item {
                        Text(
                            category, 
                            fontSize = 20.sp, 
                            fontWeight = FontWeight.SemiBold, 
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(categoryChannels) { channel ->
                                ChannelCard(channel = channel, context = context)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChannelCard(channel: Channel, context: android.content.Context) {
    var isFocused by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .width(200.dp)
            .clickable { 
                Toast.makeText(context, "Playing: ${channel.name}\n${channel.url.take(50)}...", Toast.LENGTH_LONG).show()
            }
            .clip(RoundedCornerShape(12.dp))
            .background(if (isFocused) Color(0xFFE50914) else Color(0xFF1A1A1A))
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f/9f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                channel.name.take(2).uppercase(), 
                fontSize = 28.sp, 
                fontWeight = FontWeight.Bold, 
                color = Color(0xFFE50914)
            )
        }
        Text(
            channel.name, 
            color = Color.White, 
            fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
            maxLines = 2, 
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            channel.group, 
            fontSize = 12.sp, 
            color = Color.Gray, 
            maxLines = 1
        )
    }
}

@Composable
fun darkColorScheme() = androidx.compose.material3.darkColorScheme(
    primary = Color(0xFFE50914),
    background = Color.Black,
    surface = Color(0xFF1A1A1A)
)
