package com.group83.a83.model

data class FlashcardModel(
    override val type: GameType = GameType.flashcards,
    val id: Int,
    val front: String,
    val back: String,
) : GameQuestion()
