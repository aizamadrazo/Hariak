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
import kotlin.random.Random


class Helmuga (var metros : Int = 100)
class Korrhilo(
    var nombre:  String,
    //private val lock: Any,
    private val lista : MutableList<String>,
    private var korrika: Helmuga



): Thread(nombre){
    override fun run() {

        while (true){
            //synchronized(lock){
                val metroak = Random.nextInt(1, 100)
                var queda : Int

                if (korrika.metros <=0 )return
                //con etso evitamso que corra negativo
                korrika.metros = maxOf(0, korrika.metros - metroak)
                 queda = korrika.metros

                lista.add("$name a recorrifo $metroak y quedan: $queda")

                if (korrika.metros == 100 ){
                    lista.add("Empecamso")

                }
                //usaamos return porque por mucho que un atleta cabe el tro tienque seguir
                if (korrika.metros == 0){
                    lista.add("$name a cabado la carrera  ")
                    return
                }
            //}
        }
    }

}

@Composable
fun Korrika(){
//val lock = Any()
    //si el obejoto no es comaprido ponemos dos ya que no recorren en el msimo carril sino idtintos
    val korrika1 = Helmuga()
    val korrika2 = Helmuga()

    var lista = remember { mutableStateListOf<String>() }

    val Atleta1 = Korrhilo("Atleta1" , lista, korrika1 )
    val Atleta2 = Korrhilo("Atleta2", lista,korrika2 )

    Atleta1.start()
    Atleta2.start()

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
