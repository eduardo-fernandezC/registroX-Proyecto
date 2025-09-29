package com.example.registrox_proyecto.data.repository

import com.example.registrox_proyecto.data.model.Role
import com.example.registrox_proyecto.data.model.User

class AuthRepository {

    fun login(email: String, password: String): User?{
        if(email.isBlank() || password.isBlank())return null

        val role = if (email.trim().lowercase().endsWith("@registrox.cl")){
            Role.TRABAJADOR
        }else{
            Role.USUARIO
        }
        return User(email.trim(),role)
    }

    fun logout(): Boolean{
        return true
    }
}