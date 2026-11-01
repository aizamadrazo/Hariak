package com.example.hariak

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Datos que comparten las 3 máquinas
class Fabrica {
    val lock = Any()
    var total = 0
    @Volatile var pausado = false
    @Volatile var parado = false
}

// Estado de la pantalla + lógica que hacen las 3 máquinas
class GalletasState(private val activity: Activity) {
    val lineas = mutableStateListOf<String>()   // lo que se ve en pantalla
    var contador by mutableStateOf(0)
    var fabrica: Fabrica? = null

    fun ui(accion: () -> Unit) {
        activity.runOnUiThread { accion() }
    }

    fun nueva(): Fabrica {
        fabrica?.parado = true                  // por si había una en marcha
        lineas.clear()
        contador = 0
        val f = Fabrica()
        fabrica = f
        return f
    }

    fun pausar() { fabrica?.pausado = true }
    fun reanudar() { fabrica?.pausado = false }

    fun eliminar() {
        fabrica?.parado = true
        fabrica = null
        lineas.clear()
        contador = 0
    }

    // Lo que hace cada máquina (las 3 llaman a esta función)
    fun trabajar(nombre: String, f: Fabrica) {
        ui { if (!f.parado) lineas.add("--- ARRANCA $nombre") }

        while (!f.parado) {
            if (f.pausado) {
                Thread.sleep(100)
                continue
            }

            var n = -1
            synchronized(f.lock) {              // sección crítica
                if (f.total < 50) {
                    f.total++
                    n = f.total
                }
            }
            if (n == -1) break                  // ya hay 50: parar

            ui {
                if (!f.parado) {
                    lineas.add("$nombre -> galleta $n")
                    contador = f.total
                }
            }
            Thread.sleep(150)                   // fuera del synchronized
        }

        ui { if (!f.parado) lineas.add("---- FIN $nombre") }
    }

    // join: esperar a las 3 en otro hilo para no congelar la pantalla
    fun esperarFin(hilos: List<Thread>, f: Fabrica) {
        Thread {
            hilos.forEach { it.join() }
            ui { if (!f.parado) lineas.add("TODAS TERMINADAS: ${f.total} galletas") }
        }.start()
    }
}

// La pantalla con los 4 botones, el contador y la lista
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGalletas(
    titulo: String,
    estado: GalletasState,
    onVolver: () -> Unit,
    onIniciar: () -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(estado.lineas.size) {
        if (estado.lineas.isNotEmpty()) listState.animateScrollToItem(estado.lineas.size - 1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = { TextButton(onClick = onVolver) { Text("Volver") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(onClick = onIniciar, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Iniciar") }
                Button(onClick = { estado.pausar() }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Pausar") }
                Button(onClick = { estado.reanudar() }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Reanudar") }
                Button(onClick = { estado.eliminar() }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Eliminar") }
            }
            Spacer(Modifier.height(8.dp))
            Text("Galletas: ${estado.contador}/50", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            LazyColumn(state = listState) {
                items(estado.lineas) { Text(it) }
            }
        }
    }
}