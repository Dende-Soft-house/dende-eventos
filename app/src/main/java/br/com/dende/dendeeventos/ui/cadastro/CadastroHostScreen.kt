package br.com.dende.dendeeventos.ui.cadastro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.components.DendeBackButton
import br.com.dende.dendeeventos.ui.theme.BlackLinear
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.White

@Composable
fun CadastroHostScreen(
    viewModel: CadastroViewModel = viewModel(),
    onVoltarParaLogin: () -> Unit
) {
    // Escuta o estado centralizado do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            when (val state = uiState) {

                // 1. TELA DE SELEÇÃO INICIAL
                is CadastroUiState.SelecaoPerfil -> {
                    SelecaoPerfilScreen(
                        onParticiparClick = { viewModel.selecionarPerfilUsuario() },
                        onOrganizarClick = { viewModel.selecionarPerfilOrganizador() },
                        onVoltarParaLogin = onVoltarParaLogin
                    )
                }

                // 2. FLUXO DO USUÁRIO COMUM
                is CadastroUiState.CadastroUsuarioUiState -> {
                    FluxoUsuarioScreen(
                        state = state,
                        viewModel = viewModel,
                        onVoltarParaLogin = onVoltarParaLogin
                    )
                }

                // 3. FLUXO DO ORGANIZADOR
                is CadastroUiState.CadastroOrganizadorUiState -> {
                    FluxoOrganizadorScreen(
                        state = state,
                        viewModel = viewModel,
                        onVoltarParaLogin = onVoltarParaLogin
                    )
                }
            }
        }
    }
}

@Composable
fun SelecaoPerfilScreen(
    onParticiparClick: () -> Unit,
    onOrganizarClick: () -> Unit,
    onVoltarParaLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DendeBackButton(onClick = onVoltarParaLogin)
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "Registrar-me",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Para qual finalidade gostaria de criar sua conta?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = Inter,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(90.dp))

        Button(
            onClick = onParticiparClick,
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlackLinear, contentColor = White),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Participar de Eventos", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onOrganizarClick,
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange),
            border = BorderStroke(2.dp, Orange),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Organizar Eventos", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        }
    }
}