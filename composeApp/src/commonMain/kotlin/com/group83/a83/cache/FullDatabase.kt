package com.group83.a83.cache

import com.group83.a83.model.FlashcardModel
import com.group83.a83.model.ABCDModel
import com.group83.a83.model.AnswerModel
import com.group83.a83.model.GameQuestion
import com.group83.a83.model.ImageABCDModel
import com.group83.a83.model.ImageInputModel
import com.group83.a83.model.InputModel
import com.group83.a83.model.Stats

class FullDatabase(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.databaseQueries
    private val inputQueries = database.inputQuestionsQueries

    init {
        //ensureDefaultSubjectExists()
        val seeder = DatabaseSeeder(this)
        seeder.seed()

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

    internal fun insertUniqueSubject(name: String) {
        val subjects = dbQuery.getSubjectsByName(name).executeAsList()
        if (subjects.isEmpty()) {
            dbQuery.insertSubject(name)
        }
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
            ensureDefaultInputImageExistsForSubject(subjects[0].id)
            ensureDefaultInputExistsForSubject(subjects[0].id)
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

    internal fun insertABCDQuestionWithAnswers(subjectId: Long, questionText: String, answers: List<String>, correctPositions: List<Long>) {
        require(answers.isNotEmpty()) { "answers cannot be empty" }
        dbQuery.transaction {
            dbQuery.insertAbcdQuestion(subjectId, questionText)
            val qId = dbQuery.selectLatestAbcdQuestionId().executeAsOne()
            answers.forEachIndexed { idx, ans ->
                // question_type = 0 for normal ABCDQuestion
                val isCorrect = if (correctPositions.contains(idx.toLong())) 1L else 0L
                dbQuery.insertAbcdAnswer(qId, 0L, ans, isCorrect)
            }
        }
    }

    private fun ensureDefaultInputImageExistsForSubject(id: Long) {
        val inputQuestions = getInputQuestionsForSubject(id, 1)
        if (inputQuestions.isEmpty()) {
            insertInputQuestionWithAnswers(id, "Is this a question?", "https://cdn4.iconfinder.com/data/icons/top-search-7/128/_list_bullets_points_checklist_choose--512.png", listOf("Yes", "yes"))
        }
    }

    private fun ensureDefaultInputExistsForSubject(id: Long) {
        val inputQuestions = getInputQuestionsForSubject(id, 0)
        if (inputQuestions.isEmpty()) {
            insertInputQuestionWithAnswers(id, "Is this a question?", null, listOf("Yes", "yes"))
        }
    }

    internal fun getABCDQuestionsForSubject(subjectId: Long): List<ABCDModel> {
        val result = mutableListOf<ABCDModel>()
        dbQuery.selectAbcdQuestionsBySubject(subjectId) { qId, question ->
            val answers = dbQuery.selectAbcdAnswersByQuestion(qId, 0L) { aId, answer, _isCorrect ->
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

    internal fun insertABCDImageQuestionWithAnswers(subjectId: Long, questionText: String, imageSrcText: String, answers: List<String>, correctPositions: List<Long>) {
        require(answers.isNotEmpty()) { "answers cannot be empty" }
        dbQuery.transaction {
            dbQuery.insertAbcdImageQuestion(subjectId, questionText, imageSrcText)
            val qId = dbQuery.selectLatestABCDImageQuestionId().executeAsOne()
            answers.forEachIndexed { idx, ans ->
                // question_type = 1 for image questions
                val isCorrect = if (correctPositions.contains(idx.toLong())) 1L else 0L
                dbQuery.insertAbcdAnswer(qId, 1L, ans, isCorrect)
            }
        }
    }

    internal fun getABCDImageQuestionsForSubject(subjectId: Long): List<ImageABCDModel> {
        val result = mutableListOf<ImageABCDModel>()
        dbQuery.selectABCDImageQuestionsBySubject(subjectId) { qId, question, image_src ->
            val answers = dbQuery.selectAbcdAnswersByQuestion(qId, 1L) { aId, answer, _isCorrect ->
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

    internal fun insertInputQuestionWithAnswers(subjectId: Long, questionText: String, imageSrcText: String?, answers: List<String>) {
        require(answers.isNotEmpty()) { "answers cannot be empty" }
        val questionType = if (imageSrcText == null) 0L else 1L
        dbQuery.transaction {
            inputQueries.insertInputQuestion(subjectId, questionText, imageSrcText, questionType )
            val qId = inputQueries.selectLatestInputQuestionId().executeAsOne()

            answers.forEach { ans ->
                inputQueries.insertInputAnswer(qId, ans)
            }
        }
    }

    internal fun getInputQuestionsForSubject(subjectId: Long, questionType: Long): List<GameQuestion> {
        val result = mutableListOf<GameQuestion>()

        inputQueries.selectInputByType(subjectId, questionType) { qId, qType, question, image_src, _subjId ->
            val answers = inputQueries.selectAnswersForInputQuestion(qId) { aId, answer ->
                AnswerModel(id = aId.toInt(), answer = answer)
            }.executeAsList()

            if (qType == 0L) {
                result.add(
                    InputModel(
                        id = qId.toInt(),
                        question = question,
                        answers = answers
                    )
                )
            } else {
                result.add(
                    ImageInputModel(
                        id = qId.toInt(),
                        question = question,
                        imageSrc = if (image_src == null) "" else image_src,
                        answers = answers
                    )
                )
            }
        }.executeAsList()

        return result
    }

    internal fun saveStats(stats: Stats) {
        dbQuery.transaction {
            dbQuery.clearStats()
            dbQuery.insertStats(
                stats.correct.toLong(),
                stats.incorrect.toLong(),
                stats.unanswered.toLong()
            )
        }
    }

    internal fun getStats(): Stats {
        return dbQuery.selectStats { correct, incorrect, unanswered ->
            Stats(
                correct = correct.toInt(),
                incorrect = incorrect.toInt(),
                unanswered = unanswered.toInt()
            )
        }.executeAsOneOrNull() ?: Stats(0, 0, 0)
    }
}

//internal data class Subject(val id: Long, val name: String)
