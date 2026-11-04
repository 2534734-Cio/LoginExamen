package com.example

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlin.test.*

class ServerTest {

    @Test
    fun `test root endpoint`() = testApplication {
        application {
            rootModule()
        }
        // verify server root returns 200
        assertEquals(HttpStatusCode.OK, client.get("/").status)
    }

    @Test
    fun `login correcto devuelve el rol del usuario`() = testApplication {
        application {
            rootModule()
        }
        val respuesta = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody("""{"username":"admin","password":"1234"}""")
        }
        assertEquals(HttpStatusCode.OK, respuesta.status)
        assertTrue(respuesta.bodyAsText().contains("ADMINISTRADOR"))
    }

    @Test
    fun `login con contrasena incorrecta devuelve 401`() = testApplication {
        application {
            rootModule()
        }
        val respuesta = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody("""{"username":"admin","password":"0000"}""")
        }
        assertEquals(HttpStatusCode.Unauthorized, respuesta.status)
    }
}
