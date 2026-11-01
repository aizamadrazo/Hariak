package com.example.hariak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class Metodo3Activity : ComponentActivity() {

    private val estado = GalletasState(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaGalletas(
                    titulo = "Método 3: lambda",
                    estado = estado,
                    onVolver = { finish() },
                    onIniciar = { iniciar() }
                )
            }
        }
    }

    private fun iniciar() {
        val f = estado.nueva()

        // MÉTODO 3: lambda directamente en el constructor de Thread
        val hilos = (1..3).map { i ->
            Thread { estado.trabajar("Máquina $i", f) }
        }
        hilos.forEach { it.start() }
        estado.esperarFin(hilos, f)
    }

    override fun onDestroy() {
        estado.eliminar()
        super.onDestroy()
    }
}