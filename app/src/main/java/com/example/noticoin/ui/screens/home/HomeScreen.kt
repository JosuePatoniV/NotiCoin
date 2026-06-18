package com.example.noticoin.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noticoin.R
import com.example.noticoin.ui.screens.favoritas.FavoritasScreen
import com.example.noticoin.ui.screens.mercado.MercadoScreen
import com.example.noticoin.ui.screens.notificaciones.NotificacionesScreen
import com.example.noticoin.viewmodel.FavoritasViewModel
import com.example.noticoin.viewmodel.MercadoViewModel
import com.example.noticoin.viewmodel.NotificacionesViewModel

enum class HomeTab { MERCADO, FAVORITAS, NOTIFICACIONES }

@Composable
fun HomeScreen() {
    var tabActual by remember { mutableStateOf(HomeTab.MERCADO) }

    val mercadoViewModel: MercadoViewModel = viewModel()
    val favoritasViewModel: FavoritasViewModel = viewModel()
    val notificacionesViewModel: NotificacionesViewModel = viewModel()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                NavigationBarItem(
                    selected = tabActual == HomeTab.MERCADO,
                    onClick = { tabActual = HomeTab.MERCADO },
                    icon = {
                        Icon(
                            if (tabActual == HomeTab.MERCADO) Icons.Filled.ShowChart else Icons.Outlined.ShowChart,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(R.string.tab_mercado)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = tabActual == HomeTab.FAVORITAS,
                    onClick = { tabActual = HomeTab.FAVORITAS },
                    icon = {
                        Icon(
                            if (tabActual == HomeTab.FAVORITAS) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(R.string.tab_favoritas)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = tabActual == HomeTab.NOTIFICACIONES,
                    onClick = { tabActual = HomeTab.NOTIFICACIONES },
                    icon = {
                        Icon(
                            if (tabActual == HomeTab.NOTIFICACIONES) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(R.string.tab_notificaciones)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    ) { padding ->
        AnimatedContent(
            targetState = tabActual,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "tab_transition"
        ) { tab ->
            when (tab) {
                HomeTab.MERCADO -> MercadoScreen(viewModel = mercadoViewModel)
                HomeTab.FAVORITAS -> FavoritasScreen(viewModel = favoritasViewModel)
                HomeTab.NOTIFICACIONES -> NotificacionesScreen(viewModel = notificacionesViewModel)
            }
        }
    }
}
