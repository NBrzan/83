package com.group83.a83

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform