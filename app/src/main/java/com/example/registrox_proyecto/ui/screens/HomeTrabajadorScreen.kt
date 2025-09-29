package com.example.registrox_proyecto.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.registrox_proyecto.scannerqr.ScannerActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTrabajadorScreen(onBackClick: () -> Unit = {}) {
    var codigoEscaneado by remember { mutableStateOf("") }

    var resultado by remember { mutableStateOf<String?>(null) }

    val qrLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            codigoEscaneado = result.contents
            resultado = "Código escaneado: $codigoEscaneado"
        } else {
            resultado = "No se detectó ningún código"
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Column {
                    Text("Bienvenido", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
                    Text("Trabajador", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            actions = {
                Button(onClick = onBackClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                    Text("Salir")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = codigoEscaneado,
                onValueChange = { codigoEscaneado = it },
                label = { Text("Ingrese código QR o ID") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val options = ScanOptions().apply {
                        setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                        setPrompt("Apunta al QR para escanear")
                        setCameraId(0)
                        setBeepEnabled(true)
                        setOrientationLocked(true)
                        setCaptureActivity(ScannerActivity::class.java)
                        setBarcodeImageEnabled(true)
                    }
                    qrLauncher.launch(options)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Escanear QR")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { resultado = "Validando manual: $codigoEscaneado" },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Validar Manual")
            }

            Spacer(Modifier.height(16.dp))

            resultado?.let {
                Text(it, style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
