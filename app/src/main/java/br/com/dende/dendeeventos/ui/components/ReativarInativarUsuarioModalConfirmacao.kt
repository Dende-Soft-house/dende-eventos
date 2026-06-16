package br.com.dende.dendeeventos.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
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
import androidx.navigation.NavController
import br.com.dende.dendeeventos.domain.ModalConfirmacaoViewModel
import br.com.dende.dendeeventos.ui.navigation.AppDestinations
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
    navController: NavController,
    viewModel: ModalConfirmacaoViewModel = remember { ModalConfirmacaoViewModel() },
    onSuccess: (ModalConfirmacaoTipo) -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(visible, tipo) {
        if (visible) {
            viewModel.abrirModal(tipo)
        } else {
            viewModel.fecharModal()
        }
    }

    ReativarInativarUsuarioModalConfirmacaoContent(
        visible = uiState.visible,
        tipo = uiState.tipo,
        confirmationText = uiState.confirmationText,
        showError = uiState.showError,
        onConfirmationTextChange = viewModel::onConfirmationTextChange,
        onConfirm = {
            val tipoAtual = uiState.tipo
            val confirmado = viewModel.confirmarAcao()

            if (confirmado) {
                onSuccess(tipoAtual)
                if (tipoAtual == ModalConfirmacaoTipo.REATIVAR) {
                    // Redireciona diretamente para o Feed de Eventos ao reativar
                    navController.navigate(AppDestinations.FeedEventos) {
                        popUpTo(AppDestinations.Login) { inclusive = true }
                        launchSingleTop = true
                    }
                } else if (tipoAtual == ModalConfirmacaoTipo.INATIVAR) {
                    // Redireciona para o Login ao inativar, limpando a pilha
                    navController.navigate(AppDestinations.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        },
        onCancel = {
            viewModel.fecharModal()
            onCancel()
        },
        onDismissRequest = {
            viewModel.fecharModal()
            onDismissRequest()
        }
    )
}

@Composable
private fun ReativarInativarUsuarioModalConfirmacaoContent(
    visible: Boolean,
    tipo: ModalConfirmacaoTipo,
    confirmationText: String,
    showError: Boolean,
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus(true)
                        onDismissRequest()
                    }
            )

            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
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
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "DIGITE CONFIRMAR:",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = confirmationText,
                            onValueChange = { novoTexto ->
                                onConfirmationTextChange(novoTexto)
                            },
                            placeholder = {
                                Text(
                                    text = "CONFIRMAR",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    letterSpacing = 2.sp
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = true,
                            shape = RoundedCornerShape(30.dp),
                            isError = showError,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF0F0F0),
                                unfocusedContainerColor = Color(0xFFF0F0F0),
                                disabledContainerColor = Color(0xFFF0F0F0),
                                errorContainerColor = Color(0xFFF0F0F0),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                cursorColor = Orange,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                errorTextColor = Color.Black
                            )
                        )

                        if (showError) {
                            Text(
                                text = tipo.mensagemAviso,
                                color = Color(0xFFD32F2F),
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp)
                            )
                        }
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
                                .padding(top = 8.dp)
                                .height(50.dp),
                            enabled = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Orange,
                                contentColor = Color.White,
                                disabledContainerColor = Orange,
                                disabledContentColor = Color.White
                            ),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            Text(
                                text = tipo.acaoBotao,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                focusManager.clearFocus(true)
                                onCancel()
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = Orange
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Orange
                            ),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            Text(
                                text = "CANCELAR",
                                fontWeight = FontWeight.Bold
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
    val navController = androidx.navigation.compose.rememberNavController()
    DendeeventosTheme {
        ReativarInativarUsuarioModalConfirmacao(
            visible = true,
            tipo = ModalConfirmacaoTipo.REATIVAR,
            navController = navController,
            onSuccess = {},
            onCancel = {},
            onDismissRequest = {}
        )
    }
}