
package com.group83.a83.data

import com.group83.a83.sqldelight.Database
import com.group83.a83.sqldelight.FlashCard

class FlashCardRepository(private val database: Database) {
    fun getAll(): List<FlashCard> =
        database.flashCardQueries.selectAll().executeAsList()

    fun create(front: String, back: String) {
        val id: Long? = null
        database.flashCardQueries.insert(id = id, front = front, back = back)
    }

    fun printAll() {
        getAll().forEach { println(it) }
    }
}