package dev.dmayr.listadetareas

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import dev.dmayr.listadetareas.adapter.TareasAdapter
import dev.dmayr.listadetareas.databinding.ActivityMainBinding
import dev.dmayr.listadetareas.model.Tarea
import dev.dmayr.listadetareas.utils.SwipeToDeleteCallback

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PREFERENCIAS = "prefs_lista_de_tareas"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var tareasAdapter: TareasAdapter
    private val listaDeTareas: MutableList<Tarea> = mutableListOf()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(
            PREFERENCIAS, MODE_PRIVATE
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /*-----------------------------------------------------------------------*/
        val descripcionTarea = binding.descripcionTarea
        val btnAgregarTarea = binding.btnAgregarTarea
        val btnBorrarTodasLasTareas = binding.btnBorrarTodasLasTareas
        val rvTareas = binding.rvTareas
        val tvTareasPendientes = binding.tvTareasPendientes

        tareasAdapter = TareasAdapter(listaDeTareas) {
            updatecount(tvTareasPendientes)
        }

        leerTodasLasTareas()
        updatecount(tvTareasPendientes)

        val handleElementSwipes = SwipeToDeleteCallback { pos ->
            tareasAdapter.borrarTarea(pos)
            borrarTarea(pos)
            updatecount(tvTareasPendientes)
        }
        rvTareas.layoutManager = LinearLayoutManager(this)
        rvTareas.adapter = tareasAdapter

        ItemTouchHelper(handleElementSwipes).attachToRecyclerView(rvTareas)

        btnAgregarTarea.setOnClickListener {
            val texto = descripcionTarea.text.toString().trim()
            if (texto.isNotEmpty()) {
                val nuevaTarea = Tarea(System.currentTimeMillis(), texto)
                tareasAdapter.agregarTarea(nuevaTarea)
                guardarTarea(nuevaTarea)
                descripcionTarea.text.clear()
                updatecount(tvTareasPendientes)
            }
        }

        btnBorrarTodasLasTareas.setOnClickListener {
            tareasAdapter.borrarTodasLasTareas()
            borrarTodasLasTareas()
            updatecount(tvTareasPendientes)
        }
    }

    private fun guardarTarea(nuevaTarea: Tarea) {
        val tareaKey = nuevaTarea.id.toString()
        sharedPreferences.edit {
            putLong("${tareaKey}_id", nuevaTarea.id)
                .putString("${tareaKey}_descripcion", nuevaTarea.descripcionTarea)
                .putBoolean("${tareaKey}_isComplete", nuevaTarea.isComplete)
        }
    }

    private fun borrarTarea(pos: Int) {
        if (pos in listaDeTareas.indices) {
            val tareaKey = listaDeTareas[pos].id.toString()
            sharedPreferences.edit {
                remove("${tareaKey}_id")
                remove("${tareaKey}_descripcion")
                remove("${tareaKey}_isComplete")
            }
        }
    }

    private fun borrarTodasLasTareas() {
        sharedPreferences.edit { clear() }
    }

    private fun leerTodasLasTareas() {
        listaDeTareas.clear()

        val todasLasPreferencias = sharedPreferences.all
        val tareaIds = todasLasPreferencias.keys
            .filter { it.endsWith("_id") }
            .map { it.removeSuffix("_id") }
            .distinct()

        tareaIds.forEach { idStr ->
            val id = sharedPreferences.getLong("${idStr}_id", -1)
            val descripcion = sharedPreferences.getString("${idStr}_descripcion", null)
            val isComplete = sharedPreferences.getBoolean("${idStr}_isComplete", false)

            if (id != (-1).toLong() && descripcion != null) {
                val tarea = Tarea(id, descripcion, System.currentTimeMillis(), isComplete)
                listaDeTareas.add(tarea)
            }
        }
    }

    private fun updatecount(tareasPendientes: TextView) {
        val pendientes: String = tareasAdapter.getTareas().count { !it.isComplete }.toString()
        tareasPendientes.text = pendientes
    }
}
