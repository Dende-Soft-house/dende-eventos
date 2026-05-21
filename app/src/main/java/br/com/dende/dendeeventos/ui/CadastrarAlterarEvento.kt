package br.com.dende.dendeeventos.ui

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
import androidx.compose.ui.tooling.preview.Preview
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
import br.com.dende.dendeeventos.domain.Evento
import br.com.dende.dendeeventos.domain.Faturamento
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacoesBasicasScreen(
    eventoAlterando: Evento? = null, onBack: () -> Unit, onNext: () -> Unit
) {
    var nome by remember { mutableStateOf(eventoAlterando?.nome ?: "") }
    var paginaWeb by remember { mutableStateOf(eventoAlterando?.paginaWeb ?: "") }
    var descricao by remember { mutableStateOf(eventoAlterando?.descricao ?: "") }
    var dataInicio by remember { mutableStateOf(eventoAlterando?.dataInicio?.toString() ?: "") }
    var dataFim by remember { mutableStateOf(eventoAlterando?.dataFim?.toString() ?: "") }

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

    val datePickerColors = DatePickerDefaults.colors(
        containerColor = White,
        titleContentColor = Black,
        headlineContentColor = Black,
        weekdayContentColor = Color.Gray,
        selectedDayContainerColor = Orange,
        selectedDayContentColor = White,
        todayContentColor = Orange,
        todayDateBorderColor = Orange
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
            val cal = Calendar.getInstance().apply {
                timeInMillis = dataSel
                set(Calendar.HOUR_OF_DAY, timePickerStateInicio.hour)
                set(Calendar.MINUTE, timePickerStateInicio.minute)
            }
            dataInicio = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(cal.time)
            erroDataInicio = false
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
            val cal = Calendar.getInstance().apply {
                timeInMillis = dataSelFim
                set(Calendar.HOUR_OF_DAY, timePickerStateFim.hour)
                set(Calendar.MINUTE, timePickerStateFim.minute)
            }
            dataFim = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(cal.time)
            erroDataFim = false
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
                    fontFamily = Inter
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
                    nome,
                    { nome = it; erroNome = null },
                    "Digite nome do evento",
                    isError = erroNome != null,
                    errorMessage = erroNome
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Página Web", false)
                CustomTextField(
                    paginaWeb,
                    { paginaWeb = it; erroPaginaWeb = null },
                    "Digite página do evento",
                    isError = erroPaginaWeb != null,
                    errorMessage = erroPaginaWeb
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Descrição", true)
                CustomTextField(
                    descricao,
                    { descricao = it; erroDescricao = null },
                    "Digite descrição do evento",
                    isError = erroDescricao != null,
                    errorMessage = erroDescricao
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Data de início", true)
                Box(modifier = Modifier.fillMaxWidth()) {
                    CustomTextField(
                        dataInicio,
                        { },
                        "Selecione data e hora",
                        icon = Icons.Default.DateRange,
                        isError = erroDataInicio
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
                        dataFim,
                        { },
                        "Selecione data e hora",
                        icon = Icons.Default.DateRange,
                        isError = erroDataFim
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
                        erroNome = when {
                            nome.isEmpty() -> "Campo obrigatório"
                            nome.length < 3 -> "Nome curto ou inválido"
                            else -> null
                        }

                        erroPaginaWeb = when {
                            paginaWeb.isNotEmpty() && !paginaWeb.contains(".") -> "Página web inválida"
                            else -> null
                        }

                        erroDescricao = when {
                            descricao.isEmpty() -> "Campo obrigatório"
                            descricao.length < 5 -> "Descrição curta ou inválida"
                            else -> null
                        }

                        erroDataInicio = dataInicio.isEmpty()
                        erroDataFim = dataFim.isEmpty()

                        when {
                            erroNome == null && erroPaginaWeb == null && erroDescricao == null && !erroDataInicio && !erroDataFim -> {
                                onNext()
                            }
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

@Preview(showBackground = true)
@Composable
fun InformacoesBasicasScreenPreview() {
    InformacoesBasicasScreen(eventoAlterando = null, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacoesAdicionaisScreen(
    eventoAlterando: Evento? = null, onBack: () -> Unit, onNext: () -> Unit
) {
    var tipoSelecionado by remember { mutableStateOf(eventoAlterando?.tipoEvento) }
    var modalidadeSelecionada by remember { mutableStateOf(eventoAlterando?.modalidadeEvento) }
    var eventoPrincipal by remember {
        mutableStateOf(
            eventoAlterando?.eventoPrincipal?.toString() ?: ""
        )
    }
    var capacidadeMaxima by remember {
        mutableStateOf(
            eventoAlterando?.capacidadeMaxima?.toString() ?: ""
        )
    }
    var localEvento by remember { mutableStateOf(eventoAlterando?.local ?: "") }

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
                title = {
                Text(
                    "Informações Adicionais",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = Inter
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
                        value = tipoSelecionado?.name?.lowercase()?.replace("_", " ")
                        ?.replaceFirstChar { it.uppercase() } ?: "",
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
                        isError = erroTipo,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = when {
                                erroTipo -> Error
                                else -> Grey2
                            }, focusedBorderColor = Black, cursorColor = Black
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
                                    tipoSelecionado = tipo
                                    erroTipo = false
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
                        value = eventoPrincipal,
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
                            cursorColor = Black
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
                                    text = "IntegraSI 2026.1",
                                    fontFamily = Inter,
                                    color = if (eventoPrincipal == "IntegraSI 2026.1") Orange else Black
                                )
                            }, onClick = {
                                eventoPrincipal = "IntegraSI 2026.1"
                                expandirEventoPrincipal = false
                            })
                            DropdownMenuItem(text = {
                                Text(
                                    text = "Nenhum",
                                    fontFamily = Inter,
                                    color = if (eventoPrincipal == "Nenhum") Orange else Black
                                )
                            }, onClick = {
                                eventoPrincipal = "Nenhum"
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
                        value = modalidadeSelecionada?.name?.lowercase()
                        ?.replaceFirstChar { it.uppercase() } ?: "",
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
                        isError = erroModalidade,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = when {
                                erroModalidade -> Error
                                else -> Grey2
                            }, focusedBorderColor = Black, cursorColor = Black
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
                            ModalidadeEvento.entries.forEach { mod ->
                                val isSelected = modalidadeSelecionada == mod
                                DropdownMenuItem(text = {
                                    Text(
                                        text = mod.name.lowercase()
                                            .replaceFirstChar { it.uppercase() },
                                        fontFamily = Inter,
                                        color = if (isSelected) Orange else Black
                                    )
                                }, onClick = {
                                    modalidadeSelecionada = mod
                                    erroModalidade = false
                                    expandirModalidade = false
                                })
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Capacidade Máxima", true)
                CustomTextField(
                    value = capacidadeMaxima,
                    onValueChange = {
                        capacidadeMaxima = it
                        erroCapacidade = null
                    },
                    placeholder = "Digite capacidade máxima de pessoas",
                    isError = erroCapacidade != null,
                    errorMessage = erroCapacidade,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel("Local", true)
                CustomTextField(
                    value = localEvento,
                    onValueChange = {
                        localEvento = it
                        erroLocal = null
                    },
                    placeholder = "Digite local ou link do evento",
                    isError = erroLocal != null,
                    errorMessage = erroLocal
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
                        erroTipo = when (tipoSelecionado) {
                            null -> true
                            else -> false
                        }

                        erroModalidade = when (modalidadeSelecionada) {
                            null -> true
                            else -> false
                        }

                        erroCapacidade = when {
                            capacidadeMaxima.isEmpty() -> "Campo obrigatório"
                            capacidadeMaxima.toIntOrNull() == null || capacidadeMaxima.toInt() < 0 -> "Capacidade inválida"
                            else -> null
                        }

                        erroLocal = when {
                            localEvento.isEmpty() -> "Campo obrigatório"
                            localEvento.length < 3 -> "Local curto ou inválido"
                            else -> null
                        }

                        when {
                            !erroTipo && !erroModalidade && erroCapacidade == null && erroLocal == null -> {
                                onNext()
                            }
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

@Preview(showBackground = true)
@Composable
fun InformacoesAdicionaisScreenPreview() {
    InformacoesAdicionaisScreen(eventoAlterando = null, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaturamentoScreen(
    eventoAlterando: Faturamento? = null, onBack: () -> Unit, onNext: () -> Unit
) {
    var valorIngresso by remember { mutableStateOf(eventoAlterando?.precoTicket?.toString() ?: "") }
    var aceitaDevolucoes by remember { mutableStateOf(eventoAlterando?.aceitaEstorno ?: false) }
    var taxaDevolucao by remember { mutableStateOf(eventoAlterando?.taxaEstorno?.toString() ?: "") }

    var erroValor by remember { mutableStateOf<String?>(null) }
    var erroTaxa by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                Text(
                    "Faturamento",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = Inter
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
                    value = valorIngresso,
                    onValueChange = { valorIngresso = it; erroValor = null },
                    placeholder = "Digite valor do ingresso do evento",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = erroValor != null,
                    errorMessage = erroValor
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormSwitch(
                    label = "Aceita Devoluções?",
                    isRequired = false,
                    checked = aceitaDevolucoes,
                    onCheckedChange = { aceitaDevolucoes = it })

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
                    .background(White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                DendeButton(
                    text = "CONTINUAR",
                    onClick = {
                        val valorNumerico = valorIngresso.replace(",", ".").toDoubleOrNull()
                        erroValor = when {
                            valorIngresso.isEmpty() -> "Campo obrigatório"
                            valorNumerico == null || valorNumerico < 0.0 -> "Valor inválido"
                            else -> null
                        }

                        val taxaNumerica = taxaDevolucao.replace(",", ".").toDoubleOrNull()
                        erroTaxa = when {
                            !aceitaDevolucoes -> null
                            taxaDevolucao.isEmpty() -> "Campo obrigatório"
                            taxaNumerica == null || taxaNumerica !in 0.0..100.0 -> "Taxa inválida"
                            else -> null
                        }

                        when {
                            erroValor == null && erroTaxa == null -> {
                                onNext()
                            }
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

@Preview(showBackground = true)
@Composable
fun FaturamentoScreenPreview() {
    FaturamentoScreen(eventoAlterando = null, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerScreen(
    eventoAlterando: Evento? = null, onBack: () -> Unit, onComplete: () -> Unit
) {
    var bannerUri by remember { mutableStateOf(eventoAlterando?.urlBanner ?: "") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                Text(
                    "Banner", fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = Inter
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
                        .background(if (bannerUri.isEmpty()) Grey2.copy(alpha = 0.2f) else White),
                    contentAlignment = Alignment.Center
                ) {
                    if (bannerUri.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    bannerUri = " "
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
                                model = bannerUri.ifEmpty { null },
                                contentDescription = "Banner do evento",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                                error = painterResource(id = R.drawable.ic_launcher_background)
                            )

                            IconButton(
                                onClick = { bannerUri = "" },
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
                    onClick = onComplete,
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

@Preview(showBackground = true)
@Composable
fun BannerScreenPreview() {
    BannerScreen(eventoAlterando = null, onBack = {}, onComplete = {})
}