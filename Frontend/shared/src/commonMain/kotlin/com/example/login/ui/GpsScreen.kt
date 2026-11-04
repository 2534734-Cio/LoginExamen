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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.login.sensor.rememberSensorGps
import kotlin.math.roundToLong

private fun Double.cincoDecimales(): Double = (this * 100000).roundToLong() / 100000.0

@Composable
fun GpsScreen(onRegresar: () -> Unit) {
    val gps = rememberSensorGps()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Prueba del sensor GPS", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        val ubicacion = gps.ubicacion
        when {
            !gps.disponible ->
                Text("El GPS no está disponible en esta plataforma.", textAlign = TextAlign.Center)

            !gps.permisoConcedido -> {
                Text("La app necesita permiso para usar tu ubicación.", textAlign = TextAlign.Center)
                Spacer(Modifier.height(12.dp))
                Button(onClick = gps.solicitarPermiso) { Text("Dar permiso de ubicación") }
            }

            !gps.gpsActivado ->
                Text("Activa la ubicación del dispositivo para usar el GPS.", textAlign = TextAlign.Center)

            ubicacion == null ->
                Text("Buscando señal GPS...")

            else -> {
                Text("Latitud: ${ubicacion.latitud.cincoDecimales()}", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("Longitud: ${ubicacion.longitud.cincoDecimales()}", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("Precisión: ± ${ubicacion.precisionMetros.toInt()} m")
            }
        }

        Spacer(Modifier.height(32.dp))
        OutlinedButton(onClick = onRegresar) { Text("Regresar") }
    }
}
