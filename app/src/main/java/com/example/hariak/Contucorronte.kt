package com.example.hariak

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class Kontua (var moni : Int = 40)
class Konthilo(
    var nombre:  String,
    private val lock: Any,
    private val lista : MutableList<String>,
    private val sacar : Int,
    private var kontua: Kontua



): Thread(nombre){
    override fun run() {
        while (true){
            synchronized(lock){
                var queda : Int
                if (kontua.moni <=0 )return
                kontua.moni -= sacar

                 queda = kontua.moni

                lista.add("$name a sacado diner queda $queda")

                if (kontua.moni == 40 ){
                    lista.add("Empecamso")

                }
                if (kontua.moni == 0){
                    lista.add("ya o queda dinero ")
                }
            }
        }
    }

}

@Composable
fun Contucorronte(){
val lock = Any()
    val kontua = Kontua()
    var lista = remember { mutableStateListOf<String>() }
    val Ane = Konthilo("Ane", lock , lista, 10, kontua )
    val Mikel = Konthilo("Mikel", lock, lista, 10, kontua )

    Mikel.start()
    Ane.start()

    // ---------- PANTALLA ----------
    val listState = rememberLazyListState()
    // Cuando llega una línea nueva, la lista baja sola hasta el final
    LaunchedEffect(lista.size) {
        if (lista.isNotEmpty()) listState.animateScrollToItem(lista.size - 1)
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
                items(lista) { Text(it) }
            }
        }
    }
}
