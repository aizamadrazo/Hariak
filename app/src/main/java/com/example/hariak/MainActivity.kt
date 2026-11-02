package com.example.hariak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Las pantallas que existen
enum class Destino { MENU, METODO1, METODO2, METODO3,METODO4,METODO5,METODO6 }

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                var destino by remember { mutableStateOf(Destino.MENU) }

                // según el valor de "destino" se dibuja una pantalla u otra
                when (destino) {
                    Destino.MENU -> Menu { destino = it }
                    Destino.METODO1 -> Metodo1Screen { destino = Destino.MENU }
                    Destino.METODO2 -> Metodo2Screen { destino = Destino.MENU }
                    Destino.METODO3 -> Metodo3Screen { destino = Destino.MENU }
                    Destino.METODO4 -> Metodo4 { destino = Destino.MENU }
                    Destino.METODO5 -> Metodo5 { destino = Destino.MENU }
                    Destino.METODO6 -> Metodo6 { destino = Destino.MENU }



                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(onElegir: (Destino) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Hilos: fábrica de galletas") }) }) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(24.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { onElegir(Destino.METODO1) }, modifier = Modifier.fillMaxWidth()) {
                Text("Método 1: Thread")
            }
            Button(onClick = { onElegir(Destino.METODO2) }, modifier = Modifier.fillMaxWidth()) {
                Text("Método 2: Runnable")
            }
            Button(onClick = { onElegir(Destino.METODO3) }, modifier = Modifier.fillMaxWidth()) {
                Text("Método 3: lambda")
            }
            Button(onClick = { onElegir(Destino.METODO4) }, modifier = Modifier.fillMaxWidth()) {
                Text("Método 4: methodo 1")
            }
            Button(onClick = { onElegir(Destino.METODO5) }, modifier = Modifier.fillMaxWidth()) {
                Text("Método 5: mthodo 2")
            }
            Button(onClick = { onElegir(Destino.METODO6) }, modifier = Modifier.fillMaxWidth()) {
                Text("Método 6: mthodo 3")
            }
        }
    }
}