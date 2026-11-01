package com.example.hariak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class Metodo2Activity : ComponentActivity() {

    private val estado = GalletasState(this)

    // MÉTODO 2: clase que implementa la interfaz Runnable
    inner class MaquinaRunnable(private val nombre: String, private val f: Fabrica) : Runnable {
        override fun run() {
            estado.trabajar(nombre, f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaGalletas(
                    titulo = "Método 2: Runnable",
                    estado = estado,
                    onVolver = { finish() },
                    onIniciar = { iniciar() }
                )
            }
        }
    }

    private fun iniciar() {
        val f = estado.nueva()
        val hilos = (1..3).map { Thread(MaquinaRunnable("Máquina $it", f)) }
        hilos.forEach { it.start() }
        estado.esperarFin(hilos, f)
    }

    override fun onDestroy() {
        estado.eliminar()
        super.onDestroy()
    }
}