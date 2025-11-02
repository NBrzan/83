package com.group83.a83.view.component

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

