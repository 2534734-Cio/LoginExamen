package com.example.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.login.model.LoginResponse
import com.example.login.network.AuthService
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginExitoso: (LoginResponse) -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val authService = remember { AuthService() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    cargando = true
                    error = null
                    val respuesta = authService.login(usuario.trim(), contrasena)
                    cargando = false
                    if (respuesta.success) {
                        onLoginExitoso(respuesta)
                    } else {
                        error = respuesta.message
                    }
                }
            },
            enabled = !cargando && usuario.isNotBlank() && contrasena.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (cargando) "Entrando..." else "Entrar")
        }
    }
}