package com.group83.a83.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CreateQuestionView(
    text: String,
    imagePath: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(modifier = modifier
        .padding(8.dp)
        .clickable { onClick() }
    ) {
        Text(text, modifier = Modifier.padding(4.dp))
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(100.dp)
                .defaultMinSize(120.dp, 120.dp)
                .border(width = 1.dp, color = Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(imagePath, modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}