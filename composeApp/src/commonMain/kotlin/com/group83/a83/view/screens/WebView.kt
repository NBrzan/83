package com.group83.a83.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
expect fun WebViewContent(url: String, modifier: Modifier = Modifier)

@Composable
fun WebGameView(onBack: () -> Unit) {
    // Use your machine's local network IP
    val gameUrl = "http://192.168.1.103:5173/"

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // WebView
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfffceca7))
        ) {
            WebViewContent(gameUrl, modifier = Modifier.fillMaxSize())
        }
    }
}

