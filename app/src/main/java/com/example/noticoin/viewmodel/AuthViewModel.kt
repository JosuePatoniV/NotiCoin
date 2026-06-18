package com.example.noticoin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticoin.data.local.AppDatabase
import com.example.noticoin.data.repository.AuthRepository
import com.example.noticoin.data.repository.ResultAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val mensaje: String) : AuthState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AuthRepository by lazy {
        val db = AppDatabase.getInstance(application)
        AuthRepository(db.usuarioDao())
    }

    private val _estado = MutableStateFlow<AuthState>(AuthState.Idle)
    val estado: StateFlow<AuthState> = _estado

    fun login(nombre: String, contrasena: String) {
        if (nombre.isBlank() || contrasena.isBlank()) {
            _estado.value = AuthState.Error("Completa todos los campos")
            return
        }
        viewModelScope.launch {
            _estado.value = AuthState.Loading
            _estado.value = when (val r = repo.login(nombre, contrasena)) {
                is ResultAuth.Success -> AuthState.Success
                is ResultAuth.Error -> AuthState.Error(r.mensaje)
            }
        }
    }

    fun registrar(nombre: String, contrasena: String) {
        if (nombre.isBlank() || contrasena.isBlank()) {
            _estado.value = AuthState.Error("Completa todos los campos")
            return
        }
        viewModelScope.launch {
            _estado.value = AuthState.Loading
            _estado.value = when (val r = repo.registrar(nombre, contrasena)) {
                is ResultAuth.Success -> AuthState.Success
                is ResultAuth.Error -> AuthState.Error(r.mensaje)
            }
        }
    }

    fun resetEstado() {
        _estado.value = AuthState.Idle
    }
}
