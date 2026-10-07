package com.example.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun PantallaRol(
    titulo: String,
    descripcion: String,
    nombre: String,
    onSalir: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(titulo, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Bienvenido(a), $nombre")
        Spacer(Modifier.height(8.dp))
        Text(descripcion)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onSalir) { Text("Cerrar sesión") }
    }
}

@Composable
fun AdminScreen(nombre: String, onSalir: () -> Unit) =
    PantallaRol("Panel de Administrador", "Gestión de usuarios y configuración.", nombre, onSalir)

@Composable
fun SupervisorScreen(nombre: String, onSalir: () -> Unit) =
    PantallaRol("Panel de Supervisor", "Supervisión de actividades y reportes.", nombre, onSalir)

@Composable
fun ClienteScreen(nombre: String, onSalir: () -> Unit) =
    PantallaRol("Panel de Cliente", "Consulta de tus servicios y pedidos.", nombre, onSalir)

@Composable
fun DoctorScreen(nombre: String, onSalir: () -> Unit) =
    PantallaRol("Panel de Doctor", "Consulta de pacientes y citas.", nombre, onSalir)

@Composable
fun VendedorScreen(nombre: String, onSalir: () -> Unit) =
    PantallaRol("Panel de Vendedor", "Registro de ventas y productos.", nombre, onSalir)