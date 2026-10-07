package com.example.login

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform