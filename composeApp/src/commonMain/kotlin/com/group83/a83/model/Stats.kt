package com.group83.a83.model

data class Stats(
    val correct: Int,
    val incorrect: Int,
    val unanswered: Int
) {
    val total: Int get() = correct + incorrect + unanswered
}

