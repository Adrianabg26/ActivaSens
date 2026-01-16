package com.example.ActivaSens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ActivaSens.databinding.ActivityItemBinding
import com.example.ActivaSens.model.ActivitySession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Adaptador para gestionar la lista de sesiones de actividad en el RecyclerView.
 * Se encarga de convertir los objetos de datos (ActivitySession) en elementos visuales.
 */
class ActivitySessionAdapter : RecyclerView.Adapter<ActivitySessionAdapter.SessionViewHolder>() {

    // Lista mutable que contiene los datos a mostrar
    private var sessionList = mutableListOf<ActivitySession>()

    /**
     * Actualiza los datos del adaptador. Al usar notifyDataSetChanged(),
     * indicamos al RecyclerView que debe redibujar toda la lista.
     */
    fun submitList(newList: List<ActivitySession>) {
        sessionList.clear()
        sessionList.addAll(newList)
        notifyDataSetChanged()
    }

    /** Infla el layout de cada item (activity_item.xml) usando ViewBinding.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ActivityItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SessionViewHolder(binding)
    }

    /**
     * Une los datos de una posición específica de la lista con el ViewHolder correspondiente.
     */
    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(sessionList[position])
    }

    /**
     * Indica al RecyclerView el número total de elementos que debe renderizar.
     * Basado en el tamaño de la colección 'sessionList'.
     */
    override fun getItemCount(): Int = sessionList.size

    /**
     * Clase interna ViewHolder que mantiene las referencias a las vistas de cada fila.
     * Esto mejora el rendimiento al evitar buscar las vistas repetidamente.
     */
    class SessionViewHolder(private val binding: ActivityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Vincula los datos de un objeto ActivitySession con los componentes de la interfaz.
         */
        fun bind(session: ActivitySession) {
            // Asigna el nombre de la sesión directamente al TextView
            binding.tvName.text = session.name
            // Muestra la duración formateada.
            binding.tvDuration.text = "Duración: ${session.durationMinutes} min"
            // Indica el tipo de actividad realizada
            binding.tvType.text = "Tipo: ${session.type}"

            /**
             * Formateo de fecha: Convertimos el timestamp (Long) a un formato legible.
             * Usamos Locale.getDefault() para respetar la configuración de idioma del móvil.
             */
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateStr = sdf.format(Date(session.dateTimeMillis))
            binding.tvDate.text = "Fecha/hora: $dateStr"
        }
    }
}

