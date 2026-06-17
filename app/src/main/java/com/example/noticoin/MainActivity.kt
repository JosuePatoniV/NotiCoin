package com.example.noticoin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noticoin.ui.screens.home.HomeScreen
import com.example.noticoin.ui.screens.login.LoginScreen
import com.example.noticoin.ui.screens.register.RegisterScreen
import com.example.noticoin.ui.theme.NotiCoinTheme

sealed class Ruta(val ruta: String) {
    object Login : Ruta("login")
    object Register : Ruta("register")
    object Home : Ruta("home")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotiCoinTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Ruta.Login.ruta
                ) {
                    composable(
                        route = Ruta.Login.ruta,
                        enterTransition = { slideInHorizontally { -it } },
                        exitTransition = { slideOutHorizontally { -it } }
                    ) {
                        LoginScreen(
                            onLoginExitoso = {
                                navController.navigate(Ruta.Home.ruta) {
                                    popUpTo(Ruta.Login.ruta) { inclusive = true }
                                }
                            },
                            onIrARegistro = { navController.navigate(Ruta.Register.ruta) }
                        )
                    }
                    composable(
                        route = Ruta.Register.ruta,
                        enterTransition = { slideInHorizontally { it } },
                        exitTransition = { slideOutHorizontally { it } }
                    ) {
                        RegisterScreen(
                            onRegistroExitoso = { navController.popBackStack() },
                            onVolver = { navController.popBackStack() }
                        )
                    }
                    composable(
                        route = Ruta.Home.ruta,
                        enterTransition = { slideInHorizontally { it } },
                        exitTransition = { slideOutHorizontally { it } }
                    ) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}
