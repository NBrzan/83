package com.group83.a83.repository

import com.group83.a83.cache.FullDatabase
import com.group83.a83.model.Stats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsRepository(private val db: FullDatabase) {
    private val _statsFlow = MutableStateFlow(db.getStats())
    val statsFlow: StateFlow<Stats> = _statsFlow.asStateFlow()

    fun refreshStats() {
        _statsFlow.value = db.getStats()
    }

    fun saveStats(stats: Stats) {
        db.saveStats(stats)
        refreshStats()
    }

    fun incrementCorrect() {
        val current = _statsFlow.value
        saveStats(current.copy(correct = current.correct + 1))
    }

    fun incrementIncorrect() {
        val current = _statsFlow.value
        saveStats(current.copy(incorrect = current.incorrect + 1))
    }

    fun incrementUnanswered() {
        val current = _statsFlow.value
        saveStats(current.copy(unanswered = current.unanswered + 1))
    }

    fun decrementCorrect() {
        val current = _statsFlow.value
        if (current.correct > 0) {
            saveStats(current.copy(correct = current.correct - 1))
        }
    }

    fun decrementIncorrect() {
        val current = _statsFlow.value
        if (current.incorrect > 0) {
            saveStats(current.copy(incorrect = current.incorrect - 1))
        }
    }

    fun decrementUnanswered() {
        val current = _statsFlow.value
        if (current.unanswered > 0) {
            saveStats(current.copy(unanswered = current.unanswered - 1))
        }
    }
}
