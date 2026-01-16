ActivaSens - Registro de Actividad Física

Este proyecto es una aplicación Android desarrollada en Kotlin para la asignatura de Progrmación Multimedia y Aplicaciones móviles. La app permite gestionar un histórico de entrenamientos y monitorizar el movimiento en tiempo real mediante los sensores del dispositivo.

## Características principales
- Registro de sesiones: Formulario para introducir nombre, duración y tipo de actividad con validación de datos para evitar errores.
- Historial dinámico: Uso de un RecyclerView para mostrar todas las sesiones guardadas de forma eficiente.
- Modo Sensor: Pantalla secundaria que utiliza el acelerómetro para detectar la intensidad del movimiento y cambiar el estado visual de la app.

## Tecnologías y conceptos aplicados
- Arquitectura: Patrón Adapter + ViewHolder para la gestión de listas.
- Interfaz: Uso de Material Design (Cards, TextFields, Buttons) y ViewBinding para conectar el XML con Kotlin.
- Componentes: Manejo del ciclo de vida de los sensores (onResume, onPause) y navegación mediante Intents.
- Modelo de datos: Clase personalizada ActivitySession para estructurar la información de cada entrenamiento.
  
