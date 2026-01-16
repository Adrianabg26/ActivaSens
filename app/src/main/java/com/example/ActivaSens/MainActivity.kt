package com.example.ActivaSens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ActivaSens.databinding.ActivityMainBinding
import com.example.ActivaSens.model.ActivitySession

class MainActivity : AppCompatActivity() {

    // ViewBinding para acceso seguro a los componentes del XML
    private lateinit var binding: ActivityMainBinding
    // Instancia única del adaptador para el RecyclerView
    private val adapter = ActivitySessionAdapter()
    // Fuente de datos: Lista de sesiones en memoria
    private val sessions = mutableListOf<ActivitySession>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Configuración del RecyclerView:
         * 1. Definimos un LinearLayoutManager (diseño de lista vertical estándar).
         * 2. Vinculamos el adaptador que gestionará los items.
         */
        binding.rvSessions.layoutManager = LinearLayoutManager(this)
        binding.rvSessions.adapter = adapter

        // Inicializamos la lista (vacía al principio)
        adapter.submitList(sessions.toList())

        // Listener para el guardado manual de datos
        binding.btnSave.setOnClickListener { onSaveClicked() }

        // Navegación hacia la actividad de sensores mediante un Intent
        binding.btnRealtime.setOnClickListener {
            val intent = Intent(this, RealtimeActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Lógica de captura y validación de datos del formulario.
     */
    private fun onSaveClicked() {
        // Extraemos valores eliminando espacios en blanco innecesarios
        val name = binding.etName.text.toString().trim()
        val durationStr = binding.etDuration.text.toString().trim()
        val type = binding.etType.text.toString().trim().ifEmpty { "General" }

        // Validación de campo obligatorio (Nombre)
        if (name.isEmpty()) {
            Toast.makeText(this, "Escribe el nombre de la actividad.", Toast.LENGTH_SHORT).show()
            return
        }

        // Validación y conversión segura de String a Int
        val duration = durationStr.toIntOrNull()
        if (duration == null || duration <= 0) {
            Toast.makeText(this, "La duración debe ser un número mayor que 0.", Toast.LENGTH_SHORT).show()
            return
        }

        /**
         * Creación del objeto de datos.
         * Se usa System.currentTimeMillis() para capturar el instante exacto del guardado.
         */
        val session = ActivitySession(
            name = name,
            durationMinutes = duration,
            dateTimeMillis = System.currentTimeMillis(),
            type = type
        )

        /**
         * Actualización de la UI:
         * Añadimos la nueva sesión al principio (índice 0) para que aparezca arriba.
         * Pasamos una copia (.toList()) al adaptador para asegurar la integridad de los datos.
         */
        sessions.add(0, session)
        adapter.submitList(sessions.toList())

        Toast.makeText(this, "Actividad guardada.", Toast.LENGTH_SHORT).show()

        // Limpieza de campos para facilitar una nueva entrada
        binding.etName.text?.clear()
        binding.etDuration.text?.clear()
        binding.etType.text?.clear()
    }



}
