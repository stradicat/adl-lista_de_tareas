package dev.dmayr.listadetareas

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dev.dmayr.listadetareas.adapter.TareasAdapter
import dev.dmayr.listadetareas.databinding.ActivityMainBinding
import dev.dmayr.listadetareas.model.Tarea

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareasAdapter
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
        binding.descripcionTarea
        binding.btnAgregarTarea
        binding.btnBorrarTodasLasTareas
        binding.rvTareas
        val tvTareasPendientes = binding.tvTareasPendientes

        adapter = TareasAdapter(listaDeTareas) {
            updatecount(tvTareasPendientes)
        }
    }

    private fun updatecount(tareasPendientes: TextView) {
        val pendientes: String = adapter.getTareas().count { !it.isComplete }.toString()
        tareasPendientes.text = pendientes
    }
}
