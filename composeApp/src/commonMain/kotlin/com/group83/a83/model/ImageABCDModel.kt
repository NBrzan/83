package com.group83.a83.model

data class ImageABCDModel(
    override val type: GameType = GameType.imageABCD,
    val id: Int,
    val imageSrc: String,
    val question: String,
    val answers: List<AnswerModel>,
    val correctAnswers: List<Int>,
) : GameQuestion()
