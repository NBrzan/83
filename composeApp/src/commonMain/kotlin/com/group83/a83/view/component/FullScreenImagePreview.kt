package com.group83.a83.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun FullScreenImagePreview(imageSrc: String, onClose: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Box(modifier = Modifier.fillMaxSize()) {
            CoilImage(
                imageModel = { imageSrc },
                modifier = Modifier
                    .fillMaxSize()
            )
            Button(onClick = onClose, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)) {
                Text("Close")
            }
        }
    }
}
