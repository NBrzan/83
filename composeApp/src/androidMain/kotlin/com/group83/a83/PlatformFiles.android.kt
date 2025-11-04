package com.group83.a83

import android.content.Context

actual object PlatformFiles {
    actual fun readText(name: String): String? = ResourceInstaller.readJson(name = name)
    actual fun saveText( name: String, content: String): Boolean = ResourceInstaller.saveJson(name = name, content = content)
}

