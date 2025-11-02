package com.group83.a83.controller

import com.group83.a83.model.InputModel

class InputController(val question: InputModel) {
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

    fun confirm() {
        if (!confirmed) {
            val normalized = input.trim().lowercase()
            isCorrect = question.answers.any { it.answer.trim().lowercase() == normalized }
            confirmed = true
        }
    }

    fun reset() {
        input = ""
        confirmed = false
        isCorrect = null
    }
}

