package com.group83.a83.view.component

// Simple in-memory models for created questions. These are local to the UI layer for now.
sealed class QuestionModel {
    data class MultipleChoice(
        val question: String,
        val answers: List<String>,
        val correct: List<Boolean>
    ) : QuestionModel()

    data class InputAnswer(
        val question: String,
        val answers: List<String>
    ) : QuestionModel()

    data class Flashcard(
        val question: String,
        val answer: String
    ) : QuestionModel()
}

