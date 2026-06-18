package com.example.noticoin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noticoin.data.local.entities.Notificacion
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificacionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(notificacion: Notificacion)

    @Query("DELETE FROM notificacion WHERE idNotificacion = :id")
    suspend fun eliminar(id: Int)

    @Query("SELECT * FROM notificacion ORDER BY idNotificacion DESC")
    fun obtenerTodas(): Flow<List<Notificacion>>
}
