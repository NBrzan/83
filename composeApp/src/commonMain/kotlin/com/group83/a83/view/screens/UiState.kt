package com.group83.a83.view.screens

data class Subject(
    val name: String,
    val emoji: String
)

data class UiState(
    val currentSelectedSubject: String = "Angleščina",
    val subjects: List<Subject> = listOf(
        Subject("Angleščina", "🌐"),
        Subject("Geografija", "🗺️")
    ),
    val flashcardCount: Int = 0,
    val isLoading: Boolean = false
)
