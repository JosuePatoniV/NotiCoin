package com.example.noticoin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noticoin.data.local.entities.Crypto
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(crypto: Crypto)

    @Query("DELETE FROM crypto WHERE idCripto = :idCripto")
    suspend fun eliminar(idCripto: String)

    @Query("SELECT * FROM crypto")
    fun obtenerTodas(): Flow<List<Crypto>>

    @Query("SELECT * FROM crypto WHERE idCripto = :idCripto LIMIT 1")
    suspend fun buscarPorId(idCripto: String): Crypto?
}
