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
import kotlinx.coroutines.Runnable
import java.lang.Thread.sleep


@Composable
fun Metodo6(onVolver: () -> Unit) {
    val lineas = remember { mutableStateListOf<String>() }
    val lock = Any()
    var contador = 0

    for (h in 1..3) {
        var numhilo = h
        val hilo = Thread {
            while (true) {
                var numg2: Int

                synchronized(lock) {
                    if (contador >= 50) return@synchronized
                    contador ++
                    numg2 = contador

                    if (numg2 == 1) {
                        lineas.add("Empecamos")
                    }

                    lineas.add("Fbarica $numhilo galleta nuemor $numg2")

                    if (numg2 == 50) {
                        lineas.add("hemoss llegado a 50")
                    }

                }


            }
        }
        hilo.start()
    }


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
        Column(
            Modifier
                .padding(padding)
                .padding(12.dp)
        ) {
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

