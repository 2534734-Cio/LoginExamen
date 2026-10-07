package com.example.login

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.login.navigation.AppNavigation

@Composable
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}