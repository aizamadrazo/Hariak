package com.example.hariak

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Scaffold(topBar = { TopAppBar(title = { Text("Hilos: fábrica de galletas") }) }) { padding ->
                    Column(
                        modifier = Modifier.padding(padding).padding(24.dp).fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { startActivity(Intent(this@MainActivity, Metodo1Activity::class.java)) },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Método 1: Thread") }

                        Button(
                            onClick = { startActivity(Intent(this@MainActivity, Metodo2Activity::class.java)) },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Método 2: Runnable") }

                        Button(
                            onClick = { startActivity(Intent(this@MainActivity, Metodo3Activity::class.java)) },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Método 3: lambda") }
                    }
                }
            }
        }
    }
}