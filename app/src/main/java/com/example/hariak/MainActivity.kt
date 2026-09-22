package com.example.hariak

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hariak.ui.theme.HariakTheme

class Ariketa(private val numHilo: Int) :Thread() {
    override fun run() {
        Log.e("HILO", "--- ARRANCA EL HILO Nº $numHilo")
        Log.e("HILO", "Nombre:$name")
        Log.e("HILO", "Prioridad:$priority")
        Log.e("HILO", "ID:$id")
        Log.e("HILO", "Hilos activos: ${Thread.activeCount()}")
        Log.e("HILO", "--- FIN HILO :$numHilo")

    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 1..3) {
            val hilo = Ariketa(i)
            hilo.name = "Hilo-$i"
            hilo.priority = when (i){
                1 -> Thread.MIN_PRIORITY
                2 -> Thread.NORM_PRIORITY
                3 -> Thread.MAX_PRIORITY

                else -> Thread.NORM_PRIORITY
            }
            hilo.start()
        }
        Log.d(
                "MAIN",
        "Se han creado los 3 hilos. Hilos activos: ${Thread.activeCount()}"
        )

    }

}

