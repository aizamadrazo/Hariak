package com.example.hariak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hariak.ui.theme.HariakTheme

class Saludo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 1..3) {
            Saludo(i).start()
        }
    }

}

@Composable

fun Greeting(modifier: Modifier = Modifier) {
    Button(
        onClick={

        }
    ){
        Text(
            text = "Hello",
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HariakTheme {
        Greeting()
    }
}