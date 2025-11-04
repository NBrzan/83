package com.group83.a83.cache

import com.group83.a83.model.FlashcardModel
import com.group83.a83.model.ABCDModel
import com.group83.a83.model.AnswerModel
import com.group83.a83.model.ImageABCDModel

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
            ensureDefaultABCDxistsForSubject(subjects[0].id)
            ensureDefaultABCDImageExistsForSubject(subjects[0].id)
        }
    }

    private fun ensureDefaultCardsExistsForSubject(id: Long) {
        val cards = getAllCards()
        if (cards.isEmpty()) {
            insertCardWithSubject(id, "Hello", "Hola")
        }
    }

    private fun ensureDefaultABCDxistsForSubject(id: Long) {
        val abcd = getABCDQuestionsForSubject(id)
        if (abcd.isEmpty()) {
            insertABCDQuestionWithAnswers(id, "Is this a question?", listOf("Yes", "No", "Maybe", "Not sure"), listOf(0))
        }
    }

    private fun ensureDefaultABCDImageExistsForSubject(id: Long) {
        val abcd = getABCDImageQuestionsForSubject(id)
        if (abcd.isEmpty()) {
            insertABCDImageQuestionWithAnswers(id, "Is this a question?", "https://cdn4.iconfinder.com/data/icons/top-search-7/128/_list_bullets_points_checklist_choose--512.png", listOf("Yes", "No", "Maybe", "Not sure"), listOf(0))
        }
    }

    internal fun insertABCDQuestionWithAnswers(subjectId: Long, questionText: String, answers: List<String>, correctPositions: List<Int>) {
        require(answers.isNotEmpty()) { "answers cannot be empty" }
        dbQuery.transaction {
            dbQuery.insertAbcdQuestion(subjectId, questionText)
            val qId = dbQuery.selectLatestAbcdQuestionId().executeAsOne()
            answers.forEachIndexed { idx, ans ->
                // question_type = 0 for normal ABCDQuestion
                val isCorrect = if (correctPositions.contains(idx)) 1L else 0L
                dbQuery.insertAbcdAnswer(qId, 0L, ans, isCorrect)
            }
        }
    }

    internal fun getABCDQuestionsForSubject(subjectId: Long): List<ABCDModel> {
        val result = mutableListOf<ABCDModel>()
        dbQuery.selectAbcdQuestionsBySubject(subjectId) { qId, question ->
            val answers = dbQuery.selectAbcdAnswersByQuestion(qId, 0L) { aId, answer, isCorrect ->
                AnswerModel(id = aId.toInt(), answer = answer)
            }.executeAsList()

            val correctIds = dbQuery.selectCorrectAbcdAnswersIdsByQuestion(qId, 0L).executeAsList()


            result.add(
                ABCDModel(
                    id = qId.toInt(),
                    question = question,
                    answers = answers,
                    correctAnswers = correctIds
                )
            )
        }.executeAsList()

        return result
    }

    internal fun insertABCDImageQuestionWithAnswers(subjectId: Long, questionText: String, imageSrcText: String, answers: List<String>, correctPositions: List<Int>) {
        require(answers.isNotEmpty()) { "answers cannot be empty" }
        dbQuery.transaction {
            dbQuery.insertAbcdImageQuestion(subjectId, questionText, imageSrcText)
            val qId = dbQuery.selectLatestABCDImageQuestionId().executeAsOne()
            answers.forEachIndexed { idx, ans ->
                // question_type = 1 for image questions
                val isCorrect = if (correctPositions.contains(idx)) 1L else 0L
                dbQuery.insertAbcdAnswer(qId, 1L, ans, isCorrect)
            }
        }
    }

    internal fun getABCDImageQuestionsForSubject(subjectId: Long): List<ImageABCDModel> {
        val result = mutableListOf<ImageABCDModel>()
        dbQuery.selectABCDImageQuestionsBySubject(subjectId) { qId, question, image_src ->
            val answers = dbQuery.selectAbcdAnswersByQuestion(qId, 1L) { aId, answer, isCorrect ->
                AnswerModel(id = aId.toInt(), answer = answer)
            }.executeAsList()

            val correctIds = dbQuery.selectCorrectAbcdAnswersIdsByQuestion(qId, 1L).executeAsList()


            result.add(
                ImageABCDModel(
                    id = qId.toInt(),
                    question = question,
                    answers = answers,
                    imageSrc = image_src,
                    correctAnswers = correctIds
                )
            )
        }.executeAsList()

        return result
    }
}

//internal data class Subject(val id: Long, val name: String)
