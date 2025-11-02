package com.group83.a83.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.controller.InputController
import com.group83.a83.model.AnswerModel
import com.group83.a83.model.InputModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InputView(controller: InputController) {
    var input by remember { mutableStateOf(controller.input) }
    var confirmed by remember { mutableStateOf(controller.confirmed) }
    var isCorrect by remember { mutableStateOf(controller.isCorrect) }

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
                controller.confirm()
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
fun InputPreview() {
    val question = InputModel(
        id = 1,
        question = "Name a primary color:",
        answers = listOf(
            AnswerModel(0, "red"),
            AnswerModel(1, "blue"),
            AnswerModel(2, "yellow")
        )
    )
    val controller = InputController(question)
    InputView(controller)
}

