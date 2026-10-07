package com.example.login.network

import com.example.login.model.LoginRequest
import com.example.login.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AuthService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun login(username: String, password: String): LoginResponse {
        return try {
            val respuesta = client.post("${baseUrl()}/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }
            respuesta.body<LoginResponse>()
        } catch (e: Exception) {
            LoginResponse(success = false, message = "No se pudo conectar: ${e::class.simpleName} - ${e.message}")
        }
    }
}