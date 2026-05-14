package br.com.dende.dendeeventos.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.core.designsystem.components.CustomTextField
import br.com.dende.dendeeventos.core.designsystem.components.DateTimePicker
import br.com.dende.dendeeventos.core.designsystem.components.FormLabel
import br.com.dende.dendeeventos.core.designsystem.components.ProgressBarStep
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.domain.ModalidadeEvento
import br.com.dende.dendeeventos.domain.TipoEvento
import br.com.dende.dendeeventos.ui.theme.DendeeventosTheme
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacoesBasicasScreen() {
    var nome by remember { mutableStateOf("") }
    var paginaWeb by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }

    var erroNome by remember { mutableStateOf<String?>(null) }
    var erroPaginaWeb by remember { mutableStateOf<String?>(null) }
    var erroDescricao by remember { mutableStateOf<String?>(null) }
    var erroDataInicio by remember { mutableStateOf(false) }
    var erroDataFim by remember { mutableStateOf(false) }

    var mostrarDataPickerInicio by remember { mutableStateOf(false) }
    var mostrarHoraPickerInicio by remember { mutableStateOf(false) }
    var mostrarDataPickerFim by remember { mutableStateOf(false) }
    var mostrarHoraPickerFim by remember { mutableStateOf(false) }

    val datePickerStateInicio = rememberDatePickerState()
    val timePickerStateInicio = rememberTimePickerState()
    val datePickerStateFim = rememberDatePickerState()
    val timePickerStateFim = rememberTimePickerState()

    val corPrimaria = Color(0xFFF76B10)

    fun validarCampos() {
        erroNome = if (nome.length < 3) "Nome curto ou inválido" else null
        erroPaginaWeb = if (paginaWeb.isNotEmpty() && !paginaWeb.contains(".")) "Página web inválida" else null
        erroDescricao = if (descricao.length < 5) "Descrição curta ou inválida" else null
        erroDataInicio = dataInicio.isEmpty()
        erroDataFim = dataFim.isEmpty()
    }

    if (mostrarDataPickerInicio) {
        DatePickerDialog(
            onDismissRequest = { mostrarDataPickerInicio = false },
            confirmButton = {
                TextButton(onClick = {
                    if (datePickerStateInicio.selectedDateMillis != null) {
                        mostrarDataPickerInicio = false
                        mostrarHoraPickerInicio = true
                    }
                }) { Text("Próximo", color = corPrimaria, fontWeight = FontWeight.Bold) }
            }
        ) { DatePicker(state = datePickerStateInicio) }
    }

    if (mostrarHoraPickerInicio) {
        DateTimePicker(
            onDismiss = { mostrarHoraPickerInicio = false },
            onConfirm = {
                val cal = Calendar.getInstance().apply {
                    timeInMillis = datePickerStateInicio.selectedDateMillis ?: 0L
                    set(Calendar.HOUR_OF_DAY, timePickerStateInicio.hour)
                    set(Calendar.MINUTE, timePickerStateInicio.minute)
                }
                dataInicio = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(cal.time)
                erroDataInicio = false
                mostrarHoraPickerInicio = false
            }
        ) { TimePicker(state = timePickerStateInicio) }
    }

    if (mostrarDataPickerFim) {
        DatePickerDialog(
            onDismissRequest = { mostrarDataPickerFim = false },
            confirmButton = {
                TextButton(onClick = {
                    if (datePickerStateFim.selectedDateMillis != null) {
                        mostrarDataPickerFim = false
                        mostrarHoraPickerFim = true
                    }
                }) { Text("Próximo", color = corPrimaria, fontWeight = FontWeight.Bold) }
            }
        ) { DatePicker(state = datePickerStateFim) }
    }

    if (mostrarHoraPickerFim) {
        DateTimePicker(
            onDismiss = { mostrarHoraPickerFim = false },
            onConfirm = {
                val cal = Calendar.getInstance().apply {
                    timeInMillis = datePickerStateFim.selectedDateMillis ?: 0L
                    set(Calendar.HOUR_OF_DAY, timePickerStateFim.hour)
                    set(Calendar.MINUTE, timePickerStateFim.minute)
                }
                dataFim = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(cal.time)
                erroDataFim = false
                mostrarHoraPickerFim = false
            }
        ) { TimePicker(state = timePickerStateFim) }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Informações Básicas", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Passo 1 de 4", color = Color(0xFF8A8A8A), fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(6.dp))

            ProgressBarStep(step = 1, totalSteps = 4)

            Spacer(modifier = Modifier.height(32.dp))

            FormLabel("Nome", true)
            CustomTextField(nome, { nome = it; erroNome = null }, "Digite nome do evento", isError = erroNome != null, errorMessage = erroNome)

            Spacer(modifier = Modifier.height(24.dp))

            FormLabel("Página Web", false)
            CustomTextField(paginaWeb, { paginaWeb = it; erroPaginaWeb = null }, "Digite página do evento", isError = erroPaginaWeb != null, errorMessage = erroPaginaWeb)

            Spacer(modifier = Modifier.height(24.dp))

            FormLabel("Descrição", true)
            CustomTextField(descricao, { descricao = it; erroDescricao = null }, "Digite descrição do evento", isError = erroDescricao != null, errorMessage = erroDescricao)

            Spacer(modifier = Modifier.height(24.dp))

            FormLabel("Data de início", true)
            Box(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(dataInicio, { }, "Selecione data e hora", icon = Icons.Default.DateRange, isError = erroDataInicio)
                Box(modifier = Modifier.matchParentSize().clickable { mostrarDataPickerInicio = true })
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormLabel("Data de fim", true)
            Box(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(dataFim, { }, "Selecione data e hora", icon = Icons.Default.DateRange, isError = erroDataFim)
                Box(modifier = Modifier.matchParentSize().clickable { mostrarDataPickerFim = true })
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { validarCampos() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2D35))
            ) {
                Text("CONTINUAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InformacoesBasicasScreenPreview() {
    InformacoesBasicasScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacoesAdicionaisScreen(onBack: () -> Unit, onNext: () -> Unit) {
    var tipoSelecionado by remember { mutableStateOf<TipoEvento?>(null) }
    var modalidadeSelecionada by remember { mutableStateOf<ModalidadeEvento?>(null) }
    var eventoPrincipal by remember { mutableStateOf("") }
    var capacidadeMaxima by remember { mutableStateOf("") }
    var localEvento by remember { mutableStateOf("") }

    var expandirTipo by remember { mutableStateOf(false) }
    var expandirEventoPrincipal by remember { mutableStateOf(false) }
    var expandirModalidade by remember { mutableStateOf(false) }

    var erroTipo by remember { mutableStateOf(false) }
    var erroModalidade by remember { mutableStateOf(false) }
    var erroCapacidade by remember { mutableStateOf<String?>(null) }
    var erroLocal by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Informações Adicionais", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Passo 2 de 4", color = Color(0xFF8A8A8A), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                ProgressBarStep(step = 2, totalSteps = 4)

                Spacer(modifier = Modifier.height(32.dp))

                FormLabel("Tipo", true)
                ExposedDropdownMenuBox(expanded = expandirTipo, onExpandedChange = { expandirTipo = it }) {
                    OutlinedTextField(
                        value = tipoSelecionado?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "",
                        onValueChange = {}, readOnly = true,
                        placeholder = { Text("Selecione tipo do evento", color = Color(0xFF9E9E9E)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTipo) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        isError = erroTipo,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (erroTipo) Color.Red else Color(0xFFE0E0E0),
                            focusedBorderColor = Color.Black
                        )
                    )
                    ExposedDropdownMenu(expanded = expandirTipo, onDismissRequest = { expandirTipo = false }) {
                        TipoEvento.entries.forEach { tipo ->
                            DropdownMenuItem(
                                text = { Text(tipo.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                onClick = { tipoSelecionado = tipo; erroTipo = false; expandirTipo = false }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Evento Principal", false)
                ExposedDropdownMenuBox(expanded = expandirEventoPrincipal, onExpandedChange = { expandirEventoPrincipal = it }) {
                    OutlinedTextField(
                        value = eventoPrincipal,
                        onValueChange = {}, readOnly = true,
                        placeholder = { Text("Associe o evento a um principal (opcional)", color = Color(0xFF9E9E9E)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirEventoPrincipal) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color(0xFFE0E0E0))
                    )
                    ExposedDropdownMenu(expanded = expandirEventoPrincipal, onDismissRequest = { expandirEventoPrincipal = false }) {
                        DropdownMenuItem(text = { Text("IntegraSI 2026.1") }, onClick = { eventoPrincipal = "IntegraSI 2026.1"; expandirEventoPrincipal = false })
                        DropdownMenuItem(text = { Text("Nenhum") }, onClick = { eventoPrincipal = "Nenhum"; expandirEventoPrincipal = false })
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Modalidade", true)
                ExposedDropdownMenuBox(expanded = expandirModalidade, onExpandedChange = { expandirModalidade = it }) {
                    OutlinedTextField(
                        value = modalidadeSelecionada?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "",
                        onValueChange = {}, readOnly = true,
                        placeholder = { Text("Selecione modalidade do evento", color = Color(0xFF9E9E9E)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirModalidade) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        isError = erroModalidade,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (erroModalidade) Color.Red else Color(0xFFE0E0E0),
                            focusedBorderColor = Color.Black
                        )
                    )
                    ExposedDropdownMenu(expanded = expandirModalidade, onDismissRequest = { expandirModalidade = false }) {
                        ModalidadeEvento.entries.forEach { mod ->
                            DropdownMenuItem(
                                text = { Text(mod.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                onClick = { modalidadeSelecionada = mod; erroModalidade = false; expandirModalidade = false }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Capacidade Máxima", true)
                CustomTextField(
                    value = capacidadeMaxima,
                    onValueChange = { capacidadeMaxima = it; erroCapacidade = null },
                    placeholder = "Digite capacidade máxima de pessoas",
                    isError = erroCapacidade != null,
                    errorMessage = erroCapacidade,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Local", true)
                CustomTextField(
                    value = localEvento,
                    onValueChange = { localEvento = it; erroLocal = null },
                    placeholder = "Digite local ou link do evento",
                    isError = erroLocal != null,
                    errorMessage = erroLocal
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        erroTipo = tipoSelecionado == null
                        erroModalidade = modalidadeSelecionada == null
                        erroCapacidade = if (capacidadeMaxima.isEmpty()) "Campo obrigatório" else null
                        erroLocal = if (localEvento.isEmpty()) "Campo obrigatório" else null

                        if (!erroTipo && !erroModalidade && erroCapacidade == null && erroLocal == null) {
                            onNext()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2D35))
                ) {
                    Text("CONTINUAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InformacoesAdicionaisScreenPreview() {
    InformacoesAdicionaisScreen({}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaturamentoScreen(onBack: () -> Unit, onNext: () -> Unit) {
    var valorIngresso by remember { mutableStateOf("") }
    var aceitaDevolucoes by remember { mutableStateOf(true) }
    var taxaDevolucao by remember { mutableStateOf("") }

    var erroValor by remember { mutableStateOf<String?>(null) }
    var erroTaxa by remember { mutableStateOf<String?>(null) }

    val corLaranja = Color(0xFFF76B10)
    val corEscura = Color(0xFF2E2D35)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Faturamento", fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = Inter) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Passo 3 de 4", color = Color(0xFF8A8A8A), fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Inter)
                Spacer(modifier = Modifier.height(6.dp))
                ProgressBarStep(step = 3, totalSteps = 4)

                Spacer(modifier = Modifier.height(32.dp))

                FormLabel("Valor de Ingresso", true)
                CustomTextField(
                    value = valorIngresso,
                    onValueChange = { valorIngresso = it; erroValor = null },
                    placeholder = "Digite valor do ingresso do evento",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = erroValor != null,
                    errorMessage = erroValor
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Aceita Devoluções?", true)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .background(if (aceitaDevolucoes) corLaranja else Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, if (aceitaDevolucoes) corLaranja else Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                            .clickable { aceitaDevolucoes = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = if (aceitaDevolucoes) Color.White else Color.Black)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .background(if (!aceitaDevolucoes) corEscura else Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, if (!aceitaDevolucoes) corEscura else Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                            .clickable { aceitaDevolucoes = false },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = if (!aceitaDevolucoes) Color.White else Color.Black)
                    }
                }

                if (aceitaDevolucoes) {
                    Spacer(modifier = Modifier.height(24.dp))
                    FormLabel("Taxa de Devolução", true)
                    CustomTextField(
                        value = taxaDevolucao,
                        onValueChange = { taxaDevolucao = it; erroTaxa = null },
                        placeholder = "Digite taxa de devolução do ingresso",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = erroTaxa != null,
                        errorMessage = erroTaxa
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        erroValor = if (valorIngresso.isEmpty()) "Campo obrigatório" else null

                        if (aceitaDevolucoes) {
                            val taxaNumerica = taxaDevolucao.replace(",", ".").toDoubleOrNull()
                            erroTaxa = when {
                                taxaDevolucao.isEmpty() -> "Campo obrigatório"
                                taxaNumerica == null || taxaNumerica < 0 || taxaNumerica > 100 -> "Taxa inválida"
                                else -> null
                            }
                        } else {
                            erroTaxa = null
                        }

                        if (erroValor == null && erroTaxa == null) {
                            onNext()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = corEscura)
                ) {
                    Text("CONTINUAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = Inter)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FaturamentoScreenPreview() {
    FaturamentoScreen({}, {})
}