package br.com.dende.dendeeventos

import android.os.Bundle
import android.widget.Toast // Importe necessário para o Toast de teste
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dende.dendeeventos.core.designsystem.components.*
import br.com.dende.dendeeventos.ui.theme.DendeeventosTheme
import br.com.dende.dendeeventos.ui.cadastro.CadastroHostScreen
// import br.com.dende.dendeeventos.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DendeeventosTheme {

                CadastroHostScreen(
                    onVoltarParaLogin = {
                        Toast.makeText(this, "Ação: Voltar para a tela de Login", Toast.LENGTH_SHORT).show()
                    }
                )


            }
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
            label = "Teste",
            iconRes = R.drawable.ic_launcher_background, // seu ícone
            isSelected = selectedCategory == "Art",
            onClick = { selectedCategory = "Art" }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DendeeventosTheme {
        EventCard(imageUrl = "")
    }
}