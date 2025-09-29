package com.example.registrox_proyecto.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.registrox_proyecto.data.model.User
import com.example.registrox_proyecto.data.repository.AuthRepository

class LoginViewModel (
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel(){

    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val loginError = mutableStateOf("")
    val user = mutableStateOf<User?>(null)

    fun login(){
        val result = authRepository.login(email.value,password.value)
        if(result != null){
            user.value = result
            loginError.value = ""
        } else {
            loginError.value = "Correo o contraseña invalidos"
        }
    }

    fun logout() {
        authRepository.logout()
        user.value = null
        email.value = ""
        password.value = ""
        loginError.value = ""
    }
}