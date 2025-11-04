package com.group83.a83.cache

import com.group83.a83.model.FlashcardModel

class FullDatabase(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.databaseQueries

    init {
        ensureDefaultSubjectExists()
    }

    // Keep existing API
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

    internal fun getAllSubjects(): List<Subject> {
        return dbQuery.selectAllSubjects(::mapSubject).executeAsList()
    }

    internal fun insertSubject(name: String) {
        dbQuery.insertSubject(name)
    }

    internal fun insertCardWithSubject(subjectId: Long, front: String, back: String) {
        dbQuery.insertCardWithSubject(subjectId, front, back)
    }

    internal fun getCardsForSubject(subjectId: Long): List<FlashcardModel> {
        return dbQuery.selectCardsBySubject(subjectId, ::mapFlashCard).executeAsList()
    }

    private fun mapSubject(id: Long, name: String): Subject {
        return Subject(id = id, name = name)
    }

    private fun ensureDefaultSubjectExists() {
        var subjects = getAllSubjects()
        if (subjects.isEmpty()) {
            insertSubject("English")
            subjects = getAllSubjects()
            ensureDefaultCardsExistsForSubject(subjects[0].id)
        }
    }

    private fun ensureDefaultCardsExistsForSubject(id: Long) {
        val cards = getAllCards()
        if (cards.isEmpty()) {
            insertCardWithSubject(id, "Hello", "Hola")
        }
    }

}


//data class Subject(val id: Long, val name: String)
