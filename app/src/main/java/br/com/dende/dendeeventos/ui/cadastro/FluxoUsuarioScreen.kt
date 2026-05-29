package br.com.dende.dendeeventos.ui.cadastro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel

enum class CadastroStep {
    PERFIL, DADOS, CONFIRMACAO
}

@Composable
fun FluxoUsuarioScreen(
    state: CadastroUiState.CadastroUsuarioUiState,
    viewModel: CadastroViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Seu cabeçalho de voltar
        IconButton(onClick = { viewModel.voltarPasso() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // O seu fluxo interno
        if (state.currentStep == 1) {
            DadosPessoaisForm(
                email = state.email,
                onEmailChange = { viewModel.updateEmailUsuario(it) },
                senha = state.senha,
                onSenhaChange = { viewModel.updateSenhaUsuario(it) },
                nome = state.nome,
                onNomeChange = { viewModel.updateNomeUsuario(it) },
                onContinue = { viewModel.avancarPasso() },
                onBack = { viewModel.voltarPasso() }
            )
        } else {
            // Tela de Confirmação (Step 2)
            Text("Tela de Confirmação do Usuário")
        }
    }
}

@Composable
fun PerfilSelection(onOptionSelected: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Registrar-me",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Para qual finalidade gostaria de criar sua conta?",
            color = SoftDarkish,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onOptionSelected,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonLinear),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Participar de eventos", color = WhiteText)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onOptionSelected,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Orange),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Organizar eventos", color = Black)
        }
    }
}

@Composable
fun DadosPessoaisForm(
    email: String, onEmailChange: (String) -> Unit,
    senha: String, onSenhaChange: (String) -> Unit,
    nome: String, onNomeChange: (String) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    Column {
        Text("Registrar-me", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Passo 1 de 2", fontSize = 12.sp, color = Color.Gray)

        // Barra de progresso customizada
        LinearProgressIndicator(
            progress = { 0.5f },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            color = ButtonLinear,
            trackColor = Grey2,
        )

        Text("Dados Pessoais", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))

        CustomTextField(label = "Email", value = email, onValueChange = onEmailChange, placeholder = "exemplo@exemplo.com")
        CustomTextField(label = "Senha", value = senha, onValueChange = onSenhaChange, isPassword = true)
        CustomTextField(label = "Nome", value = nome, onValueChange = onNomeChange, placeholder = "Exemplo da Silva")

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(8.dp))
                Text("VOLTAR")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                onClick = onContinue,
                modifier = Modifier.weight(1.4f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonLinear),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("CONTINUAR")
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.LightGray) },
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Orange,
                focusedBorderColor = Orange
            )
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CadastroUsuarioPreview() {
    DendeeventosTheme {
        FluxoUsuarioScreen(
            state = CadastroUiState.CadastroUsuarioUiState(),
            viewModel = viewModel()
        )
    }
}