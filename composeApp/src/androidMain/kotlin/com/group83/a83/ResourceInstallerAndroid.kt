package com.group83.a83

import android.annotation.SuppressLint
import android.content.Context
import java.io.File

@SuppressLint("StaticFieldLeak") // Maybe fix later
object ResourceInstaller {

    val defaultJsonFiles = listOf("english.json")

    private lateinit var ctx: Context
    fun init(context: Context) { ctx = context.applicationContext }

    fun installDefaultsIfNeeded(appDir: String = ctx.filesDir.path) {
        val dir = File(appDir)
        if (!dir.exists()) dir.mkdirs()
        val res = ctx.resources
        val pkg = ctx.packageName

        for (name in defaultJsonFiles) {
            val target = File(dir, name)
            if (target.exists()) continue

            val nameWithoutExt = name.substringBeforeLast('.')
            val resId = res.getIdentifier(nameWithoutExt, "raw", pkg)
            if (resId == 0) continue

            try {
                res.openRawResource(resId).use { input ->
                    target.outputStream().use { out -> input.copyTo(out) }
                }
            } catch (e: Exception) {
                // ignore
            }
        }
    }

    fun saveJson(appDir: String = ctx.filesDir.path, name: String, content: String): Boolean {
        return try {
            val dir = File(appDir)
            if (!dir.exists()) dir.mkdirs()
            File(dir, name).writeText(content)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun readJson(appDir: String = ctx.filesDir.path, name: String): String? {
        return try {
            val f = File(appDir, name)
            if (!f.exists()) null else f.readText()
        } catch (e: Exception) {
            null
        }
    }
}

