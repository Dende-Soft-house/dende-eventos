package br.com.dende.dendeeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dende.dendeeventos.core.designsystem.components.CategoryChip
import br.com.dende.dendeeventos.core.designsystem.components.InviteCard
import br.com.dende.dendeeventos.core.designsystem.components.InvitePopup
import br.com.dende.dendeeventos.ui.theme.DendeeventosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CategorySelector()
        }
    }
}


@Composable
fun MinhaTela() {
    // Estado que controla se o popup aparece
    var showInvitePopup by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { showInvitePopup = true }) {
            Text("Abrir Convite")
        }
    }

    // Se o estado for true, mostramos o Dialog
    if (showInvitePopup) {
        InvitePopup(
            onDismiss = { showInvitePopup = false },
            onAccept = {
                showInvitePopup = false
                // lógica de aceitar
            },
            onReject = {
                showInvitePopup = false
                // lógica de recusar
            }
        )
    }
}

@Composable
fun CategorySelector() {
    // Estado para saber qual categoria está selecionada (ID ou Nome)
    var selectedCategory by remember { mutableStateOf("Design") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Categoria Design
        CategoryChip(
            label = "Design",
            iconRes = R.drawable.ic_launcher_background, // seu ícone
            isSelected = selectedCategory == "Design",
            onClick = { selectedCategory = "Design" }
        )

        // Categoria Art
        CategoryChip(
            label = "Art",
            iconRes = R.drawable.ic_launcher_background, // seu ícone
            isSelected = selectedCategory == "Art",
            onClick = { selectedCategory = "Art" }
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DendeeventosTheme {
        Greeting("Android")
    }
}