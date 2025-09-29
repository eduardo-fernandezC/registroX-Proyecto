package com.example.registrox_proyecto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.registrox_proyecto.data.model.Entrada
import com.example.registrox_proyecto.data.repository.EntradasRepository
import com.example.registrox_proyecto.navigation.Routes
import com.example.registrox_proyecto.ui.viewmodel.CarritoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    entradasRepository: EntradasRepository = EntradasRepository()
) {
    var searchText by remember { mutableStateOf("") }

    val entradas = remember(searchText) { entradasRepository.buscarPorTitulo(searchText) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("RegistroX", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleLarge)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.ENTRADAS) }) {
                        BadgedBox(
                            badge = {
                                if (carritoViewModel.carrito.isNotEmpty()) {
                                    Badge { Text(carritoViewModel.carrito.size.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar entradas...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(entradas) { entrada ->
                    EntradaItem(entrada = entrada, onAgregar = { carritoViewModel.agregar(it) }, onVerQr = {
                        navController.navigate(Routes.detalleRoute(it.id))
                    })
                }
            }

            if (carritoViewModel.carrito.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate(Routes.ENTRADAS) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Comprar (${carritoViewModel.carrito.size})")
                    }
                    Button(
                        onClick = { carritoViewModel.limpiar() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}


@Composable
private fun EntradaItem(entrada: Entrada, onAgregar: (Entrada) -> Unit, onVerQr: (Entrada) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(entrada.titulo, style = MaterialTheme.typography.titleLarge)
            Text(entrada.lugar, style = MaterialTheme.typography.bodyMedium)
            Text("Precio: ${entrada.precio} $", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onAgregar(entrada) }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text("Agregar al carrito")
                }
            }
        }
    }
}
