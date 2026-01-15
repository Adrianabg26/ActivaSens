package com.example.ActivaSens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ActivaSens.databinding.ActivityItemBinding
import com.example.ActivaSens.model.ActivitySession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivitySessionAdapter : RecyclerView.Adapter<ActivitySessionAdapter.SessionViewHolder>() {
    private var sessionList = mutableListOf<ActivitySession>()
    fun submitList(newList: List<ActivitySession>) {
        sessionList.clear()
        sessionList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ActivityItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(sessionList[position])
    }

    override fun getItemCount(): Int = sessionList.size
    class SessionViewHolder(private val binding: ActivityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(session: ActivitySession) {
            binding.tvName.text = session.name
            binding.tvDuration.text = "Duración: ${session.durationMinutes} min"
            binding.tvType.text = "Tipo: ${session.type}"


            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateStr = sdf.format(Date(session.dateTimeMillis))
            binding.tvDate.text = "Fecha/hora: $dateStr"
        }
    }
}

