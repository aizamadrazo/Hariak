package com.example.hariak

import android.app.Activity
import android.widget.Button
import androidx.activity.compose.LocalActivity
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
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random


class Helmuga1 (var metros : Int = 100)
class Korrhilo1(
    var nombre:  String,
    //private val lock: Any,
    private val lista : MutableList<String>,
    private var korrika: Helmuga1,
    private val activity: Activity



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
                activity.runOnUiThread {

                    lista.add("$name a recorrifo $metroak y quedan: $queda")
                }
                if (korrika.metros == 100 ){
                    activity.runOnUiThread {
                        lista.add("Empecamso")
                    }
                }
                //usaamos return porque por mucho que un atleta cabe el tro tienque seguir
                if (korrika.metros == 0){
                    activity.runOnUiThread {

                    lista.add("$name a cabado la carrera  ")}
                    return
                }
            //}
        }
    }

}


/*
@Composable
fun Korrika2(){
//val lock = Any()
    val activity = LocalActivity.current!!

    val lista = remember { mutableStateListOf<String>() }

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
            Button(onClick = {
                lista.clear()
                val atleta1 = Korrhilo1("Atleta1", lista, Helmuga1(), activity)
                val atleta2 = Korrhilo1("Atleta2", lista, Helmuga1(), activity)
                atleta1.start()
                atleta2.start()
            }) { Text("Iniciar") }

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
*/

@Composable
fun Korrika2(){
//val lock = Any()
    //si el obejoto no es comaprido ponemos dos ya que no recorren en el msimo carril sino idtintos
    val activity = LocalActivity.current!!


    var lista = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        val atleta1 = Korrhilo1("Atleta1", lista, Helmuga1(), activity)
        val atleta2 = Korrhilo1("Atleta2", lista, Helmuga1(), activity)
        atleta1.start()
        atleta2.start()

        Thread {
            atleta1.join()      // se queda aquí hasta que acabe el Atleta1
            atleta2.join()      // y luego hasta que acabe el Atleta2
            // llegados aquí, los dos han terminado
            activity.runOnUiThread {
                lista.add("CARRERA TERMINADA")
            }
        }.start()
    }

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
