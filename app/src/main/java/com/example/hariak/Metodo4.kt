package com.example.hariak

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.lang.Thread.sleep

class Contador(var valor: Int = 0)
class Hilo(
    nombre: String,
    private val lock: Any,
    private val contador: Contador,
    private val lineas: MutableList<String>
) : Thread(nombre) {

    override fun run() {
        while (true) {
            var numg2: Int

            synchronized(lock) {
                if (contador.valor >= 50) return
                contador.valor++
                numg2 = contador.valor

                if (numg2 == 1) {
                    lineas.add("Empecamos")
                }

                lineas.add("Fbarica $name galleta nuemor $numg2")

                if (numg2 == 50) {
                    lineas.add("hemoss llegado a 50")
                }

            }


        }
    }
}

@Composable
fun Metodo4(onVolver: () -> Unit) {
    val lineas = remember { mutableStateListOf<String>() }
    val contador = Contador()
    val lock = Any()
    lineas.add("Empezamossss")
    val hilo1 = Hilo("hilo1",lock, contador, lineas)
    val hilo2 = Hilo("hilo2",lock, contador, lineas)
    val hilo3 = Hilo("hilo3",lock, contador, lineas)

        hilo1.start()
        hilo2.start()
        hilo3.start()


    // ---------- PANTALLA ----------
    val listState = rememberLazyListState()
    // Cuando llega una línea nueva, la lista baja sola hasta el final
    LaunchedEffect(lineas.size) {
        if (lineas.isNotEmpty()) listState.animateScrollToItem(lineas.size - 1)
    }

    Scaffold(
        topBar = {

        }
    ) { padding ->
        Column(Modifier
            .padding(padding)
            .padding(12.dp)) {
            // Los 4 botones

            Spacer(Modifier.height(8.dp))
            //Text("Galletas: $contador/50", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            // Lista con scroll donde van saliendo las galletas
            LazyColumn(state = listState) {
                items(lineas) { Text(it) }
            }
        }
    }
}

