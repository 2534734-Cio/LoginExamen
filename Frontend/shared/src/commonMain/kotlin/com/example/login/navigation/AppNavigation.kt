package com.example.login.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.login.model.LoginResponse
import com.example.login.model.Rol
import com.example.login.model.toRol
import com.example.login.ui.AdminScreen
import com.example.login.ui.ClienteScreen
import com.example.login.ui.DoctorScreen
import com.example.login.ui.GpsScreen
import com.example.login.ui.LoginScreen
import com.example.login.ui.SupervisorScreen
import com.example.login.ui.VendedorScreen

@Composable
fun AppNavigation() {
    var sesion by remember { mutableStateOf<LoginResponse?>(null) }
    val actual = sesion
    val nombre = actual?.name ?: ""
    val salir = { sesion = null }
    // true = se muestra la pantalla de prueba del GPS
    var mostrarGps by remember { mutableStateOf(false) }

    if (mostrarGps) {
        GpsScreen(onRegresar = { mostrarGps = false })
    } else if (actual == null) {
        LoginScreen(
            onLoginExitoso = { sesion = it },
            onProbarGps = { mostrarGps = true }
        )
    } else {
        when (actual.role?.toRol()) {
            Rol.ADMINISTRADOR -> AdminScreen(nombre, salir)
            Rol.SUPERVISOR -> SupervisorScreen(nombre, salir)
            Rol.CLIENTE -> ClienteScreen(nombre, salir)
            Rol.DOCTOR -> DoctorScreen(nombre, salir)
            Rol.VENDEDOR -> VendedorScreen(nombre, salir)
            null -> LoginScreen(onLoginExitoso = { sesion = it })
        }
    }
}