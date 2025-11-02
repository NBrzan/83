package com.group83.a83.view.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
        animationSpec = tween(durationMillis = 500)
    )

    val isFrontVisible = rotation <= 90f

    val cardColor = if (flipped) Color(0xFFFFF2A7) else Color(0xFFA7E6FF)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.4f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density
            }
            .clip(RoundedCornerShape(30.dp))
            .background(cardColor)
            .clickable {
                controller.flip()
                flipped = controller.isFront
            }
            .border(
                width = 4.dp,
                color = Color(0xFF444444),
                shape = RoundedCornerShape(30.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isFrontVisible,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "FlipCardContent"
        ) { front ->
            if (front) {
                FlashcardText(controller.question.front)
            } else {
                FlashcardText(
                    text = controller.question.back,
                    flipped = true
                )
            }
        }
    }
}

@Composable
private fun FlashcardText(text: String, flipped: Boolean = false) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(24.dp)
            .graphicsLayer { if (flipped) rotationY = 180f },
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(0xFF333333)
    )
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