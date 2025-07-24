package dev.dmayr.listadetareas.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import dev.dmayr.listadetareas.model.Tarea

@Dao
interface TareaDao {

    suspend fun agregarTarea(tarea: Tarea)

    suspend fun modificarTarea(tarea: Tarea)

    suspend fun eliminarTarea(tarea: Tarea)

    suspend fun mostrarTareas(): LiveData<Tarea>
}
