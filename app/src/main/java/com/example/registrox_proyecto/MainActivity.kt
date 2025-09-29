package com.example.registrox_proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registrox_proyecto.navigation.BottomNavItem
import com.example.registrox_proyecto.navigation.Routes
import com.example.registrox_proyecto.ui.components.bottombar.BottomBar
import com.example.registrox_proyecto.ui.screens.*
import com.example.registrox_proyecto.ui.theme.RegistroXProyectoTheme

import com.example.registrox_proyecto.ui.viewmodel.CarritoViewModel
import com.example.registrox_proyecto.ui.viewmodel.LoginViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroXProyectoTheme {
                QRApp()
            }
        }
    }
}

@Composable
fun QRApp() {
    val navController = rememberNavController()

    val loginViewModel = remember { LoginViewModel() }
    val carritoViewModel = remember { CarritoViewModel() }

    val user = loginViewModel.user.value
    val bottomItems = listOf(BottomNavItem.Home, BottomNavItem.QR, BottomNavItem.Profile) +
            if (user?.role == com.example.registrox_proyecto.data.model.Role.TRABAJADOR) listOf(BottomNavItem.Scan) else emptyList()

    androidx.compose.material3.Scaffold(
        bottomBar = {
            if (user != null) {
                BottomBar(navController = navController, items = bottomItems, userRole = user.role)
            }
        }
    ) { padding ->
        NavGraph(
            navController = navController,
            carritoViewModel = carritoViewModel,
            loginViewModel = loginViewModel,
            modifier = Modifier.padding(padding)
        )
    }
}


@Composable
fun NavGraph(
    navController: NavHostController,
    carritoViewModel: CarritoViewModel,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN, modifier = modifier) {
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController, viewModel = loginViewModel)
        }
        composable(Routes.HOME) {
            HomeScreen(navController = navController, carritoViewModel = carritoViewModel)
        }
        composable(Routes.ENTRADAS) {
            EntradasScreen(navController = navController, carritoViewModel = carritoViewModel)
        }
        composable(Routes.PROFILE) {
            val user = loginViewModel.user.value
            if (user != null) {
                ProfileScreen(user = user)
            }
        }
        composable(Routes.DETALLE) { backStackEntry ->
            val entradaId = backStackEntry.arguments?.getString("entradaId") ?: ""
            DetalleEntradaScreen(navController = navController, entradaId = entradaId)
        }
        composable(Routes.TRABAJADOR) {
            HomeTrabajadorScreen(onBackClick = { navController.navigate(Routes.HOME) })
        }
    }
}
