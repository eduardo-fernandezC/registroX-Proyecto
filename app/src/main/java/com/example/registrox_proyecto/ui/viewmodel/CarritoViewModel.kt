package com.example.registrox_proyecto.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.registrox_proyecto.data.model.Entrada

class CarritoViewModel : ViewModel(){

    val carrito = mutableStateListOf<Entrada>()
    val mensajeOperacion = mutableStateOf("")

    fun agregar(entrada: Entrada){
        if (carrito.any{it.id == entrada.id}){
            mensajeOperacion.value = "La entrada ya esta en el carrito"
            return
        }
        carrito.add(entrada)
        mensajeOperacion.value = "Entrada agregada"
    }

    fun eliminarPorId(id:String){
        carrito.removeAll{it.id == id}
        mensajeOperacion.value = "Entrada eliminada"
    }
    fun limpiar(){
        carrito.clear()
        mensajeOperacion.value = "carrito vacio"
    }
    fun total(): Double = carrito.sumOf { it.precio }
}