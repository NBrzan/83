package com.group83.a83.model

data class InputModel(
    override val type: GameType = GameType.input,
    val id: Int,
    val question: String,
    val answers: List<AnswerModel>,
) : GameQuestion()
