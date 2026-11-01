package com.example.hariak

import android.os.Handler
import android.os.Looper
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Datos que comparten las 3 máquinas.
// "private" = solo se ve dentro de este archivo.
private class Fabrica2 {
    val lock = Any()                 // el "candado" que usa synchronized
    var total = 0                    // galletas hechas entre las 3 máquinas
    @Volatile var pausado = false    // true = las máquinas esperan
    @Volatile var parado = false     // true = las máquinas terminan (Eliminar / Volver)
}

// MÉTODO 2: implementar la interfaz Runnable.
// Runnable solo describe LA TAREA (run). El hilo se crea aparte,
// pasándole este objeto al constructor de Thread.
class MaquinaRunnable(private val tarea: () -> Unit) : Runnable {
    override fun run() {
        tarea()                      // esto se ejecuta en el hilo
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Metodo2Screen(onVolver: () -> Unit) {

    // ---------- ESTADO DE LA PANTALLA ----------
    // Lista de líneas que se ven en pantalla. Compose la vigila:
    // cada vez que añadimos una línea, la pantalla se actualiza sola.
    val lineas = remember { mutableStateListOf<String>() }
    var contador by remember { mutableStateOf(0) }                 // galletas hechas
    var fabrica by remember { mutableStateOf<Fabrica2?>(null) }    // la tanda actual
    val principal = remember { Handler(Looper.getMainLooper()) }   // para hablar con la pantalla

    // ---------- FUNCIONES ----------

    // Los hilos secundarios NO pueden tocar la pantalla directamente.
    // Con esto le pedimos al hilo principal que lo haga por ellos.
    fun ui(accion: () -> Unit) {
        principal.post { accion() }
    }

    // Lo que hace cada máquina. Las 3 máquinas ejecutan esta misma función.
    fun trabajar(nombre: String, f: Fabrica2) {
        ui { if (!f.parado) lineas.add("--- ARRANCA $nombre") }

        while (!f.parado) {
            // Si está en pausa, esperamos un poco y volvemos a mirar
            if (f.pausado) {
                Thread.sleep(100)
                continue
            }

            // SECCIÓN CRÍTICA: solo una máquina a la vez entra aquí.
            // Comprobar "< 50" y sumar van juntos, así nunca se repite un número
            // ni se pasa de 50.
            var n = -1
            synchronized(f.lock) {
                if (f.total < 50) {
                    f.total++
                    n = f.total
                }
            }
            if (n == -1) break       // ya se llegó a 50: esta máquina termina

            ui {
                if (!f.parado) {
                    lineas.add("$nombre -> galleta $n")
                    contador = f.total
                }
            }
            Thread.sleep(150)        // fuera del synchronized, para no bloquear a las demás
        }

        ui { if (!f.parado) lineas.add("---- FIN $nombre") }
    }

    // Botón INICIAR: crea una tanda nueva desde 0 y arranca las 3 máquinas
    fun iniciar() {
        fabrica?.parado = true       // si había una tanda en marcha, se para
        lineas.clear()
        contador = 0

        val f = Fabrica2()
        fabrica = f

        // MÉTODO 2: creamos 3 Runnable y se los pasamos a Thread(...)
        val hilos = (1..3).map { i ->
            Thread(MaquinaRunnable { trabajar("Máquina $i", f) })
        }
        hilos.forEach { it.start() } // start() arranca los 3 a la vez

        // join(): esperar a que acaben las 3. Se hace en otro hilo para no
        // congelar la pantalla (join bloquea hasta que el hilo termina).
        Thread {
            hilos.forEach { it.join() }
            ui { if (!f.parado) lineas.add("TODAS TERMINADAS: ${f.total} galletas") }
        }.start()
    }

    // Botón ELIMINAR: para las máquinas y deja todo a 0
    fun eliminar() {
        fabrica?.parado = true
        fabrica = null
        lineas.clear()
        contador = 0
    }

    // ---------- COMPORTAMIENTO AL SALIR ----------
    BackHandler { onVolver() }                                     // botón atrás del móvil
    DisposableEffect(Unit) { onDispose { eliminar() } }            // al salir, paran las máquinas

    // ---------- PANTALLA ----------
    val listState = rememberLazyListState()
    // Cuando llega una línea nueva, la lista baja sola hasta el final
    LaunchedEffect(lineas.size) {
        if (lineas.isNotEmpty()) listState.animateScrollToItem(lineas.size - 1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Método 2: Runnable") },
                navigationIcon = { TextButton(onClick = onVolver) { Text("Volver") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(12.dp)) {
            // Los 4 botones
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(onClick = { iniciar() }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Iniciar") }
                Button(onClick = { fabrica?.pausado = true }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Pausar") }
                Button(onClick = { fabrica?.pausado = false }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Reanudar") }
                Button(onClick = { eliminar() }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(2.dp)) { Text("Eliminar") }
            }
            Spacer(Modifier.height(8.dp))
            Text("Galletas: $contador/50", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            // Lista con scroll donde van saliendo las galletas
            LazyColumn(state = listState) {
                items(lineas) { Text(it) }
            }
        }
    }
}