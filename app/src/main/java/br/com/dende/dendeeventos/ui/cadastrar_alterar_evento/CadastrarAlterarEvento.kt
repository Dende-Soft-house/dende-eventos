package br.com.dende.dendeeventos.ui.cadastrar_alterar_evento

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.components.CustomTextField
import br.com.dende.dendeeventos.core.designsystem.components.DateTimePicker
import br.com.dende.dendeeventos.core.designsystem.components.DendeButton
import br.com.dende.dendeeventos.core.designsystem.components.FormLabel
import br.com.dende.dendeeventos.core.designsystem.components.FormSwitch
import br.com.dende.dendeeventos.core.designsystem.components.ProgressBarStep
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.domain.ModalidadeEvento
import br.com.dende.dendeeventos.domain.TipoEvento
import br.com.dende.dendeeventos.ui.theme.Black
import br.com.dende.dendeeventos.ui.theme.ButtonLinear
import br.com.dende.dendeeventos.ui.theme.Error
import br.com.dende.dendeeventos.ui.theme.Grey
import br.com.dende.dendeeventos.ui.theme.Grey2
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.SoftDarkish
import br.com.dende.dendeeventos.ui.theme.White
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacoesBasicasScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
    viewModel: CadastrarAlterarEventoViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    var mostrarDataPickerInicio by remember { mutableStateOf(false) }
    var mostrarHoraPickerInicio by remember { mutableStateOf(false) }
    var mostrarDataPickerFim by remember { mutableStateOf(false) }
    var mostrarHoraPickerFim by remember { mutableStateOf(false) }

    val datePickerStateInicio = rememberDatePickerState()
    val timePickerStateInicio = rememberTimePickerState()
    val datePickerStateFim = rememberDatePickerState()
    val timePickerStateFim = rememberTimePickerState()

    val datePickerColors = DatePickerDefaults.colors(
        containerColor = White,
        titleContentColor = Black,
        headlineContentColor = Black,
        weekdayContentColor = Color.Black,
        selectedDayContainerColor = Orange,
        selectedDayContentColor = White,
        todayContentColor = Orange,
        todayDateBorderColor = Orange,
        dayContentColor = Black
    )

    val timePickerColors = TimePickerDefaults.colors(
        clockDialColor = Grey,
        clockDialSelectedContentColor = White,
        clockDialUnselectedContentColor = Black,
        selectorColor = Orange,
        timeSelectorSelectedContainerColor = Orange,
        timeSelectorUnselectedContainerColor = Grey,
        timeSelectorSelectedContentColor = White,
        timeSelectorUnselectedContentColor = Black
    )

    if (mostrarDataPickerInicio) {
        DatePickerDialog(
            onDismissRequest = { mostrarDataPickerInicio = false }, confirmButton = {
                TextButton(
                    onClick = {
                        if (datePickerStateInicio.selectedDateMillis != null) {
                            mostrarDataPickerInicio = false
                            mostrarHoraPickerInicio = true
                        }
                    }) {
                    Text(
                        "Próximo", color = Orange, fontWeight = FontWeight.Bold, fontFamily = Inter
                    )
                }
            }, dismissButton = {
                TextButton(onClick = { mostrarDataPickerInicio = false }) {
                    Text("Cancelar", color = ButtonLinear, fontFamily = Inter)
                }
            }, colors = datePickerColors
        ) {
            DatePicker(state = datePickerStateInicio, colors = datePickerColors)
        }
    }

    if (mostrarHoraPickerInicio) {
        DateTimePicker(onDismiss = { mostrarHoraPickerInicio = false }, onConfirm = {
            val dataSel = datePickerStateInicio.selectedDateMillis ?: 0L
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = dataSel
                set(Calendar.HOUR_OF_DAY, timePickerStateInicio.hour)
                set(Calendar.MINUTE, timePickerStateInicio.minute)
            }
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val dataFormatada = simpleDateFormat.format(cal.time)
            viewModel.updateState { it.copy(dataInicio = dataFormatada, erroDataInicio = false) }
            mostrarHoraPickerInicio = false
        }) {
            TimePicker(state = timePickerStateInicio, colors = timePickerColors)
        }
    }

    if (mostrarDataPickerFim) {
        DatePickerDialog(
            onDismissRequest = { mostrarDataPickerFim = false }, confirmButton = {
                TextButton(
                    onClick = {
                        if (datePickerStateFim.selectedDateMillis != null) {
                            mostrarDataPickerFim = false
                            mostrarHoraPickerFim = true
                        }
                    }) {
                    Text(
                        "Próximo", color = Orange, fontWeight = FontWeight.Bold, fontFamily = Inter
                    )
                }
            }, dismissButton = {
                TextButton(onClick = { mostrarDataPickerFim = false }) {
                    Text("Cancelar", color = ButtonLinear, fontFamily = Inter)
                }
            }, colors = datePickerColors
        ) {
            DatePicker(state = datePickerStateFim, colors = datePickerColors)
        }
    }

    if (mostrarHoraPickerFim) {
        DateTimePicker(onDismiss = { mostrarHoraPickerFim = false }, onConfirm = {
            val dataSelFim = datePickerStateFim.selectedDateMillis ?: 0L
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = dataSelFim
                set(Calendar.HOUR_OF_DAY, timePickerStateFim.hour)
                set(Calendar.MINUTE, timePickerStateFim.minute)
            }
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val dataFormatada = simpleDateFormat.format(cal.time)
            viewModel.updateState { it.copy(dataFim = dataFormatada, erroDataFim = false) }
            mostrarHoraPickerFim = false
        }) {
            TimePicker(state = timePickerStateFim, colors = timePickerColors)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Informações Básicas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = Inter,
                        color = Black
                    )
                }, navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Passo 1 de 4",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(6.dp))

                ProgressBarStep(step = 1, totalSteps = 4)

                Spacer(modifier = Modifier.height(32.dp))

                FormLabel("Nome", true)
                CustomTextField(
                    uiState.nome,
                    { novoNome ->
                        viewModel.updateState { it.copy(nome = novoNome, erroNome = null) }
                    },
                    "Digite nome do evento",
                    isError = uiState.erroNome != null,
                    errorMessage = uiState.erroNome
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Página Web", false)
                CustomTextField(
                    uiState.paginaWeb,
                    { novaPaginaWeb ->
                        viewModel.updateState {
                            it.copy(
                                paginaWeb = novaPaginaWeb,
                                erroPaginaWeb = null
                            )
                        }
                    },
                    "Digite página do evento",
                    isError = uiState.erroPaginaWeb != null,
                    errorMessage = uiState.erroPaginaWeb
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Descrição", true)
                CustomTextField(
                    uiState.descricao,
                    { novaDescricao ->
                        viewModel.updateState {
                            it.copy(
                                descricao = novaDescricao,
                                erroDescricao = null
                            )
                        }
                    },
                    "Digite descrição do evento",
                    isError = uiState.erroDescricao != null,
                    errorMessage = uiState.erroDescricao
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Data de início", true)
                Box(modifier = Modifier.fillMaxWidth()) {
                    CustomTextField(
                        uiState.dataInicio,
                        { },
                        "Selecione data e hora",
                        icon = Icons.Default.DateRange,
                        isError = uiState.erroDataInicio
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { mostrarDataPickerInicio = true })
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Data de fim", true)
                Box(modifier = Modifier.fillMaxWidth()) {
                    CustomTextField(
                        uiState.dataFim,
                        { },
                        "Selecione data e hora",
                        icon = Icons.Default.DateRange,
                        isError = uiState.erroDataFim
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { mostrarDataPickerFim = true })
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                DendeButton(
                    text = "CONTINUAR",
                    onClick = {
                        if (viewModel.validarInformacoesBasicas()) {
                            onNext()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    containerColor = ButtonLinear,
                    contentColor = White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacoesAdicionaisScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
    viewModel: CadastrarAlterarEventoViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    var tipoSelecionado by remember { mutableStateOf(uiState.tipoEvento) }
    var modalidadeSelecionada by remember { mutableStateOf(uiState.modalidadeEvento) }

    var expandirTipo by remember { mutableStateOf(false) }
    var expandirEventoPrincipal by remember { mutableStateOf(false) }
    var expandirModalidade by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Informações Adicionais",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = Inter,
                        color = Black
                    )
                }, navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Passo 2 de 4",
                    color = SoftDarkish,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(6.dp))
                ProgressBarStep(step = 2, totalSteps = 4)

                Spacer(modifier = Modifier.height(32.dp))

                FormLabel("Tipo", true)
                ExposedDropdownMenuBox(
                    expanded = expandirTipo, onExpandedChange = { expandirTipo = it }) {
                    OutlinedTextField(
                        value = uiState.tipoEvento.name.lowercase().replace("_", "/")
                            .replaceFirstChar { it.uppercase() },
                        onValueChange = {},
                        readOnly = true,
                        placeholder = {
                            Text(
                                "Selecione tipo do evento",
                                color = SoftDarkish,
                                fontSize = 15.sp,
                                fontFamily = Inter
                            )
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirTipo) },
                        modifier = Modifier
                            .menuAnchor(
                                type = androidx.compose.material3.MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
                            .fillMaxWidth(),
                        isError = uiState.erroTipoEvento != null,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = when {
                                uiState.erroTipoEvento != null -> Error
                                else -> Grey2
                            }, focusedBorderColor = Black, cursorColor = Black, focusedTextColor = Black, unfocusedTextColor = Black, errorTextColor = Black
                        )
                    )
                    MaterialTheme(
                        colorScheme = MaterialTheme.colorScheme.copy(
                            surface = White, surfaceContainer = White
                        )
                    ) {
                        ExposedDropdownMenu(
                            expanded = expandirTipo, onDismissRequest = { expandirTipo = false }) {
                            TipoEvento.entries.forEach { tipo ->
                                val isSelected = tipoSelecionado == tipo
                                DropdownMenuItem(text = {
                                    Text(
                                        text = tipo.name.lowercase().replace("_", " ")
                                            .replaceFirstChar { it.uppercase() },
                                        fontFamily = Inter,
                                        color = if (isSelected) Orange else Black
                                    )
                                }, onClick = {
                                    viewModel.updateState {
                                        it.copy(
                                            tipoEvento = tipoSelecionado,
                                            erroTipoEvento = null
                                        )
                                    }
                                    expandirTipo = false
                                })
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Evento Principal", false)
                ExposedDropdownMenuBox(
                    expanded = expandirEventoPrincipal,
                    onExpandedChange = { expandirEventoPrincipal = it }) {
                    OutlinedTextField(
                        value = uiState.eventoPrincipal?.nome ?: "",
                        onValueChange = {},
                        readOnly = true,
                        placeholder = {
                            Text(
                                "Associe o evento a um principal",
                                color = SoftDarkish,
                                fontSize = 15.sp,
                                fontFamily = Inter
                            )
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirEventoPrincipal) },
                        modifier = Modifier
                            .menuAnchor(
                                type = androidx.compose.material3.MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Grey2,
                            focusedBorderColor = Black,
                            cursorColor = Black,
                            focusedTextColor = Black,
                            unfocusedTextColor = Black,
                            errorTextColor = Black
                        )
                    )
                    MaterialTheme(
                        colorScheme = MaterialTheme.colorScheme.copy(
                            surface = White, surfaceContainer = White
                        )
                    ) {
                        ExposedDropdownMenu(
                            expanded = expandirEventoPrincipal,
                            onDismissRequest = { expandirEventoPrincipal = false }) {
                            DropdownMenuItem(text = {
                                Text(
                                    text = "Nenhum",
                                    fontFamily = Inter,
                                    color = Black
                                )
                            }, onClick = {
                                viewModel.updateState { it.copy(eventoPrincipal = null) }
                                expandirEventoPrincipal = false
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Modalidade", true)
                ExposedDropdownMenuBox(
                    expanded = expandirModalidade, onExpandedChange = { expandirModalidade = it }) {
                    OutlinedTextField(
                        value = modalidadeSelecionada.name.lowercase()
                            .replaceFirstChar { it.uppercase() },
                        onValueChange = {},
                        readOnly = true,
                        placeholder = {
                            Text(
                                "Selecione modalidade do evento",
                                color = SoftDarkish,
                                fontSize = 15.sp,
                                fontFamily = Inter
                            )
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirModalidade) },
                        modifier = Modifier
                            .menuAnchor(
                                type = androidx.compose.material3.MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
                            .fillMaxWidth(),
                        isError = uiState.erroModalidade != null,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = when {
                                uiState.erroModalidade != null -> Error
                                else -> Grey2
                            }, focusedBorderColor = Black, cursorColor = Black, focusedTextColor = Black, unfocusedTextColor = Black, errorTextColor = Black
                        )
                    )
                    MaterialTheme(
                        colorScheme = MaterialTheme.colorScheme.copy(
                            surface = White, surfaceContainer = White
                        )
                    ) {
                        ExposedDropdownMenu(
                            expanded = expandirModalidade,
                            onDismissRequest = { expandirModalidade = false }) {
                            ModalidadeEvento.entries.forEach { modalidade ->
                                val isSelected = modalidadeSelecionada == modalidade
                                DropdownMenuItem(text = {
                                    Text(
                                        text = modalidade.name.lowercase()
                                            .replaceFirstChar { it.uppercase() },
                                        fontFamily = Inter,
                                        color = if (isSelected) Orange else Black
                                    )
                                }, onClick = {
                                    viewModel.updateState {
                                        it.copy(
                                            modalidadeEvento = modalidadeSelecionada,
                                            erroModalidade = null
                                        )
                                    }
                                    expandirModalidade = false
                                })
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Capacidade Máxima", true)
                CustomTextField(
                    value = uiState.capacidadeMaxima,
                    onValueChange = { novaCapacidade ->
                        viewModel.updateState {
                            it.copy(
                                capacidadeMaxima = novaCapacidade,
                                erroCapacidadeMaxima = null
                            )
                        }
                    },
                    placeholder = "Digite capacidade máxima de pessoas",
                    isError = uiState.erroCapacidadeMaxima != null,
                    errorMessage = uiState.erroCapacidadeMaxima,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Local", true)
                CustomTextField(
                    value = uiState.local,
                    onValueChange = { novoLocal ->
                        viewModel.updateState { it.copy(local = novoLocal, erroLocal = null) }
                    },
                    placeholder = "Digite local ou link do evento",
                    isError = uiState.erroLocal != null,
                    errorMessage = uiState.erroLocal
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                DendeButton(
                    text = "CONTINUAR",
                    onClick = {
                        if (viewModel.validarInformacoesAdicionais()) {
                            onNext()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    containerColor = ButtonLinear,
                    contentColor = White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaturamentoScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
    viewModel: CadastrarAlterarEventoViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Faturamento",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = Inter,
                        color = Black
                    )
                }, navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Passo 3 de 4",
                    color = SoftDarkish,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(6.dp))
                ProgressBarStep(step = 3, totalSteps = 4)

                Spacer(modifier = Modifier.height(32.dp))

                FormLabel("Valor de Ingresso", true)
                CustomTextField(
                    value = uiState.precoTicket,
                    onValueChange = { novoPreco ->
                        viewModel.updateState {
                            it.copy(
                                precoTicket = novoPreco,
                                erroPrecoTicket = null
                            )
                        }
                    },
                    placeholder = "Digite valor do ingresso do evento",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.erroPrecoTicket != null,
                    errorMessage = uiState.erroPrecoTicket
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormSwitch(
                    label = "Aceita Devoluções?",
                    isRequired = false,
                    checked = uiState.aceitaEstorno,
                    onCheckedChange = { aceita: Boolean ->
                        viewModel.updateState {
                            it.copy(
                                aceitaEstorno = aceita,
                                taxaEstorno = if (aceita) it.taxaEstorno else "",
                                erroTaxaEstorno = if (aceita) it.erroTaxaEstorno else null
                            )
                        }
                    }
                )

                if (uiState.aceitaEstorno) {
                    Spacer(modifier = Modifier.height(24.dp))
                    FormLabel("Taxa de Devolução", true)
                    CustomTextField(
                        value = uiState.taxaEstorno,
                        onValueChange = { novaTaxa ->
                            viewModel.updateState {
                                it.copy(
                                    taxaEstorno = novaTaxa,
                                    erroTaxaEstorno = null
                                )
                            }
                        },
                        placeholder = "Digite taxa de devolução do ingresso",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.erroTaxaEstorno != null,
                        errorMessage = uiState.erroTaxaEstorno
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                DendeButton(
                    text = "CONTINUAR",
                    onClick = {
                        if (viewModel.validarFaturamento()) {
                            onNext()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    containerColor = ButtonLinear,
                    contentColor = White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit,
    viewModel: CadastrarAlterarEventoViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Banner",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = Inter,
                        color = Black
                    )
                }, navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Black
                        )
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Passo 4 de 4",
                    color = SoftDarkish,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(6.dp))

                ProgressBarStep(step = 4, totalSteps = 4)

                Spacer(modifier = Modifier.height(32.dp))

                FormLabel("Anexo", false)
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .border(1.dp, Orange, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (uiState.urlBanner.isEmpty()) Grey2.copy(alpha = 0.2f) else White),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.urlBanner.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    uiState.urlBanner = " "
                                }, contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                                contentDescription = "Adicionar banner",
                                modifier = Modifier.size(32.dp),
                                tint = Orange
                            )
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = uiState.urlBanner.ifEmpty { null },
                                contentDescription = "Banner do evento",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                                error = painterResource(id = R.drawable.ic_launcher_background)
                            )

                            IconButton(
                                onClick = { uiState.urlBanner = "" },
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Excluir",
                                    tint = Orange,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                DendeButton(
                    text = "CONCLUIR",
                    onClick = {
                        val eventoPronto = viewModel.eventoParaSalvar()
                        onComplete()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    containerColor = Orange,
                    contentColor = White
                )
            }
        }
    }
}