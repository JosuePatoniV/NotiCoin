package com.example.noticoin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticoin.data.local.AppDatabase
import com.example.noticoin.data.local.entities.Crypto
import com.example.noticoin.data.remote.CoinGeckoApi
import com.example.noticoin.data.remote.models.CryptoResponse
import com.example.noticoin.data.repository.CryptoRepository
import com.example.noticoin.data.repository.ResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MercadoState {
    object Loading : MercadoState()
    data class Success(val lista: List<CryptoResponse>) : MercadoState()
    data class Error(val mensaje: String) : MercadoState()
}

class MercadoViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: CryptoRepository by lazy {
        val db = AppDatabase.getInstance(application)
        CryptoRepository(db.cryptoDao(), db.notificacionDao(), CoinGeckoApi.crear())
    }

    private val _estado = MutableStateFlow<MercadoState>(MercadoState.Loading)
    val estado: StateFlow<MercadoState> = _estado

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    init {
        cargarMercado()
    }

    fun cargarMercado() {
        viewModelScope.launch {
            _estado.value = MercadoState.Loading
            _estado.value = when (val r = repo.obtenerMercado()) {
                is ResultData.Success -> MercadoState.Success(r.data)
                is ResultData.Error -> MercadoState.Error(r.mensaje)
                is ResultData.Loading -> MercadoState.Loading
            }
        }
    }

    fun agregarFavorita(item: CryptoResponse) {
        viewModelScope.launch {
            val esFav = repo.esFavorita(item.id)
            if (esFav) {
                _mensaje.value = "${item.name} ya está en favoritas"
            } else {
                repo.agregarFavorita(Crypto(idCripto = item.id, nombre = item.name))
                _mensaje.value = "${item.name} agregada a favoritas"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}
