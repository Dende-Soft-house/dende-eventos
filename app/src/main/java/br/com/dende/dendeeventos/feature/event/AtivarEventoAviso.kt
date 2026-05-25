package br.com.dende.dendeeventos.feature.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.domain.EventStatusViewModel
import br.com.dende.dendeeventos.ui.theme.Orange
// AtivarEventoAviso.kt — primeira linha deve ser:


@Composable
fun AtivarEventoAvisoDialog(
    eventoId: String,
    viewModel: EventStatusViewModel = viewModel(),
    onDismiss: () -> Unit,
    onSucesso: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.estadoSucesso) {
        if (uiState.estadoSucesso) onSucesso()
    }

    Dialog(onDismissRequest = onDismiss) {
        AtivarEventoAvisoContent(
            confirmarText = uiState.confirmar.orEmpty(),
            erroText = uiState.erro,
            botaoLiberado = uiState.botaoLiberado,
            estadoCarregando = uiState.estadoCarregando,
            onConfirmarChange = viewModel::confirmar,
            onAtivarClick = { viewModel.ativar(eventoId) },
            onCancelarClick = onDismiss
        )
    }
}

@Composable
fun AtivarEventoAvisoContent(
    confirmarText: String,
    erroText: String?,
    botaoLiberado: Boolean,
    estadoCarregando: Boolean,
    onConfirmarChange: (String) -> Unit,
    onAtivarClick: () -> Unit,
    onCancelarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(342.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.aviso),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ativar Evento",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Você está a 1 passo de ativar este evento",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "DIGITE CONFIRMAR:",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmarText,
                onValueChange = onConfirmarChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text("CONFIRMAR", fontSize = 14.sp, color = Color.LightGray)
                },
                shape = RoundedCornerShape(12.dp)
            )

            if (!erroText.isNullOrEmpty()) {
                Text(
                    text = erroText,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onAtivarClick,
                enabled = botaoLiberado && !estadoCarregando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange,
                    disabledContainerColor = Orange.copy(alpha = 0.5f)
                )
            ) {
                if (estadoCarregando) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "ATIVAR EVENTO",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onCancelarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Orange)
            ) {
                Text(
                    text = "CANCELAR",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAtivarEventoAviso() {
    AtivarEventoAvisoContent(
        confirmarText = "",
        erroText = null,
        botaoLiberado = false,
        estadoCarregando = false,
        onConfirmarChange = {},
        onAtivarClick = {},
        onCancelarClick = {}
    )
}
