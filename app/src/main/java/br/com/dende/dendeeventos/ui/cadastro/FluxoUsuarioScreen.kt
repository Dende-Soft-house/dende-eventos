package br.com.dende.dendeeventos.ui.cadastro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.components.*
import br.com.dende.dendeeventos.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FluxoUsuarioScreen(
    state: CadastroUiState.CadastroUsuarioUiState,
    viewModel: CadastroViewModel,
    onVoltarParaLogin: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val estaEmModal = state.erroAtualDialog != null || state.showSuccessDialog
                val mostrarBotao = !estaEmModal && state.currentStep == 1

                if (mostrarBotao) {
                    DendeBackButton(onClick = { viewModel.voltarPasso() })
                    Spacer(modifier = Modifier.width(30.dp))
                }
                Text("Registrar-me", fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BARRA DE PROGRESSO DO USUÁRIO
            val passoAtualVisual = state.currentStep
            val progresso = passoAtualVisual / state.totalSteps.toFloat()

            Text(
                text = "Passo $passoAtualVisual de ${state.totalSteps}",
                style = MaterialTheme.typography.labelLarge,
                color = Orange
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progresso },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Orange,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            // ROTEAMENTO DOS PASSOS (View)
            Box(modifier = Modifier.weight(1f)) {
                when (state.currentStep) {
                    1 -> CadastrarDadosComuns(state, viewModel)
                    2 -> ConferenciaUsuarioComum(state, viewModel)
                }
            }

            // DIALOGS E POP-UPS LIGADOS AO LOGIN
            if (state.showSuccessDialog) {
                CadastroConcluidoDialog(
                    onConfirm = {
                        viewModel.fecharDialogSucesso()
                        onVoltarParaLogin() // <-- Vai pro Login ao terminar!
                    },
                    onDismiss = { viewModel.fecharDialogSucesso() }
                )
            }

            when (state.erroAtualDialog) {
                TipoErroDialog.CAMPOS_VAZIOS -> ErroCamposNaoPreenchidosDialog { viewModel.fecharDialogErro() }
                TipoErroDialog.IDADE_MINIMA -> ErroIdadeMinimaDialog { viewModel.fecharDialogErro() }
                TipoErroDialog.EMAIL_DUPLICADO -> ErroEmailDuplicadoDialog(
                    onTentarNovamente = { viewModel.fecharDialogErro() },
                    onIrParaLogin = {
                        viewModel.fecharDialogErro()
                        onVoltarParaLogin()
                    }
                )
                null -> {}
            }
        }
    }
}

@Composable
fun CadastrarDadosComuns(state: CadastroUiState.CadastroUsuarioUiState, viewModel: CadastroViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Dados Pessoais", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)

            DendeTextField(
                value = state.email,
                onValueChange = { viewModel.updateEmail(it) },
                title = "Email",
                placeholder = "exemplo@email.com",
                isError = state.emailError != null,
                errorMessage = state.emailError
            )

            DendePasswordField(
                value = state.senha,
                onValueChange = { viewModel.updateSenha(it) },
                isError = state.senhaError != null,
                errorMessage = state.senhaError
            )

            DendeTextField(
                value = state.nome,
                onValueChange = { viewModel.updateNome(it) },
                title = "Nome",
                placeholder = "João Silva",
                isError = state.nomeError != null,
                errorMessage = state.nomeError
            )

            DendeDropdownField(
                value = state.genero,
                onValueChange = { viewModel.updateGenero(it) },
                options = listOf("Masculino", "Feminino", "Não Binário", "Prefiro não dizer"),
                title = "Gênero",
                placeholder = "Selecione..."
            )

            DendeDatePickerField(
                value = state.dataNascimento,
                onDateSelected = { viewModel.updateDataNascimento(it) },
                title = "Data de Nascimento",
                placeholder = "DD/MM/AAAA"
            )

            DendeCheckBox(
                checked = state.aceitouTermos,
                onCheckedChange = { viewModel.updateAceitouTermos(it) },
                isError = state.aceitouTermosError
            )
        }

        DendeFooterButton(
            onPrimaryClick = { viewModel.avancarPasso() },
            onSecondaryClick = { viewModel.voltarPasso() }
        )
    }
}

@Composable
fun ConferenciaUsuarioComum(state: CadastroUiState.CadastroUsuarioUiState, viewModel: CadastroViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Confira seus dados", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)

            ItemConferencia("Nome Completo", state.nome)
            ItemConferencia("E-mail", state.email)
            ItemConferencia("Gênero", state.genero)
            ItemConferencia("Nascimento", state.dataNascimento)

            Spacer(modifier = Modifier.height(16.dp))
        }
        DendeFooterButton(
            onPrimaryClick = { viewModel.abrirDialogSucesso() },
            onSecondaryClick = { viewModel.voltarPasso() }
        )
    }
}