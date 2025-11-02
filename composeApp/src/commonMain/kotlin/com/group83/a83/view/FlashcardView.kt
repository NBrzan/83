package com.group83.a83.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.controller.FlashcardController
import com.group83.a83.model.FlashcardModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FlashcardView(controller: FlashcardController) {
    var flipped by remember { mutableStateOf(controller.isFront) }

    val rotation by animateFloatAsState(
        targetValue = if (flipped) 0f else 180f,
        animationSpec = tween(durationMillis = 400)
    )

    val isFrontVisible = rotation <= 90f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF6F6F6))
            .clickable {
                controller.flip()
                flipped = controller.isFront
            }
            .border(2.dp, Color.Black, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(isFrontVisible) { front ->
            Text(
                text = if (front) controller.question.front else controller.question.back,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlashcardPreview() {
    val question = FlashcardModel(
        id = 1,
        front = "Dog",
        back = "Perro"
    )
    val controller = FlashcardController(question)

    FlashcardView(controller)
}