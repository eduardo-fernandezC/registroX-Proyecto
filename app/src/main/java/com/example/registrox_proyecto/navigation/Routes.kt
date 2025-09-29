package com.example.registrox_proyecto.navigation

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val ENTRADAS = "entradas"
    const val PROFILE = "profile"
    const val DETALLE = "detalle/{entradaId}"
    const val TRABAJADOR = "trabajador"

    fun detalleRoute(entradaId: String) = "detalle/$entradaId"
}