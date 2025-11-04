package com.group83.a83.controller

import com.group83.a83.AppRepositories
import com.group83.a83.model.ImageABCDModel

class ImageABCDController(val question: ImageABCDModel) {
    private val repository = AppRepositories.statsRepository

    var selectedIndex: Int? = null
        private set

    var confirmed: Boolean = false
        private set

    var isCorrect: Boolean? = null
        private set

    fun select(index: Int) {
        if (!confirmed) {
            selectedIndex = index
        }
    }

    fun confirm() {
        if (selectedIndex != null && !confirmed) {
            confirmed = true
            isCorrect = question.correctAnswers.contains(selectedIndex!!.toLong())

            if (isCorrect == true) {
                repository.incrementCorrect()
                repository.decrementUnanswered()
            } else {
                repository.incrementIncorrect()
                repository.decrementUnanswered()
            }
        }
    }

    fun reset() {
        selectedIndex = null
        confirmed = false
        isCorrect = null
    }
}

