package com.group83.a83.view.screens

import androidx.compose.foundation.layout.*
import com.group83.a83.controller.GameContainerController
import androidx.compose.runtime.Composable
import com.group83.a83.controller.ABCDController
import com.group83.a83.controller.FlashcardController
import com.group83.a83.controller.ImageABCDController
import com.group83.a83.controller.ImageInputController
import com.group83.a83.controller.InputController
import com.group83.a83.model.ABCDModel
import com.group83.a83.model.FlashcardModel
import com.group83.a83.model.ImageABCDModel
import com.group83.a83.model.ImageInputModel
import com.group83.a83.model.InputModel
import com.group83.a83.view.component.ABCDView
import com.group83.a83.view.component.FlashcardView
import com.group83.a83.view.component.ImageABCDView
import com.group83.a83.view.component.ImageInputView
import com.group83.a83.view.component.InputView
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GameContainerView(controller: GameContainerController) {
    val question = controller.getCurrentQuestion()
    if (question == null) {
        Text("No questions available.")
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            when (question) {
                is FlashcardModel -> FlashcardView(FlashcardController(question))
                is ABCDModel -> ABCDView(ABCDController(question))
                is ImageABCDModel -> ImageABCDView(ImageABCDController(question))
                is ImageInputModel -> ImageInputView(ImageInputController(question))
                is InputModel -> InputView(InputController(question))
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { controller.previous() }, enabled = controller.index > 0) {
                Text("\u25C0", fontSize = 24.sp)
            }
            IconButton(onClick = { controller.next() }, enabled = controller.index < controller.getListLength()) {
                Text("\u25B6", fontSize = 24.sp)
            }
        }
    }
}

@Preview
@Composable
fun GameContainerPreview() {
    val questions = listOf(
        FlashcardModel(
            id = 1,
            front = "What is the capital of France?",
            back = "Paris"
        ),
        FlashcardModel(
            id = 2,
            front = "What is 2 + 2?",
            back = "4"
        )
    )

    val controller = GameContainerController()
    controller.setQuestions(questions)
    GameContainerView(controller)
}