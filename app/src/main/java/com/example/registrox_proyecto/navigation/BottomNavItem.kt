package com.example.registrox_proyecto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem(Routes.HOME,"Home", Icons.Default.Home)
    object QR : BottomNavItem(Routes.ENTRADAS, "Entradas", Icons.Default.QrCode2)
    object Profile : BottomNavItem(Routes.PROFILE, "Cuenta", Icons.Default.AccountCircle)
    object Scan : BottomNavItem(Routes.TRABAJADOR, "Escanear", Icons.Default.QrCode2) // esto es solamente para trabajdor
}