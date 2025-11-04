package com.group83.a83

expect object PlatformFiles {
    fun readText(name: String): String?
    fun saveText(name: String, content: String): Boolean
}

