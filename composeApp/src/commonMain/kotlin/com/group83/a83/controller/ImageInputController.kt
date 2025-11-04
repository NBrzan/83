package com.group83.a83.controller

import com.group83.a83.AppRepositories
import com.group83.a83.model.ImageInputModel

class ImageInputController(val question: ImageInputModel) {

    private val repository = AppRepositories.statsRepository

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
            val isAnswerCorrect = question.answers.any {
                it.answer.trim().lowercase() == normalized
            }

            isCorrect = isAnswerCorrect
            confirmed = true

            if (isAnswerCorrect) {
                repository.incrementCorrect()
                repository.decrementUnanswered()
            } else {
                repository.incrementIncorrect()
                repository.decrementUnanswered()
            }
        }
    }

    fun reset() {
        input = ""
        confirmed = false
        isCorrect = null
    }
}