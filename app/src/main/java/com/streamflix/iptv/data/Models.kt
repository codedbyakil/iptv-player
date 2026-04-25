package com.streamflix.iptv.data

data class Channel(
    val id: String,
    val name: String,
    val url: String,
    val group: String = "General",
    val logoUrl: String? = null,
    val epgTitle: String? = null,
    val epgDescription: String? = null,
    val isFavorite: Boolean = false
)

data class ChannelGroup(
    val name: String,
    val channels: List<Channel>
)

data class PlaylistInfo(
    val name: String,
    val totalChannels: Int,
    val groups: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)
