package br.com.dende.dendeeventos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.theme.Orange

@Composable
fun CadastroCartaoScreen(
    onSalvar: () -> Unit,
    onFechar: () -> Unit
) {
    var numero by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var validade by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var salvarCartao by remember { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            // Header com X
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Adicionar Cartão",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onFechar) {
                    Icon(Icons.Default.Close, contentDescription = "Fechar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Número do Cartão", fontSize = 12.sp, fontWeight = FontWeight.Medium)
            OutlinedTextField(
                value = numero,
                onValueChange = { numero = it },
                placeholder = { Text("0000 0000 0000 0000") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Nome", fontSize = 12.sp, fontWeight = FontWeight.Medium)
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                placeholder = { Text("e.g. João Dias") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Validade", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = validade,
                        onValueChange = { validade = it },
                        placeholder = { Text("MM/YY") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("CVV", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        placeholder = { Text("123") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Salvar para outros pagamentos",
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp
                )
                Switch(
                    checked = salvarCartao,
                    onCheckedChange = { salvarCartao = it },
                    colors = SwitchDefaults.colors(checkedTrackColor = Orange)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onSalvar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange)
            ) {
                Text("SALVAR CARTÃO", fontWeight = FontWeight.Bold)
            }
        }
    }
}
