package com.group83.a83.view.component

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

    val labels = listOf("A", "B", "C", "D")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

