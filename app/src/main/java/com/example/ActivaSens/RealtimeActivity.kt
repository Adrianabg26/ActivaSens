package com.example.ActivaSens

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ActivaSens.databinding.ActivityRealtimeBinding
import kotlin.math.sqrt


/**
 * Actividad que monitoriza el acelerómetro en tiempo real.
 */
class RealtimeActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        // Constantes para recuperar datos del Intent de forma segura
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var binding: ActivityRealtimeBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // Variable para aplicar un filtro de suavizado
    private var smoothMagnitude = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * ViewBinding: Genera el objeto 'binding' inflando el layout XML (activity_realtime.xml).
         * Esto permite acceder a las vistas (botones, textos) de forma segura sin usar findViewById.
         */
        binding = ActivityRealtimeBinding.inflate(layoutInflater)

        /**
         * Establece la vista raíz del objeto binding como el contenido de la actividad.
         * binding.root hace referencia al contenedor principal del archivo XML.
         */
        setContentView(binding.root)

        // Inicialización del servicio de sensores del sistema
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        binding.btnFinalizar.setOnClickListener { finish() }
    }

    /**
     * Se ejecuta cada vez que el sensor detecta un cambio en los ejes X, Y o Z.
     */
    override fun onSensorChanged(evento: SensorEvent) {

        val x = evento.values[0]
        val y = evento.values[1]
        val z = evento.values[2]

        // Cálculo de la norma del vector (magnitud total de la aceleración)
        val magnitude = sqrt(x * x + y * y + z * z).toDouble().toFloat()

        // Restamos la gravedad terrestre (aprox 9.81) para obtener solo el movimiento del usuario
        val delta = kotlin.math.abs(magnitude - 9.81f)

        // Filtro para evitar saltos bruscos en la UI: 80% valor anterior, 20% valor nuevo
        smoothMagnitude = smoothMagnitude * 0.8f + delta * 0.2f


        updateUiByMotion(smoothMagnitude)

    }

    /**
     * Actualiza el estado visual según la intensidad del movimiento detectado.
     */
    private fun updateUiByMotion(value: Float) {
        val (text, color) = when {
            value < 0.6f -> "Sin movimiento" to Color.parseColor("#D3D3D3")
            value < 1.8f -> "Movimiento suave" to Color.parseColor("#ADD8E6")
            else -> "Movimiento intenso" to Color.parseColor("#FFFF00")
        }

        binding.tvStatus.text = text
        binding.layoutRealtime.setBackgroundColor(color)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    /**
     * IMPORTANTE: registrar el listener en onResume para que el sensor
     * solo funcione cuando la actividad está visible.
     */
    override fun onResume() {
        super.onResume()

        accelerometer?.let { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI // Frecuencia de actualización apta para interfaces
            )
        }
    }

    /**
     * IMPORTANTE: Desregistrar el sensor en onPause para ahorrar batería
     */
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
