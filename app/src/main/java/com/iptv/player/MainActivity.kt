package com.iptv.player

import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val gridLayout = GridLayout(this).apply {
            columnCount = 3
            setPadding(32)
        }
        
        val channels = listOf("Sports 1", "News HD", "Movies", "Music TV", "Discovery", "History")
        
        channels.forEach { channel ->
            val button = TextView(this).apply {
                text = channel
                textSize = 18f
                setPadding(32, 24, 32, 24)
                setBackgroundColor(0xFF333333.toInt())
                setTextColor(0xFFFFFFFF.toInt())
                gravity = android.view.Gravity.CENTER
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(16, 16, 16, 16)
                }
                setOnClickListener {
                    android.widget.Toast.makeText(context, "Playing $channel", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
            gridLayout.addView(button)
        }
        
        setContentView(gridLayout)
    }
}
