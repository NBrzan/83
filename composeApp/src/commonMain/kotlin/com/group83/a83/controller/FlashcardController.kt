package com.group83.a83.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.group83.a83.model.FlashcardModel

class FlashcardController(val question: FlashcardModel) {
    var isFront by mutableStateOf(false)
        private set

    fun flip() {
        isFront = !isFront
    }
}
