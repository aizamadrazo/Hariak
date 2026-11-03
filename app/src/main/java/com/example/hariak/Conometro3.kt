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
import java.lang.Thread.sleep

@Composable
fun Conometro3() {
    //apra saber si ya se ha iciado el cono enl viual
    var hiloIniciado by remember { mutableStateOf(false) }
    var acitvo  by remember {mutableStateOf(false)}
    var segundos by remember { mutableIntStateOf(0) }
    var minutos by remember {mutableIntStateOf(0)}
    var horas by remember {mutableIntStateOf(0)}

    val haria = Thread {
            while (true) {
                if (acitvo) {
                    //dromimod el hilo por si le dan a stop puesque no siga activo del todo el hilo por el retado
                    Thread.sleep(1000)
                    if (acitvo) {
                        Thread.sleep(1000)
                        segundos++

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFAFC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // TextView: tiempo del cronometro
        Text(
            text = "%02d:%02d:%02d".format(horas, minutos, segundos)

        , fontSize = 38.sp,
            color = Color(0xFF49454F),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Boton Start
        Button(
            onClick = {
                acitvo = true
                if (!hiloIniciado) {
                    haria.start()
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
                acitvo = false

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
                acitvo = false
                segundos = 0
                minutos = 0
                horas = 0

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
