package com.example

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val password: String,
    val name: String,
    val role: String
)

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