package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun cargarUsuarios(): List<User> {
    val texto = object {}.javaClass.getResource("/users.json")!!.readText()
    return Json.decodeFromString(texto)
}

fun Application.configureRouting() {
    val usuarios = cargarUsuarios()

    routing {
        get("/") {
            call.respondText("Backend activo")
        }

        post("/login") {
            val datos = call.receive<LoginRequest>()
            val usuario = usuarios.find {
                it.username == datos.username && it.password == datos.password
            }
            if (usuario != null) {
                call.respond(LoginResponse(true, "Bienvenido", usuario.name, usuario.role))
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    LoginResponse(false, "Usuario o contraseña incorrectos")
                )
            }
        }
    }
}