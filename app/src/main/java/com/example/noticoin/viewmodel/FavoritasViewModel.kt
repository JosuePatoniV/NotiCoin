package com.example.noticoin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticoin.data.local.AppDatabase
import com.example.noticoin.data.local.entities.Crypto
import com.example.noticoin.data.remote.CoinGeckoApi
import com.example.noticoin.data.repository.CryptoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritasViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: CryptoRepository by lazy {
        val db = AppDatabase.getInstance(application)
        CryptoRepository(db.cryptoDao(), db.notificacionDao(), CoinGeckoApi.crear())
    }

    val favoritas: StateFlow<List<Crypto>> = repo.obtenerFavoritas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun eliminar(idCripto: String) {
        viewModelScope.launch {
            repo.eliminarFavorita(idCripto)
        }
    }
}
