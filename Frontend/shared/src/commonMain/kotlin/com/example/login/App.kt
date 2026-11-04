package com.example.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.login.navigation.AppNavigation
import com.example.login.sensor.rememberNivelDeLuz

@Composable
fun App() {
    val luz = rememberNivelDeLuz()
    val modoOscuro = luz != null && luz < 10f

    val textoSensor = when {
        luz == null -> "Sensor de luz no disponible"
        modoOscuro -> "Luz ambiental: ${luz.toInt()} lx (modo oscuro)"
        else -> "Luz ambiental: ${luz.toInt()} lx"
    }

    MaterialTheme(colorScheme = if (modoOscuro) darkColorScheme() else lightColorScheme()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                AppNavigation()
                Text(
                    text = textoSensor,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .safeContentPadding()
                        .padding(16.dp)
                )
            }
        }
    }
}
