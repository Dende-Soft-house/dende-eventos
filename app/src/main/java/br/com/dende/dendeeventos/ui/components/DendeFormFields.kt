package br.com.dende.dendeeventos.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.theme.BlackLinear
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.White
import java.util.Calendar

@Composable
fun DendeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    placeholder: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 18.dp)) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Inter, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            trailingIcon = trailingIcon,
            placeholder = { Text(text = placeholder, color = Color.LightGray, fontFamily = Inter) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            isError = isError,
            supportingText = {
                if (isError && errorMessage != null) {
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Orange,
                unfocusedBorderColor = Orange,
                cursorColor = Orange,
            )
        )
    }
}

@Composable
fun DendeCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = BlackLinear,
                uncheckedColor = if (isError) MaterialTheme.colorScheme.error else Color.Gray,
                checkmarkColor = White
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                append("Ao cadastrar-se você aceita nossos ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Termos de uso")
                }
            },
            fontFamily = Inter,
            fontSize = 13.sp,
            color = if (isError) MaterialTheme.colorScheme.error else Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DendeDropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    title: String,
    placeholder: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        DendeTextField(
            value = value,
            onValueChange = {},
            title = title,
            placeholder = placeholder,
            readOnly = true,
            isError = isError,
            errorMessage = errorMessage,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { opcao ->
                DropdownMenuItem(
                    text = { Text(opcao, fontFamily = Inter) },
                    onClick = {
                        onValueChange(opcao)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DendeDatePickerField(
    value: String,
    onDateSelected: (String) -> Unit,
    title: String,
    placeholder: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val dataFormatada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            onDateSelected(dataFormatada)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(modifier = modifier.fillMaxWidth()) {
        DendeTextField(
            value = value,
            onValueChange = { },
            title = title,
            placeholder = placeholder,
            readOnly = true,
            isError = isError,
            errorMessage = errorMessage
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { datePickerDialog.show() }
        )
    }
}

@Preview(showBackground = true, name = "1. TextField - Padrão e Erro")
@Composable
fun PreviewDendeTextField() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DendeTextField(
                value = "",
                onValueChange = {},
                title = "E-mail",
                placeholder = "exemplo@email.com"
            )

            Spacer(modifier = Modifier.height(16.dp))

            DendeTextField(
                value = "dende@",
                onValueChange = {},
                title = "E-mail (Com Erro)",
                placeholder = "exemplo@email.com",
                isError = true,
                errorMessage = "Formato de e-mail inválido."
            )
        }
    }
}

@Preview(showBackground = true, name = "2. CheckBox - Variações")
@Composable
fun PreviewDendeCheckBox() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DendeCheckBox(
                checked = true,
                onCheckedChange = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            DendeCheckBox(
                checked = false,
                onCheckedChange = {},
                isError = true
            )
        }
    }
}

@Preview(showBackground = true, name = "3. Dropdown - Gênero")
@Composable
fun PreviewDendeDropdownField() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DendeDropdownField(
                value = "Feminino",
                onValueChange = {},
                options = listOf("Masculino", "Feminino", "Não Binário", "Prefiro não dizer"),
                title = "Gênero",
                placeholder = "Selecione..."
            )
        }
    }
}

@Preview(showBackground = true, name = "4. DatePicker - Nascimento")
@Composable
fun PreviewDendeDatePickerField() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DendeDatePickerField(
                value = "15/08/1998",
                onDateSelected = {},
                title = "Data de Nascimento",
                placeholder = "DD/MM/AAAA"
            )
        }
    }
}