package com.example.noticoin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticoin.data.local.AppDatabase
import com.example.noticoin.data.local.entities.Crypto
import com.example.noticoin.data.local.entities.Notificacion
import com.example.noticoin.data.remote.CoinGeckoApi
import com.example.noticoin.data.repository.CryptoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificacionesViewModel(application: Application) : AndroidViewModel(application) {

    //cambios
    private val repo: CryptoRepository by lazy {
        val db = AppDatabase.getInstance(application)
        CryptoRepository(db.cryptoDao(), db.notificacionDao(), CoinGeckoApi.crear())
    }

    val notificaciones: StateFlow<List<Notificacion>> = repo.obtenerNotificaciones()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoritas: StateFlow<List<Crypto>> = repo.obtenerFavoritas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarNotificacion(idCripto: String, descripcion: String) {
        if (idCripto.isBlank() || descripcion.isBlank()) return
        viewModelScope.launch {
            val fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            repo.agregarNotificacion(
                Notificacion(
                    fecha = fecha,
                    idCripto = idCripto,
                    descripcion = descripcion
                )
            )
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            repo.eliminarNotificacion(id)
        }
    }
}
