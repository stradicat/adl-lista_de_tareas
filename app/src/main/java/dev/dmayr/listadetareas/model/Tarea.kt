package dev.dmayr.listadetareas.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("lista_de_tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val descripcionTarea: String,
    val fechaDeCreacion: Long = System.currentTimeMillis(),
    var isComplete: Boolean = false
) : Parcelable
