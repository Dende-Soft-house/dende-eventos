package br.com.dende.dendeeventos.ui.cadastro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.components.DendeTextField
import br.com.dende.dendeeventos.ui.theme.BlackLinear
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarOrganizadorScreen() {
    var state by remember { mutableStateOf(CadastroUiState()) }

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
                FilledIconButton(
                    onClick = {
                        if (state.tipoUsuario != null) {
                            if (state.currentStep > 1) {
                                state = state.copy(currentStep = state.currentStep - 1)
                            } else {
                                state = state.copy(tipoUsuario = null, isEmpresa = null)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                        .border(BorderStroke(1.dp, BlackLinear), shape = RoundedCornerShape(15.dp)),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.Transparent),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_butto_svg),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                }
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
                // BARRA DE PROGRESSO DO ORGANIZADOR
                Text(
                    text = "Passo ${state.currentStep} de ${state.totalSteps - 1}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Orange
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = state.currentStep / state.totalSteps.toFloat(),
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = Orange,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    when (state.currentStep) {
                        1 -> IsEmpresa(state) { state = it }
                        2 -> if (state.isEmpresa == true) CadastrarDadosEmpresariais(state) { state = it } else CadastrarDadosPessoais(state) { state = it }
                        3 -> if (state.isEmpresa == true) CadastrarDadosPessoais(state) { state = it } else ConferenciaPF(state)
                        4 -> ConferenciaPJ(state)
                    }
                }

                // ==========================================
                // SUBSTITUA O SEU BUTTON POR ESTA COLUMN ABAIXO
                // ==========================================
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // BOTÃO PRINCIPAL (LARANJA)
                    Button(
                        onClick = {
                            if (state.currentStep == 1 && state.isEmpresa == null) return@Button

                            if (state.currentStep < state.totalSteps) {
                                state = state.copy(currentStep = state.currentStep + 1)
                            } else {
                                println("ENVIAR PARA A VIEWMODEL!")
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.currentStep == 1 && state.isEmpresa == null) Color.Gray else Orange,
                            contentColor = White
                        ),
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) {
                        Text(
                            text = if (state.currentStep == state.totalSteps) "Finalizar Cadastro" else "Continuar",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Inter
                        )
                    }

                    // BOTÃO VOLTAR (APENAS TEXTO - IGUAL IMAGEM 3)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = {
                            // Lógica de voltar o passo ou cancelar
                            if (state.currentStep > 1) {
                                state = state.copy(currentStep = state.currentStep - 1)
                            } else {
                                state = state.copy(tipoUsuario = null, isEmpresa = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Voltar",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Inter
                        )
                    }
                }
                // ==========================================
                // FIM DO RODAPÉ
                // ==========================================

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
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Participar de Eventos", fontSize = 23.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        }

        OutlinedButton(
            onClick = { onStateChange(state.copy(tipoUsuario = "ORGANIZADOR")) },
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange),
            border = BorderStroke(2.dp, Orange),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Organizar Eventos", fontSize = 23.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        }
    }
}

@Composable
fun IsEmpresa(state: CadastroUiState, onStateChange: (CadastroUiState) -> Unit) {
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
                modifier = Modifier.width(100.dp).height(56.dp)
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
                modifier = Modifier.width(100.dp).height(56.dp)
            ) {
                Text("Não", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color.Black)
            }
        }
    }
}

@Composable
fun CadastrarDadosEmpresariais(state: CadastroUiState, onStateChange: (CadastroUiState) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Dados empresariais", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        DendeTextField(value = state.cnpj, onValueChange = { onStateChange(state.copy(cnpj = it)) }, label = "CNPJ")
        DendeTextField(value = state.razaoSocial, onValueChange = { onStateChange(state.copy(razaoSocial = it)) }, label = "Razão Social")
        DendeTextField(value = state.nomeFantasia, onValueChange = { onStateChange(state.copy(nomeFantasia = it)) }, label = "Nome Fantasia")
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CadastrarDadosPessoais(state: CadastroUiState, onStateChange: (CadastroUiState) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Dados Pessoais", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        DendeTextField(value = state.email, onValueChange = { onStateChange(state.copy(email = it)) }, label = "E-mail")
        DendeTextField(value = state.senha, onValueChange = { onStateChange(state.copy(senha = it)) }, label = "Senha")
        DendeTextField(value = state.nomeProprietario, onValueChange = { onStateChange(state.copy(nomeProprietario = it)) }, label = "Nome completo")
        DendeTextField(value = state.genero, onValueChange = { onStateChange(state.copy(genero = it)) }, label = "Gênero")
        DendeTextField(value = state.dataNascimento, onValueChange = { onStateChange(state.copy(dataNascimento = it)) }, label = "Data de nascimento (DD/MM/AAAA)")
        Spacer(modifier = Modifier.height(16.dp))
    }
}

// CONFERÊNCIA PESSOA JURÍDICA (Imagem 5)
@Composable
fun ConferenciaPJ(state: CadastroUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Confira seus dados", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        Text("Por favor, revise as informações antes de finalizar.", color = Color.Gray)

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text("Dados Empresariais", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Orange)
        ItemConferencia("CNPJ", state.cnpj)
        ItemConferencia("Razão Social", state.razaoSocial)
        ItemConferencia("Nome Fantasia", state.nomeFantasia)

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text("Dados Pessoais", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Orange)
        ItemConferencia("E-mail", state.email)
        ItemConferencia("Nome Completo", state.nomeProprietario)
        ItemConferencia("Gênero", state.genero)
        ItemConferencia("Nascimento", state.dataNascimento)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// CONFERÊNCIA PESSOA FÍSICA (Imagem 6 - Não tem bloco de empresa)
@Composable
fun ConferenciaPF(state: CadastroUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Confira seus dados", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
        Text("Por favor, revise as informações antes de finalizar.", color = Color.Gray)

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text("Dados Pessoais", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Orange)
        ItemConferencia("E-mail", state.email)
        ItemConferencia("Nome Completo", state.nomeProprietario)
        ItemConferencia("Gênero", state.genero)
        ItemConferencia("Nascimento", state.dataNascimento)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ItemConferencia(label: String, valor: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Text(
            text = if (valor.isNotBlank()) valor else "Não preenchido",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Inter
        )
    }
}


// PREVIEWS


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Visualização em Tela Cheia"
)
@Composable
fun PreviewCadastrarUsuario() {
    MaterialTheme {
        CadastrarUsuario(state = CadastroUiState(), onStateChange = {})
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Visualização em Tela Cheia"
)
@Composable
fun PreviewIsEmpresa() {
    MaterialTheme {
        IsEmpresa(state = CadastroUiState(), onStateChange = {})
    }
}
/*
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Visualização em Tela Cheia"
)
@Composable
fun PreviewCadastrarDadosEmpresariais() {
    MaterialTheme {
        CadastrarDadosEmpresariais(
            state = CadastroUiState(cnpj = "00.000.000/0001-00"),
            onStateChange = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Visualização em Tela Cheia"
)
@Composable
fun PreviewCadastrarDadosPessoais() {
    MaterialTheme {
        CadastrarDadosPessoais(state = CadastroUiState(), onStateChange = {})
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Visualização em Tela Cheia"
)
@Composable
fun PreviewConferenciaPJ() {
    MaterialTheme {
        val mockPJ = CadastroUiState(
            isEmpresa = true,
            cnpj = "12.345.678/0001-90",
            razaoSocial = "Dendê Eventos LTDA",
            nomeProprietario = "Equipe Porto",
            email = "porto@dende.com"
        )
        ConferenciaPJ(state = mockPJ)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Visualização em Tela Cheia"
)
@Composable
fun PreviewConferenciaPF() {
    MaterialTheme {
        val mockPF = CadastroUiState(
            isEmpresa = false,
            nomeProprietario = "Caos Tech",
            email = "caos@dende.com",
            genero = "Masculino"
        )
        ConferenciaPF(state = mockPF)
    }
}

// Preview da tela completa
@Preview(showBackground = true, showSystemUi = true, name = "Fluxo Completo")
@Composable
fun CadastrarOrganizadorPreview() {
    MaterialTheme {
        CadastrarOrganizadorScreen()
    }
}*/


@Preview(showBackground = true, showSystemUi = true, name = "Conferindo Passo 1")
@Composable
fun PreviewPasso1Completo() {
    // Importante usar o teu tema aqui para as cores (Orange, etc) aparecerem
    MaterialTheme {
        // Criamos um estado "fake" já dentro do fluxo de Organizador
        val estadoDeTeste = CadastroUiState(
            tipoUsuario = "ORGANIZADOR",
            currentStep = 1
        )

        // Chamamos a tua Screen de forma estática para o Preview não se perder
        StaticCadastrarOrganizadorScreen(state = estadoDeTeste)
    }
}

// Esta função ajuda o Preview a não ficar em branco
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticCadastrarOrganizadorScreen(
    state: CadastroUiState,
    onStateChange: (CadastroUiState) -> Unit = {}
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // 1. CABEÇALHO
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // REGRA: O botão de ícone só aparece na tela inicial de decisão (tipoUsuario == null)
                // Ele "some" assim que entramos no fluxo de formulário do organizador.
                if (state.tipoUsuario == null) {
                    FilledIconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                            .border(BorderStroke(1.dp, BlackLinear), shape = RoundedCornerShape(15.dp)),
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.Transparent),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_butto_svg),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                }

                Text(
                    text = "Registrar-me",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. CONTEÚDO DINÂMICO
            if (state.tipoUsuario == null) {
                CadastrarUsuario(state = state, onStateChange = onStateChange)
            } else if (state.tipoUsuario == "ORGANIZADOR") {

                val isConferencia = (state.isEmpresa == true && state.currentStep == 4) ||
                        (state.isEmpresa == false && state.currentStep == 3)

                // Barra de progresso (Esconde na conferência)
                if (!isConferencia) {
                    Text(
                        // Adicionamos o "- 1" aqui igual você fez na tela principal!
                        text = "Passo ${state.currentStep} de ${state.totalSteps - 1}",
                        style = MaterialTheme.typography.labelLarge,
                        color = Orange
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        // A barra de progresso também precisa dessa matemática para encher 100% no último passo de form
                        progress = state.currentStep / (state.totalSteps - 1).toFloat(),
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = Orange,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    when (state.currentStep) {
                        1 -> IsEmpresa(state, onStateChange)
                        2 -> if (state.isEmpresa == true) CadastrarDadosEmpresariais(state, onStateChange) else CadastrarDadosPessoais(state, onStateChange)
                        3 -> if (state.isEmpresa == true) CadastrarDadosPessoais(state, onStateChange) else ConferenciaPF(state)
                        4 -> ConferenciaPJ(state)
                    }
                }

                // 3. RODAPÉ (MODELO IMAGEM 3: Botão + Texto embaixo)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    // BOTÃO VOLTAR
                    TextButton(
                        onClick = {
                            // Lógica de Voltar usando onStateChange
                            if (state.currentStep > 1) {
                                onStateChange(state.copy(currentStep = state.currentStep - 1))
                            } else {
                                onStateChange(state.copy(tipoUsuario = null, isEmpresa = null))
                            }
                        },
                        modifier = Modifier.width(150.dp).height(56.dp)
                    ) {
                        Text(
                            text = "Voltar",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Inter
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // BOTÃO PRINCIPAL
                    Button(
                        onClick = {
                            // Lógica de Avançar usando onStateChange
                            if (state.currentStep == 1 && state.isEmpresa == null) return@Button

                            if (state.currentStep < state.totalSteps) {
                                onStateChange(state.copy(currentStep = state.currentStep + 1))
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.currentStep == 1 && state.isEmpresa == null) BlackLinear else Orange,
                            contentColor = White
                        ),
                        modifier = Modifier.width(150.dp).height(56.dp)
                    ) {
                        Text(
                            text = if (isConferencia) "Finalizar Cadastro" else "Continuar",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Inter
                        )
                    }
                }
            }
        }
    }
}
