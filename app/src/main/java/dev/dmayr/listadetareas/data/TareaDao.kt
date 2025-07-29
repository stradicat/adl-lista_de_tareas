package dev.dmayr.listadetareas.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.dmayr.listadetareas.model.Tarea

@Dao
interface TareaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun agregarTarea(tarea: Tarea)

    @Update
    suspend fun modificarTarea(tarea: Tarea)

    @Delete
    suspend fun eliminarTarea(tarea: Tarea)

    @Query("DELETE from lista_de_tareas")
    suspend fun eliminarTodasLasTareas()

    @Query("SELECT * from lista_de_tareas ORDER BY id ASC")
    fun mostrarTareas(): LiveData<List<Tarea>>
}
