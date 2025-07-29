package dev.dmayr.listadetareas.model

data class Tarea(
    val id: Long,
    val descripcionTarea: String,
    val fechaDeCreacion: Long = System.currentTimeMillis(),
    var isComplete: Boolean = false
)
