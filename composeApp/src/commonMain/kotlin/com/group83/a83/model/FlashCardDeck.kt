package com.group83.a83.model

class FlashCardDeck(val subject: String, private val cards: List<FlashCard> = emptyList())  {
    private var currentIndex = 0

    fun getCurrentCard():Result<FlashCard, String> {
        if (cards.isEmpty()){
            return Result.Err("Empty list")
        }
        if (currentIndex >= cards.size){
            return Result.Err("End of list")
        }
        return Result.Ok(cards[currentIndex])
    }

    fun nextCard(): Result<FlashCard, String> {
        if (currentIndex +1 >= cards.size) {
            return Result.Err("End of list")
        }
        currentIndex++
        return Result.Ok(cards[currentIndex])
    }

    fun prevCard(): Result<FlashCard, String> {
        if (currentIndex -1 < 0) {
            return Result.Err("End of list")
        }
        currentIndex--
        return Result.Ok(cards[currentIndex])
    }
}