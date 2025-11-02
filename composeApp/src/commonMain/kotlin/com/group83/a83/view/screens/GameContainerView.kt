package com.group83.a83.view.screens

import androidx.compose.foundation.layout.Column
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

@Composable
fun GameContainerView(controller: GameContainerController) {
    val question = controller.getCurrentQuestion()

    Column {
        when (question) {
            is FlashcardModel -> FlashcardView(FlashcardController(question))
            is ABCDModel -> ABCDView(ABCDController(question))
            is ImageABCDModel -> ImageABCDView(ImageABCDController(question))
            is ImageInputModel -> ImageInputView(ImageInputController(question))
            is InputModel -> InputView(InputController(question))
        }

//        NavigationControls(
//            onPrev = { controller.previous() },
//            onNext = { controller.next() }
//        )
    }
}