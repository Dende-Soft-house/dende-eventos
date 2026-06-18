package br.com.dende.dendeeventos.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.dende.dendeeventos.ui.theme.DendeeventosTheme
import br.com.dende.dendeeventos.ui.theme.Orange

enum class ModalConfirmacaoTipo(
    val titulo: String,
    val acaoBotao: String,
    val mensagemAviso: String
) {
    REATIVAR(
        titulo = "Reativar Usuário",
        acaoBotao = "REATIVAR USUÁRIO",
        mensagemAviso = "Para reativar a conta, digite \"CONFIRMAR\"\ne prossiga"
    ),
    INATIVAR(
        titulo = "Inativar Usuário",
        acaoBotao = "INATIVAR USUÁRIO",
        mensagemAviso = "Para inativar a conta, digite \"CONFIRMAR\"\ne prossiga"
    )
}

@Composable
fun ReativarInativarUsuarioModalConfirmacao(
    visible: Boolean,
    tipo: ModalConfirmacaoTipo,
    confirmationText: String,
    onConfirmationTextChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit
) {
    if (!visible) return

    val focusManager = LocalFocusManager.current

    Dialog(
        onDismissRequest = {
            focusManager.clearFocus(true)
            onDismissRequest()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus(true)
                    onDismissRequest()
                },
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Orange,
                        modifier = Modifier.size(48.dp)
                    )

                    Text(
                        text = tipo.titulo,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = "DIGITE CONFIRMAR:",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(top = 12.dp),
                        )
                        OutlinedTextField(
                            value = confirmationText,
                            onValueChange = onConfirmationTextChange,
                            placeholder = {
                                Text(
                                    fontSize = 14.sp,
                                    text = "CONFIRMAR",
                                    color = Color.Gray,
                                    letterSpacing = 2.sp
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = CircleShape,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF0F0F0),
                                unfocusedContainerColor = Color(0xFFF0F0F0),
                                disabledContainerColor = Color(0xFFF0F0F0),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Orange
                            )
                        )
                        
                        Text(
                            text = tipo.mensagemAviso,
                            color = Color(0xFFD32F2F), // Vermelho aviso
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                focusManager.clearFocus(true)
                                onConfirm()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            enabled = confirmationText.equals("CONFIRMAR", ignoreCase = true),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Orange,
                                disabledContainerColor = Orange.copy(alpha = 1f),
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text(
                                text = tipo.acaoBotao,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                focusManager.clearFocus(true)
                                onCancel()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(
                                2.dp,
                                Orange.copy(alpha = 1f)
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange.copy(alpha = 1f))
                        ) {
                            Text(
                                text = "CANCELAR",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReativarInativarUsuarioModalPreview() {
    DendeeventosTheme {
        ReativarInativarUsuarioModalConfirmacao(
            visible = true,
            tipo = ModalConfirmacaoTipo.INATIVAR,
            confirmationText = "",
            onConfirmationTextChange = {},
            onConfirm = {},
            onCancel = {},
            onDismissRequest = {}
        )
    }
}
