package com.group83.a83.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.controller.ImageABCDController
import com.group83.a83.model.ImageABCDModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageABCDView(controller: ImageABCDController, imagePainter: Painter?) {
    var selected by remember { mutableStateOf(controller.selectedIndex) }
    var confirmed by remember { mutableStateOf(controller.confirmed) }
    val labels = listOf("A", "B", "C", "D")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imagePainter != null) {
            Image(
                painter = imagePainter,
                contentDescription = "Question image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("[Image here]", fontSize = 16.sp)
            }
        }
        Text(
            text = controller.question.question,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        controller.question.answers.forEachIndexed { index, answer ->
            val isSelected = selected == index
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(
                        if (isSelected) Color(0xFFB3E5FC) else Color(0xFFF6F6F6),
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        2.dp,
                        if (isSelected) Color.Blue else Color.Black,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable(enabled = !confirmed) {
                        controller.select(index)
                        selected = controller.selectedIndex
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = "${labels[index]}. ${answer.answer}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                controller.confirm()
                confirmed = controller.confirmed
            },
            enabled = selected != null && !confirmed
        ) {
            Text("Confirm")
        }
    }
}

@Preview
@Composable
fun ImageABCDPreview() {
    val question = ImageABCDModel(
        id = 1,
        imageSrc = "",
        question = "What animal is this?",
        answers = listOf(
            com.group83.a83.model.AnswerModel(0, "Cat"),
            com.group83.a83.model.AnswerModel(1, "Dog"),
            com.group83.a83.model.AnswerModel(2, "Rabbit"),
            com.group83.a83.model.AnswerModel(3, "Horse")
        ),
        correctAnswers = listOf(0)
    )
    val controller = ImageABCDController(question)
    ImageABCDView(controller, imagePainter = null)
}

