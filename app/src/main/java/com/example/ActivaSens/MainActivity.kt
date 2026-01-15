package com.example.ActivaSens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ActivaSens.databinding.ActivityMainBinding
import com.example.ActivaSens.model.ActivitySession

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = ActivitySessionAdapter()
    private val sessions = mutableListOf<ActivitySession>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSessions.layoutManager = LinearLayoutManager(this)
        binding.rvSessions.adapter = adapter

        adapter.submitList(sessions.toList())

        binding.btnSave.setOnClickListener { onSaveClicked() }
        binding.btnRealtime.setOnClickListener {
            val intent = Intent(this, RealtimeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onSaveClicked() {
        val name = binding.etName.text.toString().trim()
        val durationStr = binding.etDuration.text.toString().trim()
        val type = binding.etType.text.toString().trim().ifEmpty { "General" }


        if (name.isEmpty()) {
            Toast.makeText(this, "Escribe el nombre de la actividad.", Toast.LENGTH_SHORT).show()
            return
        }

        val duration = durationStr.toIntOrNull()
        if (duration == null || duration <= 0) {
            Toast.makeText(this, "La duración debe ser un número mayor que 0.", Toast.LENGTH_SHORT).show()
            return
        }


        val session = ActivitySession(
            name = name,
            durationMinutes = duration,
            dateTimeMillis = System.currentTimeMillis(),
            type = type
        )


        sessions.add(0, session)
        adapter.submitList(sessions.toList())

        Toast.makeText(this, "Actividad guardada.", Toast.LENGTH_SHORT).show()


        binding.etName.text?.clear()
        binding.etDuration.text?.clear()
        binding.etType.text?.clear()
    }



}
