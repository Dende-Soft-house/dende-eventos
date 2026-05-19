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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
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
import br.com.dende.dendeeventos.ui.theme.BlackLinear
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarOrganizadorScreen(
    initialState: CadastroUiState = CadastroUiState()
) {
    var state by remember { mutableStateOf(initialState) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DendeBackButton( onClick = {
                    if (state.tipoUsuario != null) {
                        if (state.currentStep > 1) {
                            state = state.copy(currentStep = state.currentStep - 1)
                        } else {
                            state = state.copy(tipoUsuario = null, isEmpresa = null)
                        }
                    }
                })
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Registrar-me",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (state.tipoUsuario == null) {
                CadastrarUsuario(state = state, onStateChange = { state = it })
            } else if (state.tipoUsuario == "ORGANIZADOR") {

        if (state.currentStep > 1) {
            val passoAtualVisual = state.currentStep - 1

            val totalPassosVisuais = if (state.isEmpresa == true) 3 else 2
            val progresso = passoAtualVisual / totalPassosVisuais.toFloat()

            // BARRA DE PROGRESSO DO ORGANIZADOR
            Text(
                text = "Passo $passoAtualVisual de $totalPassosVisuais",
                style = MaterialTheme.typography.labelLarge,
                color = Orange
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progresso,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Orange,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (state.currentStep) {
                1 -> IsEmpresa(state) { state = it }
                2 -> if (state.isEmpresa == true) CadastrarDadosEmpresariais(state) { state = it } else CadastrarDadosPessoais(state) { state = it }
                3 -> if (state.isEmpresa == true) CadastrarDadosPessoais(state) { state = it } else ConferenciaPF(state)
                4 -> ConferenciaPJ(state)
            }
        }

                if (state.showSuccessDialog) {
                    CadastroConcluidoDialog(
                        onConfirm = { },
                        onDismiss = {}
                    )
                }

                when (state.erroAtualDialog) {
                    TipoErroDialog.EMAIL_DUPLICADO -> {
                        ErroEmailDuplicadoDialog(
                            onTentarNovamente = { state = state.copy(erroAtualDialog = null) },
                            onIrParaLogin = {
                                state = state.copy(erroAtualDialog = null)
                                // Aqui no futuro você colocará a navegação para a tela de Login
                            }
                        )
                    }
                    TipoErroDialog.IDADE_MINIMA -> {
                        ErroIdadeMinimaDialog(
                            onTentarNovamente = { state = state.copy(erroAtualDialog = null) }
                        )
                    }
                    TipoErroDialog.CAMPOS_VAZIOS -> {
                        ErroCamposNaoPreenchidosDialog(
                            onTentarNovamente = { state = state.copy(erroAtualDialog = null) }
                        )
                    }
                    null -> { /* Não faz nada, nenhum erro ativo */ }
                }

            } else {
                Text("Fluxo de Participante em construção...")
            }
        }
    }
}



@Composable
fun CadastrarUsuario(state: CadastroUiState, onStateChange: (CadastroUiState) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
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
            onClick = { onStateChange(state.copy(tipoUsuario = "COMUM")) },
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlackLinear, contentColor = White),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Participar de Eventos", fontSize = 23.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        }

        OutlinedButton(
            onClick = { onStateChange(state.copy(tipoUsuario = "ORGANIZADOR")) },
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

@Composable
fun IsEmpresa(state: CadastroUiState, onStateChange: (CadastroUiState) -> Unit) {
    var state by remember { mutableStateOf(CadastroUiState()) }
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

        Row() {
            OutlinedButton(
                onClick = { onStateChange(state.copy(isEmpresa = true)) },
                shape = RoundedCornerShape(15.dp),
                // Se estiver selecionado, podemos mudar a cor do fundo para dar feedback
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
                onClick = { onStateChange(state.copy(isEmpresa = false)) },
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
fun CadastrarDadosEmpresariais(state: CadastroUiState, onStateChange: (CadastroUiState) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {


        Column(modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Text(
                "Dados empresariais",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter
            )

            Spacer(modifier = Modifier.height(8.dp))

            DendeTextField(
                value = state.cnpj,
                onValueChange = { onStateChange(state.copy(cnpj = it)) },
                title = "CNPJ",
                placeholder = "XX.XXX.XXX/XXXX-XX",
                isError = state.cnpjError != null,
                errorMessage = state.cnpjError
            )

            DendeTextField(
                value = state.razaoSocial,
                onValueChange = { onStateChange(state.copy(razaoSocial = it)) },
                title = "Razão Social",
                placeholder = "Exemplo",
                isError = state.razaoSocialError != null,
                errorMessage = state.razaoSocialError
            )

            DendeTextField(
                value = state.nomeFantasia,
                onValueChange = { onStateChange(state.copy(nomeFantasia = it)) },
                title = "Nome Fantasia",
                placeholder = "Exemplo",
                isError = state.nomeFantasiaError != null,
                errorMessage = state.nomeFantasiaError
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        DendeFooterButton(
            onPrimaryClick = {
                onStateChange(state.copy(currentStep = state.currentStep + 1))
            },
            onSecondaryClick = {
                onStateChange(state.copy(currentStep = state.currentStep - 1))
            }
        )
    }
}
@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarDadosPessoais(state: CadastroUiState, onStateChange: (CadastroUiState) -> Unit) {
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
                onValueChange = { onStateChange(state.copy(email = it)) },
                title = "Email",
                placeholder = "exemplo@email.com",
                isError = state.emailError != null,
                errorMessage = state.emailError
            )

            DendeTextField(
                value = state.senha,
                onValueChange = { onStateChange(state.copy(senha = it)) },
                title = "Senha",
                placeholder = "********",
                isError = state.senhaError != null,
                errorMessage = state.senhaError
            )

            DendeTextField(
                value = state.nome,
                onValueChange = { onStateChange(state.copy(nome = it)) },
                title = "Nome",
                placeholder = "Dende Eventos",
                isError = state.nomeError != null,
                errorMessage = state.nomeError
            )

            DendeDropdownField(
                value = state.genero,
                onValueChange = { onStateChange(state.copy(genero = it)) },
                options = listOf("Masculino", "Feminino", "Não Binário", "Prefiro não dizer"),
                title = "Gênero",
                placeholder = "Selecione..."
            )

            DendeDatePickerField(
                value = state.dataNascimento,
                onDateSelected = { onStateChange(state.copy(dataNascimento = it)) },
                title = "Data de Nascimento",
                placeholder = "DD/MM/AAAA"
            )

            DendeCheckBox(
                checked = state.aceitouTermos,
                onCheckedChange = { novoValor ->
                    onStateChange(state.copy(aceitouTermos = novoValor, aceitouTermosError = false))
                },
                isError = state.aceitouTermosError
            )
        }

        DendeFooterButton(
            onPrimaryClick = {  },
            onSecondaryClick = {  }
        )
    }
}

// CONFERÊNCIA PESSOA JURÍDICA
@Composable
fun ConferenciaPJ(state: CadastroUiState) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 18.dp)) {


        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Confira seus dados",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter
            )

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
            onPrimaryClick = {

            },
            onSecondaryClick = {

            }
        )
    }
}

// CONFERÊNCIA PESSOA FÍSICA
@Composable
fun ConferenciaPF(state: CadastroUiState) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 18.dp)) {


        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Confira seus dados",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter
            )

            ItemConferencia("E-mail", state.email)
            ItemConferencia("Nome Completo", state.nome)
            ItemConferencia("Gênero", state.genero)
            ItemConferencia("Nascimento", state.dataNascimento)

            Spacer(modifier = Modifier.height(16.dp))
        }
        DendeFooterButton(
            onPrimaryClick = {

            },
            onSecondaryClick = {

            }
        )
    }
}

@Composable
fun ItemConferencia(label: String, valor: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = if (valor.isNotBlank()) valor else "Não preenchido",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Inter
        )
    }
}

@Composable
fun CadastroConcluidoDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
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
fun ErroEmailDuplicadoDialog(
    onTentarNovamente: () -> Unit,
    onIrParaLogin: () -> Unit
) {
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
fun ErroIdadeMinimaDialog(
    onTentarNovamente: () -> Unit,){
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
fun ErroCamposNaoPreenchidosDialog(
    onTentarNovamente: () -> Unit,){
    DendeNotificationDialog(
        title = "Atenção",
        description =  "Todos os campos devem ser preenchidos!",
        iconRes = R.drawable.error_ico,
        confirmText = "Tentar Novamente",
        onConfirm = onTentarNovamente,
        onDismiss = onTentarNovamente
    )
}

// PREVIEWS

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "0. Tela de Escolha Inicial")
@Composable
fun FullPreview_EscolhaUsuario() {
    MaterialTheme {
        CadastrarOrganizadorScreen()
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "1. Passo 1 - Pergunta Empresa")
@Composable
fun FullPreview_Passo1_IsEmpresa() {
    MaterialTheme {
        CadastrarOrganizadorScreen(
            initialState = CadastroUiState(tipoUsuario = "ORGANIZADOR", currentStep = 1)
        )
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "2. Passo 2 (PJ) - Dados Empresariais")
@Composable
fun FullPreview_Passo2_DadosEmpresariais() {
    MaterialTheme {
        CadastrarOrganizadorScreen(
            initialState = CadastroUiState(tipoUsuario = "ORGANIZADOR", isEmpresa = true, currentStep = 2)
        )
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "3. Passo 2 (PF) - Dados Pessoais (Pulou a Empresa)")
@Composable
fun FullPreview_Passo2_DadosPessoais_PF() {
    MaterialTheme {
        CadastrarOrganizadorScreen(
            initialState = CadastroUiState(tipoUsuario = "ORGANIZADOR", isEmpresa = false, currentStep = 2)
        )
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "4. Passo 3 (PJ) - Dados Pessoais")
@Composable
fun FullPreview_Passo3_DadosPessoais_PJ() {
    MaterialTheme {
        CadastrarOrganizadorScreen(
            initialState = CadastroUiState(tipoUsuario = "ORGANIZADOR", isEmpresa = true, currentStep = 3)
        )
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "5. Passo Final (PF) - Conferência Física")
@Composable
fun FullPreview_Passo3_Conferencia_PF() {
    MaterialTheme {
        CadastrarOrganizadorScreen(
            initialState = CadastroUiState(tipoUsuario = "ORGANIZADOR", isEmpresa = false, currentStep = 3)
        )
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "6. Passo Final (PJ) - Conferência Jurídica")
@Composable
fun FullPreview_Passo4_Conferencia_PJ() {
    MaterialTheme {
        CadastrarOrganizadorScreen(
            initialState = CadastroUiState(tipoUsuario = "ORGANIZADOR", isEmpresa = true, currentStep = 4)
        )
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "Conferência com Pop-up de Sucesso")
@Composable
fun PreviewSucessoSobreConferencia() {
    MaterialTheme {
        val stateComSucesso = CadastroUiState(
            tipoUsuario = "ORGANIZADOR",
            currentStep = 4,
            isEmpresa = true,
            showSuccessDialog = true
        )
        CadastrarOrganizadorScreen(initialState = stateComSucesso)
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "Erro: Email Duplicado (Sobre Dados Pessoais)")
@Composable
fun PreviewErroEmailDuplicadoSobreTela() {
    MaterialTheme {
        val stateComErro = CadastroUiState(
            tipoUsuario = "ORGANIZADOR",
            currentStep = 3, // Tela de Dados Pessoais (PJ) onde o e-mail é preenchido
            isEmpresa = true,
            erroAtualDialog = TipoErroDialog.EMAIL_DUPLICADO
        )
        CadastrarOrganizadorScreen(initialState = stateComErro)
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "Erro: Idade Mínima (Sobre Dados Pessoais)")
@Composable
fun PreviewErroIdadeMinimaSobreTela() {
    MaterialTheme {
        val stateComErro = CadastroUiState(
            tipoUsuario = "ORGANIZADOR",
            currentStep = 3, // Tela de Dados Pessoais onde a data de nascimento é colocada
            isEmpresa = true,
            erroAtualDialog = TipoErroDialog.IDADE_MINIMA
        )
        CadastrarOrganizadorScreen(initialState = stateComErro)
    }
}

@Preview(device = Devices.PIXEL_7, showSystemUi = true, name = "Erro: Campos Vazios (Sobre Dados Empresariais)")
@Composable
fun PreviewErroCamposVaziosSobreTela() {
    MaterialTheme {
        val stateComErro = CadastroUiState(
            tipoUsuario = "ORGANIZADOR",
            currentStep = 2,
            isEmpresa = true,
            erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS
        )
        CadastrarOrganizadorScreen(initialState = stateComErro)
    }
}