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


class RealtimeActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var binding: ActivityRealtimeBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var smoothMagnitude = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        binding.btnFinalizar.setOnClickListener { finish() }
    }

    override fun onSensorChanged(evento: SensorEvent) {

        val x = evento.values[0]
        val y = evento.values[1]
        val z = evento.values[2]

        val magnitude = sqrt(x * x + y * y + z * z).toDouble().toFloat()
        val delta = kotlin.math.abs(magnitude - 9.81f)
        smoothMagnitude = smoothMagnitude * 0.8f + delta * 0.2f
        updateUiByMotion(smoothMagnitude)

    }

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

    override fun onResume() {
        super.onResume()

        accelerometer?.let { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
