package com.example.noticoin.data.repository

import com.example.noticoin.data.local.dao.CryptoDao
import com.example.noticoin.data.local.dao.NotificacionDao
import com.example.noticoin.data.local.entities.Crypto
import com.example.noticoin.data.local.entities.Notificacion
import com.example.noticoin.data.remote.CoinGeckoApi
import com.example.noticoin.data.remote.models.CryptoResponse
import kotlinx.coroutines.flow.Flow

sealed class ResultData<out T> {
    data class Success<T>(val data: T) : ResultData<T>()
    data class Error(val mensaje: String) : ResultData<Nothing>()
    object Loading : ResultData<Nothing>()
}

class CryptoRepository(
    private val cryptoDao: CryptoDao,
    private val notificacionDao: NotificacionDao,
    private val api: CoinGeckoApi
) {
    suspend fun obtenerMercado(): ResultData<List<CryptoResponse>> {
        return try {
            val lista = api.obtenerMercado()
            ResultData.Success(lista)
        } catch (e: Exception) {
            ResultData.Error(e.message ?: "Error de red")
        }
    }

    fun obtenerFavoritas(): Flow<List<Crypto>> = cryptoDao.obtenerTodas()

    suspend fun agregarFavorita(crypto: Crypto) = cryptoDao.insertar(crypto)

    suspend fun eliminarFavorita(idCripto: String) = cryptoDao.eliminar(idCripto)

    suspend fun esFavorita(idCripto: String): Boolean = cryptoDao.buscarPorId(idCripto) != null

    fun obtenerNotificaciones(): Flow<List<Notificacion>> = notificacionDao.obtenerTodas()

    suspend fun agregarNotificacion(notificacion: Notificacion) =
        notificacionDao.insertar(notificacion)

    suspend fun eliminarNotificacion(id: Int) = notificacionDao.eliminar(id)
}
