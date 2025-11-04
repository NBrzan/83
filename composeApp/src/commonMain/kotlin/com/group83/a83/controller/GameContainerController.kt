package com.group83.a83.controller

import com.group83.a83.model.GameQuestion

class GameContainerController {
    private var questions = listOf<GameQuestion>()
    private var index = 0

    fun getListLength() = questions.lastIndex

    fun getCurrentQuestion() = questions.getOrNull(index)

    fun setQuestions(questions: List<GameQuestion>) {
        this.questions = questions
    }


    fun next() {
        if (index < questions.lastIndex) index++
    }

    fun previous() {
        if (index > 0) index--
    }
}
