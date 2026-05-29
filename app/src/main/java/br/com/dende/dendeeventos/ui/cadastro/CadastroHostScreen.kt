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
            // O Roteador direciona para o visual correto dependendo do estado
            when (val state = uiState) {

                // 1. TELA DE SELEÇÃO (Com o visual do seu colega)
                is CadastroUiState.SelecaoPerfil -> {
                    TelaSelecaoInicial(
                        onParticiparClick = { viewModel.selecionarPerfilUsuario() },
                        onOrganizarClick = { viewModel.selecionarPerfilOrganizador() }
                    )
                }

                // 2. O SEU ARQUIVO (Fluxo do Usuário Comum)
                is CadastroUiState.CadastroUsuarioUiState -> {
                    FluxoUsuarioScreen(state = state, viewModel = viewModel)
                }

                // 3. O ARQUIVO DO SEU COLEGA (Fluxo do Organizador)
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

// O visual que o seu colega criou, agora isolado em uma função limpa!
@Composable
fun TelaSelecaoInicial(
    onParticiparClick: () -> Unit,
    onOrganizarClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Para qual finalidade gostaria de Criar sua conta?",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = Inter,
            textAlign = TextAlign.Center
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
            Text("Participar de Eventos", fontSize = 23.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        }

        OutlinedButton(
            onClick = onOrganizarClick,
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange),
            border = BorderStroke(2.dp, Orange),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Organizar Eventos", fontSize = 23.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        }
    }
}