package com.example.login.sensor

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberSensorGps(): EstadoGps {
    val context = LocalContext.current

    // ¿Ya tiene la app permiso de ubicación precisa?
    fun tienePermiso(): Boolean =
        context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    var permiso by remember { mutableStateOf(tienePermiso()) }
    var gpsActivado by remember { mutableStateOf(true) }
    var ubicacion by remember { mutableStateOf<Ubicacion?>(null) }

    // 1. Ventana del sistema "¿Permitir que Login acceda a tu ubicación?"
    val pedirPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        permiso = tienePermiso()
    }

    // 2. Se escucha el GPS solo cuando hay permiso; se vuelve a ejecutar si el permiso cambia
    DisposableEffect(permiso) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsActivado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // 3. El listener recibe cada nueva ubicación
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                ubicacion = Ubicacion(location.latitude, location.longitude, location.accuracy)
            }
            override fun onProviderEnabled(provider: String) { gpsActivado = true }
            override fun onProviderDisabled(provider: String) { gpsActivado = false }
            @Deprecated("Requerido en versiones antiguas de Android")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        if (permiso) {
            try {
                // Última ubicación conocida, para mostrar algo de inmediato
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                    ubicacion = Ubicacion(it.latitude, it.longitude, it.accuracy)
                }
                // 4. Pide una ubicación nueva cada 2 segundos
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2000L, 0f, listener
                )
            } catch (e: SecurityException) {
                permiso = false
            }
        }

        // 5. Al salir de la pantalla se deja de usar el GPS (ahorra batería)
        onDispose { locationManager.removeUpdates(listener) }
    }

    return EstadoGps(
        disponible = true,
        permisoConcedido = permiso,
        gpsActivado = gpsActivado,
        ubicacion = ubicacion,
        solicitarPermiso = {
            pedirPermiso.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    )
}
