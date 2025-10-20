package com.group83.a83


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.group83.a83.FlashCardGameUi.FlashcardScreen
import com.group83.a83.model.FlashCard
import com.group83.a83.model.FlashCardDeck

@Composable
@Preview
fun App() {
    val deck = FlashCardDeck(
        "English",
        listOf(
            FlashCard("Abundant", "Existing in large quantities"),
            FlashCard("Benevolent", "Well meaning and kindly")
        )
    )

    MaterialTheme {
        FlashcardScreen(deck)
    }
}