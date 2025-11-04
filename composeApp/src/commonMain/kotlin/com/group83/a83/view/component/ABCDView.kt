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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.controller.ABCDController
import com.group83.a83.model.ABCDModel
import com.group83.a83.model.AnswerModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ABCDView(controller: ABCDController) {
    var selected by remember { mutableStateOf(controller.selectedIndex) }
    var confirmed by remember { mutableStateOf(controller.confirmed) }
    var isCorrect by remember { mutableStateOf(controller.isCorrect) }

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
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A4A4A),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        controller.question.answers.forEachIndexed { index, answer ->
            val isSelected = selected == index
            val bgColor = when {
                confirmed && controller.question.correctAnswers.contains(index.toLong()) -> Color(0xFFB2FF59)
                confirmed && isSelected && isCorrect == false -> Color(0xFFFF5252)
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
                        selected = controller.selectedIndex
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
                confirmed = controller.confirmed
                isCorrect = controller.isCorrect
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
fun ABCDPreview() {
    val question = ABCDModel(
        id = 1,
        question = "What is the capital of France?",
        answers = listOf(
            AnswerModel(0, "Berlin"),
            AnswerModel(1, "Madrid"),
            AnswerModel(2, "Paris"),
            AnswerModel(3, "Rome")
        ),
        correctAnswers = listOf(2)
    )
    val controller = ABCDController(question)
    ABCDView(controller)
}

