package com.streamflix.iptv.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

object M3uParser {
    
    suspend fun parseM3u(content: String): List<Channel> = withContext(Dispatchers.Default) {
        val channels = mutableListOf<Channel>()
        var name = ""
        var group = "General"
        var logo: String? = null
        var epgTitle: String? = null
        var epgDescription: String? = null
        
        val lines = content.lines()
        for (i in lines.indices) {
            val line = lines[i].trim()
            
            when {
                line.startsWith("#EXTINF:") -> {
                    // Extract channel name (after last comma)
                    name = line.substringAfterLast(",").trim().takeIf { it.isNotEmpty() } ?: "Unknown Channel"
                    
                    // Extract group-title
                    group = Regex("group-title=\"([^\"]+)\"").find(line)?.groupValues?.get(1)?.trim() ?: "General"
                    if (group.isEmpty()) group = "General"
                    
                    // Extract tvg-logo
                    logo = Regex("tvg-logo=\"([^\"]+)\"").find(line)?.groupValues?.get(1)?.trim()
                    
                    // Extract tvg-name for EPG
                    epgTitle = Regex("tvg-name=\"([^\"]+)\"").find(line)?.groupValues?.get(1)?.trim()
                    
                    // Extract tvg-description if available
                    epgDescription = Regex("tvg-description=\"([^\"]+)\"").find(line)?.groupValues?.get(1)?.trim()
                }
                
                line.startsWith("#EXTGRP:") -> {
                    // Alternative group extraction
                    val grp = line.substringAfter(":").trim()
                    if (grp.isNotEmpty() && group == "General") {
                        group = grp
                    }
                }
                
                line.startsWith("http", ignoreCase = true) && name.isNotEmpty() -> {
                    channels.add(
                        Channel(
                            id = java.util.UUID.randomUUID().toString(),
                            name = name,
                            url = line,
                            group = group,
                            logoUrl = logo,
                            epgTitle = epgTitle,
                            epgDescription = epgDescription
                        )
                    )
                    name = ""
                    logo = null
                    epgTitle = null
                    epgDescription = null
                }
            }
        }
        
        channels.filter { it.url.startsWith("http", ignoreCase = true) }
    }
    
    suspend fun fetchAndParse(url: String): Result<List<Channel>> = withContext(Dispatchers.IO) {
        try {
            val content = URL(url).readText()
            Result.success(parseM3u(content))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun parseFromFile(file: File): Result<List<Channel>> = withContext(Dispatchers.IO) {
        try {
            val content = file.readText()
            Result.success(parseM3u(content))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
