
package com.example.cronometro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.concurrent.thread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class Haria2(

):
    Runnable{
    var acitvo = false
    var segundos by mutableIntStateOf(0)
    var minutos by mutableIntStateOf(0)
    var horas by mutableIntStateOf(0)
    override fun run() {
        while (true) {
            if (acitvo) {
                //dromimod el hilo por si le dan a stop puesque no siga activo del todo el hilo por el retado
                Thread.sleep(100)
                if (acitvo) {
                    segundos++
                    Thread.sleep(1000)

                    if (segundos >= 60) {
                        segundos -= 60
                        minutos++
                    }
                    if (minutos >= 60) {
                        minutos -= 60
                        horas++
                    }

                } else {
                    Thread.sleep(100)


                }
            }
        }
    }
}

@Composable
fun Conometro2() {
    //apra saber si ya se ha iciado el cono enl viual
    var hiloIniciado by remember { mutableStateOf(false) }
    val haria = remember { Haria2() }
    val hilo = Thread(haria)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFAFC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // TextView: tiempo del cronometro
        Text(
            text = "%02d:%02d:%02d".format(
                haria.horas,
                haria.minutos,
                haria.segundos
            ),            fontSize = 38.sp,
            color = Color(0xFF49454F),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Boton Start
        Button(
            onClick = {
                haria.acitvo = true
                if (!hiloIniciado) {
                    hilo.start()
                    hiloIniciado = true
                }
            },
            modifier = Modifier
                .width(120.dp)
                .height(48.dp)
                .padding(bottom = 4.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6750A4)
            )
        ) {
            Text("Start", color = Color.White)
        }

        // Boton Pause
        Button(
            onClick = {
                haria.acitvo = false

            },
            modifier = Modifier
                .width(120.dp)
                .height(48.dp)
                .padding(bottom = 4.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6750A4)
            )
        ) {
            Text("Pause", color = Color.White)
        }

        // Boton Reset
        Button(
            onClick = {
                haria.acitvo = false

                haria.segundos = 0
                haria.minutos = 0
                haria.horas = 0

            },
            modifier = Modifier
                .width(120.dp)
                .height(48.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6750A4)
            )
        ) {
            Text("Reset", color = Color.White)
        }
    }
}
