package com.example.noticoin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noticoin.data.local.dao.CryptoDao
import com.example.noticoin.data.local.dao.NotificacionDao
import com.example.noticoin.data.local.dao.UsuarioDao
import com.example.noticoin.data.local.entities.Crypto
import com.example.noticoin.data.local.entities.Notificacion
import com.example.noticoin.data.local.entities.Usuario

@Database(
    entities = [Usuario::class, Crypto::class, Notificacion::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun cryptoDao(): CryptoDao
    abstract fun notificacionDao(): NotificacionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "noticoin_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
