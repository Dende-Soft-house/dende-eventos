package br.com.dende.dendeeventos.ui.cadastro

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.components.DendeBackButton
import br.com.dende.dendeeventos.ui.components.DendeCheckBox
import br.com.dende.dendeeventos.ui.components.DendeDatePickerField
import br.com.dende.dendeeventos.ui.components.DendeDropdownField
import br.com.dende.dendeeventos.ui.components.DendeFooterButton
import br.com.dende.dendeeventos.ui.components.DendeTextField
import br.com.dende.dendeeventos.ui.components.DendeNotificationDialog
import br.com.dende.dendeeventos.ui.components.DendePasswordField
import br.com.dende.dendeeventos.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FluxoOrganizadorScreen(
    state: CadastroUiState.CadastroOrganizadorUiState,
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
            // Cabeçalho
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

            // Barra de Progresso
            if (state.currentStep > 1) {
                val passoAtualVisual = state.currentStep - 1
                val totalPassosVisuais = state.totalSteps
                val progresso = passoAtualVisual / totalPassosVisuais.toFloat()

                Text(
                    text = "Passo $passoAtualVisual de $totalPassosVisuais",
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
            }

            // O fluxo interno de etapas
            Box(modifier = Modifier.weight(1f)) {
                when (state.currentStep) {
                    1 -> IsEmpresa(state, viewModel)
                    2 -> if (state.isEmpresa == true) CadastrarDadosEmpresariais(state, viewModel) else CadastrarDadosPessoais(state, viewModel)
                    3 -> if (state.isEmpresa == true) CadastrarDadosPessoais(state, viewModel) else ConferenciaPF(state, viewModel)
                    4 -> ConferenciaPJ(state, viewModel)
                }
            }

            // Controle dos Diálogos
            if (state.showSuccessDialog) {
                CadastroConcluidoDialog(
                    onConfirm = {
                        viewModel.fecharDialogSucesso()
                        onVoltarParaLogin()
                    },
                    onDismiss = { viewModel.fecharDialogSucesso() }
                )
            }

            when (state.erroAtualDialog) {
                TipoErroDialog.EMAIL_DUPLICADO -> {
                    ErroEmailDuplicadoDialog(
                        onTentarNovamente = { viewModel.fecharDialogErro() },
                        onIrParaLogin = { viewModel.fecharDialogErro() }
                    )
                }
                TipoErroDialog.IDADE_MINIMA -> {
                    ErroIdadeMinimaDialog(
                        onTentarNovamente = { viewModel.fecharDialogErro() }
                    )
                }
                TipoErroDialog.CAMPOS_VAZIOS -> {
                    ErroCamposNaoPreenchidosDialog(
                        onTentarNovamente = { viewModel.fecharDialogErro() }
                    )
                }
                null -> { /* Não faz nada */ }
            }
        }
    }
}

@Composable
fun IsEmpresa(state: CadastroUiState.CadastroOrganizadorUiState, viewModel: CadastroViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Você representa um Empresa/Instituição?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row {
            OutlinedButton(
                onClick = {
                    viewModel.updateIsEmpresa(true)  
                    viewModel.avancarPasso()
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (state.isEmpresa == true) Orange.copy(alpha = 0.1f) else Color.Transparent,
                    contentColor = Orange
                ),
                border = BorderStroke(2.dp, if (state.isEmpresa == true) Color.LightGray else Orange),
                modifier = Modifier
                    .width(100.dp)
                    .height(56.dp)
            ) {
                Text("Sim", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color.Black)
            }

            Spacer(modifier = Modifier.width(50.dp))

            OutlinedButton(
                onClick = {
                    viewModel.updateIsEmpresa(false)  
                    viewModel.avancarPasso()
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (state.isEmpresa == false) Orange.copy(alpha = 0.1f) else Color.Transparent,
                    contentColor = Orange
                ),
                border = BorderStroke(2.dp, if (state.isEmpresa == false) Color.LightGray else Orange),
                modifier = Modifier
                    .width(100.dp)
                    .height(56.dp)
            ) {
                Text("Não", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color.Black)
            }
        }
    }
}

@Composable
fun CadastrarDadosEmpresariais(state: CadastroUiState.CadastroOrganizadorUiState, viewModel: CadastroViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Text("Dados empresariais", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
            Spacer(modifier = Modifier.height(8.dp))

            DendeTextField(
                value = state.cnpj,
                onValueChange = { viewModel.updateCnpj(it) },  
                title = "CNPJ",
                placeholder = "XX.XXX.XXX/XXXX-XX",
                isError = state.cnpjError != null,
                errorMessage = state.cnpjError
            )

            DendeTextField(
                value = state.razaoSocial,
                onValueChange = { viewModel.updateRazaoSocial(it) },  
                title = "Razão Social",
                placeholder = "Exemplo",
                isError = state.razaoSocialError != null,
                errorMessage = state.razaoSocialError
            )

            DendeTextField(
                value = state.nomeFantasia,
                onValueChange = { viewModel.updateNomeFantasia(it) },  
                title = "Nome Fantasia",
                placeholder = "Exemplo",
                isError = state.nomeFantasiaError != null,
                errorMessage = state.nomeFantasiaError
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        DendeFooterButton(
            onPrimaryClick = { viewModel.avancarPasso() },
            onSecondaryClick = { viewModel.voltarPasso() }
        )
    }
}
@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarDadosPessoais(state: CadastroUiState.CadastroOrganizadorUiState, viewModel: CadastroViewModel) {
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
                onValueChange = { novoTexto -> viewModel.updateEmail(novoTexto)},  
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
                placeholder = "Dende Eventos",
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
                onCheckedChange = { novoValor -> viewModel.updateAceitouTermos(novoValor) }, 
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
fun ConferenciaPJ(state: CadastroUiState.CadastroOrganizadorUiState, viewModel: CadastroViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Confira seus dados", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)

            ItemConferencia("Nome", state.nome)
            ItemConferencia("E-mail", state.email)
            ItemConferencia("Gênero", state.genero)
            ItemConferencia("Nascimento", state.dataNascimento)

            ItemConferencia("CNPJ", state.cnpj)
            ItemConferencia("Razão Social", state.razaoSocial)
            ItemConferencia("Nome Fantasia", state.nomeFantasia)

            Spacer(modifier = Modifier.height(16.dp))
        }
        DendeFooterButton(
            onPrimaryClick = { viewModel.abrirDialogSucesso() },
            onSecondaryClick = { viewModel.voltarPasso() }
        )
    }
}

@Composable
fun ConferenciaPF(state: CadastroUiState.CadastroOrganizadorUiState, viewModel: CadastroViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Confira seus dados", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)

            ItemConferencia("E-mail", state.email)
            ItemConferencia("Nome Completo", state.nome)
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

@Composable
fun ItemConferencia(label: String, valor: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(
            text = if (valor.isNotBlank()) valor else "Não preenchido",
            fontSize = 16.sp, fontWeight = FontWeight.Medium, fontFamily = Inter
        )
    }
}

// MODAIS
@Composable
fun CadastroConcluidoDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    DendeNotificationDialog(
        title = "Cadastro Realizado com Sucesso!",
        description = "Bem Vindo ao Dendê Eventos",
        confirmText = "Prosseguir",
        iconRes = R.drawable.sucess_ico,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        confirmButtonColor = Orange
    )
}

@Composable
fun ErroEmailDuplicadoDialog(onTentarNovamente: () -> Unit, onIrParaLogin: () -> Unit) {
    DendeNotificationDialog(
        title = "Atenção",
        description = "Email Já Cadastrado! \nPor favor tente outro email ou realize o Login",
        iconRes = R.drawable.error_ico,
        confirmText = "Tentar Novamente",
        dismissText = "Login",
        onConfirm = onTentarNovamente,
        onDismiss = onIrParaLogin
    )
}

@Composable
fun ErroIdadeMinimaDialog(onTentarNovamente: () -> Unit){
    DendeNotificationDialog(
        title = "Atenção",
        description =  "Você precisa ter no minimo 18 anos para ser um organizador de eventos.",
        iconRes = R.drawable.error_ico,
        confirmText = "Tentar Novamente",
        onConfirm = onTentarNovamente,
        onDismiss = onTentarNovamente
    )
}

@Composable
fun ErroCamposNaoPreenchidosDialog(onTentarNovamente: () -> Unit){
    DendeNotificationDialog(
        title = "Atenção",
        description =  "Todos os campos devem ser preenchidos!",
        iconRes = R.drawable.error_ico,
        confirmText = "Tentar Novamente",
        onConfirm = onTentarNovamente,
        onDismiss = onTentarNovamente
    )
}