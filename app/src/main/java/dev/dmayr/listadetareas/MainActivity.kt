package dev.dmayr.listadetareas

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var binding: ActivityMainBinding
    private lateinit var tareasAdapter: TareasAdapter
    private val listaDeTareas: MutableList<Tarea> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val handleElementSwipes = SwipeToDeleteCallback { pos ->
            tareasAdapter.borrarTarea(pos)
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
                descripcionTarea.text.clear()
                updatecount(tvTareasPendientes)
            }
        }

        btnBorrarTodasLasTareas.setOnClickListener {
            tareasAdapter.borrarTodasLasTareas()
            updatecount(tvTareasPendientes)
        }
    }

    private fun updatecount(tareasPendientes: TextView) {
        val pendientes: String = tareasAdapter.getTareas().count { !it.isComplete }.toString()
        tareasPendientes.text = pendientes
    }
}
