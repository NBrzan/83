package com.group83.a83.controller

import com.group83.a83.model.FlashcardModel

class FlashcardController(val question: FlashcardModel) {
    var isFront = true
        private set

    fun flip() {
        isFront = !isFront
    }
}
