package dev.dmayr.listadetareas.repository

import androidx.lifecycle.LiveData
import dev.dmayr.listadetareas.data.TareaDao
import dev.dmayr.listadetareas.model.Tarea

class TareaRepositorio(private val tareaDao: TareaDao) {

    val mostrarTareas: LiveData<List<Tarea>> = tareaDao.mostrarTareas()

    suspend fun modificarTarea(tarea: Tarea) {
        tareaDao.modificarTarea(tarea)
    }

    suspend fun eliminarTarea(tarea: Tarea) {
        tareaDao.eliminarTarea(tarea)
    }

    suspend fun eliminarTodasLasTareas() {
        tareaDao.eliminarTodasLasTareas()
    }
}
