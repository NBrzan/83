package com.group83.a83.controller

import com.group83.a83.AppRepositories
import com.group83.a83.model.Stats
import com.group83.a83.repository.StatsRepository
import kotlinx.coroutines.flow.StateFlow

class StatsController() {
    val repository = AppRepositories.statsRepository

    val stats: StateFlow<Stats> = repository.statsFlow

    fun incrementCorrect() = repository.incrementCorrect()
    fun incrementIncorrect() = repository.incrementIncorrect()
    fun incrementUnanswered() = repository.incrementUnanswered()
    fun decrementCorrect() = repository.decrementCorrect()
    fun decrementIncorrect() = repository.decrementIncorrect()
    fun decrementUnanswered() = repository.decrementUnanswered()
    fun refreshStats() = repository.refreshStats()
    fun saveStats(stats: Stats) = repository.saveStats(stats)
}
