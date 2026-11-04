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
        val url = "${baseUrl()}/login"
        // Logging: estos mensajes aparecen en Logcat (busca LOGIN_DEBUG)
        println("LOGIN_DEBUG -> Petición enviada a $url con usuario: $username")
        return try {
            val respuesta = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }
            println("LOGIN_DEBUG -> Código HTTP recibido: ${respuesta.status.value}")
            val datos = respuesta.body<LoginResponse>()
            println("LOGIN_DEBUG -> Respuesta del servidor: $datos")
            datos
        } catch (e: Exception) {
            println("LOGIN_DEBUG -> ERROR: ${e::class.simpleName} - ${e.message}")
            LoginResponse(success = false, message = "No se pudo conectar: ${e::class.simpleName} - ${e.message}")
        }
    }
}
