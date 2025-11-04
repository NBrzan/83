package com.group83.a83

actual object PlatformFiles {
    actual fun readText(name: String): String? = null
    actual fun saveText(name: String, content: String): Boolean = false
}

