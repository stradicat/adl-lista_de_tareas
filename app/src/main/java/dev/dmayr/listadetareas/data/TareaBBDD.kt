package dev.dmayr.listadetareas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.dmayr.listadetareas.model.Tarea

@Database([Tarea::class], version = 1, exportSchema = false)
abstract class TareaBBDD : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile
        private var INSTANCIA_BBDD: TareaBBDD? = null

        fun instanciarBBDD(context: Context): TareaBBDD {
            val tempInstancia = INSTANCIA_BBDD
            if (tempInstancia != null) {
                return tempInstancia
            }
            synchronized(this) {
                val instanciaDeBBDD = Room.databaseBuilder(
                    context.applicationContext,
                    TareaBBDD::class.java,
                    "lista_de_tareas"
                ).build()
                INSTANCIA_BBDD = instanciaDeBBDD
                return instanciaDeBBDD
            }
        }
    }
}
