package com.group83.a83.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.group83.a83.model.GameQuestion

class GameContainerController(private var questions: List<GameQuestion>) {
    var index by mutableStateOf(0)
        private set

    fun getCurrentQuestion(): GameQuestion? =
        questions.getOrNull(index)

    fun getListLength() = questions.lastIndex

    fun setQuestions(questions: List<GameQuestion>) {
        this.questions = questions
    }


    fun next() {
        if (index < questions.lastIndex) {
            index++
        }
    }

    fun previous() {
        if (index > 0) {
            index--
        }
    }
}
