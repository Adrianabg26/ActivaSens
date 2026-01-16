package com.example.ActivaSens.model

//Clase de datos que representa una sesión de actividad física
data class ActivitySession(
    val name: String, // Nombre de la actividad
    val durationMinutes: Int, // Duración en minutos
    val dateTimeMillis: Long, // Fecha guardada como número
    val type: String // Tipo de actividad
)