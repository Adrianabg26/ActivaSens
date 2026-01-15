package com.example.ActivaSens.model

data class ActivitySession(
    val name: String, // Nombre de la actividad
    val durationMinutes: Int, // Duración en minutos
    val dateTimeMillis: Long, // Fecha guardada como número
    val type: String // Tipo de actividad
)