package com.group83.a83.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.controller.ImageInputController
import com.group83.a83.model.AnswerModel
import com.group83.a83.model.ImageInputModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageInputView(controller: ImageInputController, imagePainter: Painter?) {
    var input by remember { mutableStateOf(controller.input) }
    var confirmed by remember { mutableStateOf(controller.confirmed) }
    var isCorrect by remember { mutableStateOf(controller.isCorrect) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imagePainter != null) {
            Image(
                painter = imagePainter,
                contentDescription = "Guess the image",
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
        OutlinedTextField(
            value = input,
            onValueChange = {
                controller.onInputChange(it)
                input = controller.input
            },
            label = { Text("Your answer") },
            enabled = !confirmed,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                controller.confirm(controller.question.answers)
                confirmed = controller.confirmed
                isCorrect = controller.isCorrect
            },
            enabled = input.isNotBlank() && !confirmed
        ) {
            Text("Confirm")
        }
        if (confirmed) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isCorrect == true) "Correct!" else "Incorrect.",
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun ImageInputPreview() {
    val question = ImageInputModel(
        id = 1,
        imageSrc = "",
        question = "What animal is shown in the image?",
        answers = listOf(
            AnswerModel(0, "cat"),
            AnswerModel(1, "dog")
        )
    )
    val controller = ImageInputController(question)
    ImageInputView(controller, imagePainter = null)
}

