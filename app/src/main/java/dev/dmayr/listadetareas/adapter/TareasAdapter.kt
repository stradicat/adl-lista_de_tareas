package dev.dmayr.listadetareas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.dmayr.listadetareas.databinding.ItemTareaBinding
import dev.dmayr.listadetareas.model.Tarea

class TareasAdapter(
    private val listaDeTareas: MutableList<Tarea>,
    private val onCheckedChanged: (Tarea) -> Unit
) :
    RecyclerView.Adapter<TareasAdapter.TareasViewHolder>() {
    private lateinit var binding: ItemTareaBinding

    inner class TareasViewHolder(binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val descripcionTarea = binding.descripcionTarea
        val cbTareaTerminada = binding.cbTareaTerminada
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TareasViewHolder {

        binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TareasViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TareasViewHolder,
        position: Int
    ) {
        val tarea = listaDeTareas[position]
        holder.descripcionTarea.text = tarea.descripcionTarea
        holder.cbTareaTerminada.isChecked = tarea.isComplete
        holder.cbTareaTerminada.setOnCheckedChangeListener(null)
        holder.cbTareaTerminada.setOnCheckedChangeListener { _, isChecked ->
            tarea.isComplete = isChecked
            onCheckedChanged(tarea)
        }
    }

    override fun getItemCount(): Int {
        return listaDeTareas.size
    }

    fun borrarTarea(pos: Int) {
        listaDeTareas.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun borrarTodasLasTareas() {
        if (listaDeTareas.isNotEmpty()) {
            val listSize = listaDeTareas.size
            listaDeTareas.clear()
            notifyItemRangeRemoved(0, listSize)
        }
    }

    fun agregarTarea(tarea: Tarea) {
        listaDeTareas.add(0, tarea)
        notifyItemInserted(0)
    }

    fun getTareas(): List<Tarea> = listaDeTareas
}
