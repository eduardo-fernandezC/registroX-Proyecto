package com.example.registrox_proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.registrox_proyecto.navigation.Routes
import com.example.registrox_proyecto.ui.components.topbar.DefaultTopBar
import com.example.registrox_proyecto.ui.viewmodel.CarritoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradasScreen(navController: NavController, carritoViewModel: CarritoViewModel) {
    Scaffold(topBar = { DefaultTopBar(title = "Mis Entradas", onBackClick = { navController.popBackStack() }) }) { padding ->
        if (carritoViewModel.carrito.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center) {
                Text("No has comprado entradas todavía.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(carritoViewModel.carrito) { entrada ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(entrada.titulo, style = MaterialTheme.typography.titleMedium)
                                Text(entrada.lugar)
                            }
                            Button(onClick = { navController.navigate(Routes.detalleRoute(entrada.id)) }) {
                                Text("Ver QR")
                            }
                        }
                    }
                }
            }
        }
    }
}
