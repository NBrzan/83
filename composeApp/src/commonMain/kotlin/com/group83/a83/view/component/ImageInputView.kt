package com.group83.a83.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.controller.ImageInputController
import com.group83.a83.model.AnswerModel
import com.group83.a83.model.ImageInputModel
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageInputView(controller: ImageInputController) {
    var input by remember { mutableStateOf(controller.input) }
    var confirmed by remember { mutableStateOf(controller.confirmed) }
    var isCorrect by remember { mutableStateOf(controller.isCorrect) }

    val correctAnswers = controller.question.answers.map { it.answer.trim().lowercase() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF7D4), RoundedCornerShape(24.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = controller.question.question,
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color(0xFF3E3E3E)
        )

        CoilImage(
            imageModel = { controller.question.imageSrc },
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp),
        )

        OutlinedTextField(
            value = input,
            onValueChange = {
                controller.onInputChange(it)
                input = it
            },
            enabled = !confirmed,
            textStyle = LocalTextStyle.current.copy(fontSize = 22.sp),
            shape = RoundedCornerShape(16.dp),
            label = {
                Text("Type your answer", color = Color(0xFF6A6A6A))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                controller.confirm()
                confirmed = true
                val normalizedInput = input.trim().lowercase()
                isCorrect = correctAnswers.contains(normalizedInput)
            },
            enabled = input.isNotBlank() && !confirmed,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .shadow(6.dp, RoundedCornerShape(50))
        ) {
            Text("Check", fontSize = 20.sp)
        }

        if (confirmed) {
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isCorrect == true) Color(0xFFABE47F) else Color(0xFFFF8A80))
                    .padding(vertical = 12.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = if (isCorrect == true) "✅ Great Job!" else "❌ Try Again!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
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
    ImageInputView(controller)
}

