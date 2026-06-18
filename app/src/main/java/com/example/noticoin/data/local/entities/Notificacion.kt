package com.example.noticoin.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notificacion",
    foreignKeys = [
        ForeignKey(
            entity = Crypto::class,
            parentColumns = ["idCripto"],
            childColumns = ["idCripto"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Notificacion(
    @PrimaryKey(autoGenerate = true) val idNotificacion: Int = 0,
    val fecha: String,
    val idCripto: String,
    val descripcion: String
)
