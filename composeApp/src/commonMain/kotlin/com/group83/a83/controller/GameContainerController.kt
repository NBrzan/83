package com.group83.a83.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.group83.a83.model.GameQuestion

class GameContainerController {
    private val questions = mutableListOf<GameQuestion>()
    var index by mutableStateOf(0)
        private set

    fun getListLength() = questions.lastIndex

    fun getCurrentQuestion() = questions.getOrNull(index)

    fun next() {
        if (index < questions.lastIndex) index++
    }

    fun previous() {
        if (index > 0) index--
    }

    fun loadQuestions(newQuestions: List<GameQuestion>) {
        questions.clear()
        questions.addAll(newQuestions)
        index = 0
    }
}
