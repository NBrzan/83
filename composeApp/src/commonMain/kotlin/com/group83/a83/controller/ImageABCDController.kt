package com.group83.a83.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.group83.a83.AppRepositories
import com.group83.a83.model.ImageABCDModel

class ImageABCDController(val question: ImageABCDModel) {
    private val repository = AppRepositories.statsRepository

    var selectedIndex by mutableStateOf<Int?>(null)
        private set

    var confirmed by mutableStateOf(false)
        private set

    var isCorrect by mutableStateOf<Boolean?>(null)
        private set

    fun select(index: Int) {
        if (!confirmed) {
            selectedIndex = index
        }
    }

    fun confirm() {
        val idx = selectedIndex ?: return
        if (confirmed) return

        val selectedAnswerId = question.answers[idx].id

        val correct = question.correctAnswers.contains(selectedAnswerId.toLong())

        confirmed = true
        isCorrect = correct

        if (correct) {
            repository.incrementCorrect()
            repository.decrementUnanswered()
        } else {
            repository.incrementIncorrect()
            repository.decrementUnanswered()
        }
    }

    fun reset() {
        selectedIndex = null
        confirmed = false
        isCorrect = null
    }
}

