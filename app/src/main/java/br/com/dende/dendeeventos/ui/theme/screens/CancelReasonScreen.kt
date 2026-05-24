package br.com.dende.dendeeventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.theme.Orange

@Composable
fun CancelReasonScreen(
    onContinuar: () -> Unit,
    onVoltar: () -> Unit
) {
    val motivos = listOf(
        "Não posso comparecer ao evento",
        "Mudança de planos",
        "Motivos de saúde / pessoais",
        "Encontrei ingressos melhores",
        "Outro motivo"
    )
    var selecionado by remember { mutableStateOf(motivos.first()) }
    var observacoes by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onVoltar) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("Motivo do Cancelamento", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1.3f))
            }

            Text("Passo 2 de 3", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Por favor, selecione o motivo do cancelamento",
                color = Color.White,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de motivos
            motivos.forEach { motivo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selecionado == motivo) Orange.copy(alpha = 0.2f)
                        else Color.DarkGray.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selecionado == motivo,
                            onClick = { selecionado = motivo },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Orange,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(motivo, color = Color.White, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Observações adicionais (opcional)", color = Color.White, fontSize = 12.sp)
            OutlinedTextField(
                value = observacoes,
                onValueChange = { observacoes = it },
                placeholder = { Text("Conte-nos mais sobre o seu motivo...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onContinuar,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange)
            ) {
                Text("CONTINUAR", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = onVoltar,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("VOLTAR", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
