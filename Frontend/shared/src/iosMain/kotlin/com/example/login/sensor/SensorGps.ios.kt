package com.example.login.sensor

import androidx.compose.runtime.Composable

// En esta plataforma no se usa el GPS
@Composable
actual fun rememberSensorGps(): EstadoGps = EstadoGps(
    disponible = false,
    permisoConcedido = false,
    gpsActivado = false,
    ubicacion = null,
    solicitarPermiso = {}
)
