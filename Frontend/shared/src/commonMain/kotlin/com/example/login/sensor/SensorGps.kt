package com.example.login.sensor

import androidx.compose.runtime.Composable

data class Ubicacion(
    val latitud: Double,
    val longitud: Double,
    val precisionMetros: Float
)

data class EstadoGps(
    val disponible: Boolean,          // ¿Esta plataforma tiene GPS?
    val permisoConcedido: Boolean,    // ¿El usuario dio permiso de ubicación?
    val gpsActivado: Boolean,         // ¿La ubicación del teléfono está encendida?
    val ubicacion: Ubicacion?,        // Última ubicación (null mientras busca señal)
    val solicitarPermiso: () -> Unit  // Muestra la ventana para pedir el permiso
)

@Composable
expect fun rememberSensorGps(): EstadoGps
