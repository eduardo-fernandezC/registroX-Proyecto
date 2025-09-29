package com.example.registrox_proyecto.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEntradaScreen(navController: NavController, entradaId: String) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Entrada #$entradaId") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
            }
        )
    }) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize(), contentAlignment = Alignment.Center) {
            val qrBitmap = generateQRCode("Entrada ID: $entradaId")
            qrBitmap?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = "Código QR", modifier = Modifier.size(250.dp))
            } ?: run {
                Text("No se pudo generar el QR", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


fun generateQRCode(text: String): Bitmap? {
    return try {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 500, 500)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                pixels[y * width + x] = if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
            }
        }
        Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
    } catch (e: Exception) {
        null
    }
}
