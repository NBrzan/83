package com.group83.a83.controller

import com.group83.a83.model.GameQuestion

class GameContainerController {
    private val questions = listOf<GameQuestion>()
    private var index = 0

    fun getCurrentQuestion() = questions[index]

    fun next() {
        if (index < questions.lastIndex) index++
    }

    fun previous() {
        if (index > 0) index--
    }
}
