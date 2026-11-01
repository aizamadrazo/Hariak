package com.example.hariak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class Metodo1Activity : ComponentActivity() {

    private val estado = GalletasState(this)

    // MÉTODO 1: subclase de Thread, sobrescribe run()
    inner class MaquinaThread(private val nombre: String, private val f: Fabrica) : Thread() {
        override fun run() {
            estado.trabajar(nombre, f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaGalletas(
                    titulo = "Método 1: Thread",
                    estado = estado,
                    onVolver = { finish() },
                    onIniciar = { iniciar() }
                )
            }
        }
    }

    private fun iniciar() {
        val f = estado.nueva()
        val hilos = (1..3).map { MaquinaThread("Máquina $it", f) }
        hilos.forEach { it.start() }
        estado.esperarFin(hilos, f)
    }

    override fun onDestroy() {
        estado.eliminar()          // al salir, las máquinas se paran
        super.onDestroy()
    }
}