package com.group83.a83.view.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.group83.a83.controller.ImageABCDController
import com.group83.a83.model.AnswerModel
import com.group83.a83.model.ImageABCDModel
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageABCDView(controller: ImageABCDController) {
    val selected = controller.selectedIndex
    val confirmed = controller.confirmed

    val labels = listOf("A", "B", "C", "D")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFFFF6C4))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = controller.question.question,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A4A4A)
        )

        CoilImage(
            imageModel = { controller.question.imageSrc },
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp)
        )

        controller.question.answers.forEachIndexed { index, answer ->
            val isSelected = selected == index
            val answerId = controller.question.answers[index].id
            val isCorrectAnswer = confirmed && controller.question.correctAnswers.contains(answerId.toLong())
            val isWrongSelected = confirmed && isSelected && !controller.question.correctAnswers.contains(answerId.toLong())

            val bgColor = when {
                isCorrectAnswer -> Color(0xFFB2FF59)
                isWrongSelected -> Color(0xFFFF5252)
                isSelected -> Color(0xFF80D8FF)
                else -> Color.White
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(bgColor)
                    .border(3.dp, Color.Black, RoundedCornerShape(18.dp))
                    .clickable(enabled = !confirmed) {
                        controller.select(index)
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${labels[index]}. ${answer.answer}", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                controller.confirm()
            },
            enabled = selected != null && !confirmed,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Confirm", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview
@Composable
fun ImageABCDPreview() {
    val question = ImageABCDModel(
        id = 1,
        imageSrc = "https://camo.githubusercontent.com/3cae61090608b8cbd681f5825ca5ac76af8d8d3ee12024926d51c5480aef5d6c/68747470733a2f2f796176757a63656c696b65722e6769746875622e696f2f73616d706c652d696d616765732f696d6167652d313032312e6a7067",
        question = "What animal is this?",
        answers = listOf(
            AnswerModel(0, "Cat"),
            AnswerModel(1, "Dog"),
            AnswerModel(2, "Rabbit"),
            AnswerModel(3, "Horse")
        ),
        correctAnswers = listOf(0)
    )
    val controller = ImageABCDController(question)
    ImageABCDView(controller)
}

