package com.group83.a83.cache

import com.group83.a83.model.FlashcardModel

internal class FullDatabase(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.databaseQueries

    internal fun getAllCards(): List<FlashcardModel> {
        return dbQuery.selectAllCards(::mapFlashCard).executeAsList()
    }

    internal fun insertCard(front: String, back: String) {
        dbQuery.insertCard(front, back)
    }

    internal fun removeAllCards() {
        dbQuery.removeAllCards()
    }

    private fun mapFlashCard(
        id: Long,
        front: String,
        back: String
    ): FlashcardModel {
        return FlashcardModel(
            id = id.toInt(),
            front = front,
            back = back,
        )
    }

    internal fun clearAndCreateCards(cards: List<FlashcardModel>) {
        dbQuery.transaction {
            dbQuery.removeAllCards()
            cards.forEach { card ->
                dbQuery.insertCard(
                    front = card.front,
                    back = card.back
                )
            }
        }
    }

}