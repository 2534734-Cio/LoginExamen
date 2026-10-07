package com.example.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val name: String? = null,
    val role: String? = null
)

enum class Rol {
    ADMINISTRADOR, SUPERVISOR, CLIENTE, DOCTOR, VENDEDOR
}

fun String.toRol(): Rol? = Rol.entries.find { it.name == this }