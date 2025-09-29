package com.example.registrox_proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.registrox_proyecto.navigation.Routes
import com.example.registrox_proyecto.ui.viewmodel.LoginViewModel

/**
 * Pantalla de login.
 * - Observa los estados del LoginViewModel y actúa en consecuencia.
 * - Si el usuario está autenticado se navega al HOME.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    // Observamos valores desde el ViewModel
    val email = viewModel.email.value
    val password = viewModel.password.value
    val loginError = viewModel.loginError.value
    val user = viewModel.user.value

    // Si ya hay usuario autenticado, navegamos y limpiamos el backstack
    if (user != null) {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.LOGIN) { inclusive = true }
        }
    }

    // Layout centrado con campos y botón
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Bienvenido a RegistroX", style = MaterialTheme.typography.headlineSmall)

            // Campo correo
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.email.value = it },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Campo contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.password.value = it },
                label = { Text("Contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Mostrar error si lo hay
            if (loginError.isNotEmpty()) {
                Text(loginError, color = MaterialTheme.colorScheme.error)
            }

            // Botón que delega la lógica al ViewModel
            Button(onClick = { viewModel.login() }, modifier = Modifier.fillMaxWidth()) {
                Text("Ingresar")
            }
        }
    }
}
