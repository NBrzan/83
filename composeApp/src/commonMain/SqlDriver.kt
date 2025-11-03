package com.group83.a83

import app.cash.sqldelight.db.SqlDriver
import com.group83.a83.sqldelight.Database
expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    val database = Database(driver)

    // Do more work with the database (see below).pro
    return database
}