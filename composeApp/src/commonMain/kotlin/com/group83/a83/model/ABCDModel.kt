package com.group83.a83.model

data class ABCDModel(
    override val type: GameType = GameType.abcd,
    val id: Int,
    val question: String,
    val answers: List<AnswerModel>,
    val correctAnswers: List<Long>,
) : GameQuestion()
