package com.example.noticoin.data.repository

import com.example.noticoin.data.local.dao.UsuarioDao
import com.example.noticoin.data.local.entities.Usuario

sealed class ResultAuth {
    object Success : ResultAuth()
    data class Error(val mensaje: String) : ResultAuth()
}

class AuthRepository(private val usuarioDao: UsuarioDao) {

    suspend fun login(nombre: String, contrasena: String): ResultAuth {
        val usuario = usuarioDao.login(nombre, contrasena)
        return if (usuario != null) ResultAuth.Success
        else ResultAuth.Error("Usuario o contraseña incorrectos")
    }

    suspend fun registrar(nombre: String, contrasena: String): ResultAuth {
        val existe = usuarioDao.buscarPorNombre(nombre)
        if (existe != null) return ResultAuth.Error("El usuario ya existe")
        return try {
            usuarioDao.insertar(Usuario(nombre = nombre, contrasena = contrasena))
            ResultAuth.Success
        } catch (e: Exception) {
            ResultAuth.Error("Error al registrar: ${e.message}")
        }
    }
}
