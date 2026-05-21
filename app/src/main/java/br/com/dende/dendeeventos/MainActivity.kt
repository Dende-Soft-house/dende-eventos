package br.com.dende.dendeeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import br.com.dende.dendeeventos.ui.theme.ListarIngressos // IMPORTANTE: Adicione este import!

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ListarIngressos()
        }
    }
}

