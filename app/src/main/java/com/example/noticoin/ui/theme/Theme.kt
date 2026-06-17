package com.example.noticoin.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val EsquemaOscuro = darkColorScheme(
    primary = Acento,
    onPrimary = Fondo,
    primaryContainer = AcentoVariante,
    onPrimaryContainer = OnFondo,
    secondary = AcentoVariante,
    onSecondary = Fondo,
    background = Fondo,
    onBackground = OnFondo,
    surface = Superficie,
    onSurface = OnFondo,
    surfaceVariant = GrisClaro,
    onSurfaceVariant = OnSuperficie,
    error = Rojo,
    onError = OnFondo
)

@Composable
fun NotiCoinTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaOscuro,
        typography = Typography,
        content = content
    )
}
