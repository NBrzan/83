package com.group83.a83.model

data class ImageInputModel(
    override val type: GameType = GameType.imageInput,
    val id: Int,
    val imageSrc: String,
    val question: String,
    val answers: List<AnswerModel>,
) : GameQuestion()
