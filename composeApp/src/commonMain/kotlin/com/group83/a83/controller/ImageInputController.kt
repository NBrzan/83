package com.group83.a83.controller

import com.group83.a83.model.AnswerModel
import com.group83.a83.model.ImageInputModel

class ImageInputController(val question: ImageInputModel) {
    var input: String = ""
        private set
    var confirmed: Boolean = false
        private set
    var isCorrect: Boolean? = null
        private set

    fun onInputChange(newInput: String) {
        if (!confirmed) {
            input = newInput
        }
    }

    fun confirm(correctAnswers: List<AnswerModel>) {
        if (!confirmed) {
            val normalized = input.trim().lowercase()
            isCorrect = correctAnswers.any { it.answer.trim().lowercase() == normalized }
            confirmed = true
        }
    }

    fun reset() {
        input = ""
        confirmed = false
        isCorrect = null
    }
}

