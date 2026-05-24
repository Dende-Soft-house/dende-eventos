package br.com.dende.dendeeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import br.com.dende.dendeeventos.core.designsystem.components.EventCard
import br.com.dende.dendeeventos.core.designsystem.components.InvitePopup
import br.com.dende.dendeeventos.ui.fluxo.FluxoNavHost
import br.com.dende.dendeeventos.ui.theme.DendeeventosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DendeeventosTheme {
                FluxoNavHost()
            }
        }
    }
}


@Composable
fun MinhaTela() {
    var showInvitePopup by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { showInvitePopup = true }) {
            Text("Abrir Convite")
        }
    }

    if (showInvitePopup) {
        InvitePopup(
            onDismiss = { showInvitePopup = false },
            onAccept = {
                showInvitePopup = false
            },
            onReject = {
                showInvitePopup = false
            }
        )
    }
}

@Composable
fun CategorySelector() {
    var selectedCategory by remember { mutableStateOf("Design") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CategoryChip(
            label = "Design",
            iconRes = R.drawable.ic_launcher_background,
            isSelected = selectedCategory == "Design",
            onClick = { selectedCategory = "Design" }
        )

        CategoryChip(
            label = "Teste",
            iconRes = R.drawable.ic_launcher_background,
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
