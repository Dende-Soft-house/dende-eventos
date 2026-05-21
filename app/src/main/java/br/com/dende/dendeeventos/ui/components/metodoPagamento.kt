package br.com.dende.dendeeventos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.domain.Cartao
import br.com.dende.dendeeventos.domain.StatusPagamento
import br.com.dende.dendeeventos.ui.viewmodel.CompraUiState
import br.com.dende.dendeeventos.ui.viewmodel.CompraViewModel

@Composable
fun MetodoPagamentoScreen(
    viewModel: CompraViewModel = remember { CompraViewModel() },
    onBack: () -> Unit = {}
) {
    var showAddCardPopup by remember { mutableStateOf(false) }
    val state = viewModel.uiState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        MetodoPagamentoHeader(
            onBack = {
                viewModel.limparCartoesTemporariosNaoSelecionados()
                onBack()
            }
        )

        MetodoPagamentoMain(
            state = state,
            onAddCard = {
                if (!state.limiteCartoesAtingido) {
                    showAddCardPopup = true
                }
            },
            onSelectCard = viewModel::selecionarCartao
        )

        MetodoPagamentoBottomSection(
            total = state.valorTotal,
            statusPagamento = state.statusPagamento,
            tentouConfirmarPagamento = state.tentouConfirmarPagamento,
            temCartaoSelecionado = state.cartaoSelecionado != null,
            onConfirm = viewModel::confirmarPagamento
        )

        if (showAddCardPopup) {
            AddCardPopup(
                state = state,
                onNumeroChange = viewModel::atualizarNumeroCartao,
                onNomeChange = viewModel::atualizarNomeTitular,
                onValidadeChange = viewModel::atualizarValidade,
                onCvvChange = viewModel::atualizarCvv,
                onToggleSalvarCartao = viewModel::alternarSalvarCartao,
                onSaveCard = viewModel::salvarCartao,
                onClose = {
                    viewModel.limparFormularioCartao()
                    showAddCardPopup = false
                }
            )
        }
    }
}

@Composable
fun MetodoPagamentoHeader(onBack: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(elevation = 2.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE2E8F0))
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .size(40.dp)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "←",
                fontFamily = Inter,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFEA580C),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "CHECKOUT",
            modifier = Modifier.align(Alignment.Center),
            fontFamily = Inter,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.4.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MetodoPagamentoMain(
    state: CompraUiState,
    onAddCard: () -> Unit,
    onSelectCard: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 96.dp, start = 24.dp, end = 24.dp, bottom = 150.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Método de pagamento",
            fontFamily = Inter,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )

        if (state.cartoesSalvos.isEmpty()) {
            Text(
                text = "Nenhum cartão cadastrado",
                fontFamily = Inter,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6B7280)
            )
        }

        state.cartoesSalvos.forEach { cartao ->
            MetodoPagamentoCreditCardOption(
                cartao = cartao,
                isSelected = cartao.id == state.cartaoSelecionadoId,
                onClick = { onSelectCard(cartao.id) }
            )
        }

        if (state.limiteCartoesAtingido) {
            Text(
                text = "Remova um cartão para adicionar outro",
                fontFamily = Inter,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFB91C1C)
            )
        } else {
            MetodoPagamentoAddCardButton(onClick = onAddCard)
        }
    }
}

@Composable
fun MetodoPagamentoCreditCardOption(
    cartao: Cartao,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFFF97316) else Color(0xFFF3F4F6),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF9FAFB)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(16.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF4B5563),
                        shape = RoundedCornerShape(1.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .width(14.dp)
                    .height(2.dp)
                    .background(Color(0xFF4B5563))
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Crédito",
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF111827)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "**** ${cartao.numero.takeLast(4)}",
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF9CA3AF)
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (isSelected) Color(0xFFF97316) else Color(0xFFE5E7EB),
                    shape = CircleShape
                )
                .background(if (isSelected) Color(0xFFF97316) else Color.Transparent)
        )
    }
}

@Composable
fun MetodoPagamentoAddCardButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(32.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Color(0xFFF97316),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(2.dp)
                    .background(Color(0xFFF97316))
            )

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(10.dp)
                    .background(Color(0xFFF97316))
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "Adicionar Cartão",
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFF97316)
        )
    }
}

@Composable
fun BoxScope.MetodoPagamentoBottomSection(
    total: Double,
    statusPagamento: StatusPagamento,
    tentouConfirmarPagamento: Boolean,
    temCartaoSelecionado: Boolean,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(155.dp)
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 17.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total",
                fontFamily = Inter,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = quantidadeFormatTotal(total),
                fontFamily = Inter,
                fontSize = 18.sp,
                lineHeight = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
                textAlign = TextAlign.End
            )
        }

        if (statusPagamento == StatusPagamento.CONFIRMADO) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pagamento confirmado",
                fontFamily = Inter,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF15803D)
            )
        }

        if (tentouConfirmarPagamento && !temCartaoSelecionado) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selecione ou adicione um cartão",
                fontFamily = Inter,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFB91C1C)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (temCartaoSelecionado) Color(0xFF20222C) else Color(0xFFCBD5E1))
                .clickable(enabled = temCartaoSelecionado) { onConfirm() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CONFIRMAR",
                fontFamily = Inter,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (temCartaoSelecionado) Color.White else Color(0xFF64748B),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BoxScope.AddCardPopup(
    state: CompraUiState,
    onNumeroChange: (String) -> Unit,
    onNomeChange: (String) -> Unit,
    onValidadeChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onToggleSalvarCartao: () -> Unit,
    onSaveCard: () -> Boolean,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onClose() }
    )

    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .heightIn(min = 560.dp, max = 720.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp
                )
            )
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { }
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(48.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color(0xFFE5E7EB))
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Adicionar Cartão",
                modifier = Modifier.weight(1f),
                fontFamily = Inter,
                fontSize = 28.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF20222C)
            )

            Text(
                text = "×",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onClose() },
                fontFamily = Inter,
                fontSize = 30.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF4B5563),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AddCardInput(
            label = "Número do Cartão",
            placeholder = "0000 0000 0000 0000",
            value = state.numeroCartao,
            onValueChange = onNumeroChange,
            keyboardType = KeyboardType.Number,
            visualTransformation = CardNumberVisualTransformation,
            isError = state.tentouSalvarCartao && !state.numeroCartaoValido,
            errorMessage = if (state.cartaoDuplicado) "Cartão já cadastrado" else "Cartão inválido",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        AddCardInput(
            label = "Nome",
            placeholder = "e.g. John Doe",
            value = state.nomeTitular,
            onValueChange = onNomeChange,
            isError = state.tentouSalvarCartao && !state.nomeTitularValido,
            errorMessage = "Nome inválido",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AddCardInput(
                label = "Validade",
                placeholder = "MM/YY",
                value = state.validade,
                onValueChange = onValidadeChange,
                keyboardType = KeyboardType.Number,
                visualTransformation = ExpirationDateVisualTransformation,
                isError = state.tentouSalvarCartao && !state.validadeValida,
                errorMessage = "Data inválida",
                modifier = Modifier.weight(1f)
            )

            AddCardInput(
                label = "CVV",
                placeholder = "123",
                value = state.cvv,
                onValueChange = onCvvChange,
                keyboardType = KeyboardType.NumberPassword,
                isError = state.tentouSalvarCartao && !state.cvvValido,
                errorMessage = "CVV inválido",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Salvar para outros pagamentos",
                modifier = Modifier.weight(1f),
                fontFamily = Inter,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF20222C)
            )

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(28.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(
                        if (state.salvarCartao) Color(0xFFF97316) else Color(0xFFE5E7EB)
                    )
                    .clickable { onToggleSalvarCartao() }
                    .padding(horizontal = 3.dp),
                contentAlignment = if (state.salvarCartao) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF20222C))
                .clickable {
                    if (onSaveCard()) {
                        onClose()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SALVAR CARTÃO",
                fontFamily = Inter,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddCardInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontFamily = Inter,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4B5563)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = if (isError) Color(0xFFDC2626) else Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 18.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = keyboardType
                ),
                visualTransformation = visualTransformation,
                textStyle = TextStyle(
                    fontFamily = Inter,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF20222C)
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontFamily = Inter,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFD1D5DB)
                        )
                    }

                    innerTextField()
                }
            )
        }

        if (isError) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = errorMessage,
                fontFamily = Inter,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFDC2626)
            )
        }
    }
}

private object CardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formatted = text.text.chunked(4).joinToString(" ")

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    val spacesBeforeOffset = if (offset == 0) 0 else (offset - 1) / 4
                    return (offset + spacesBeforeOffset).coerceAtMost(formatted.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    val spacesBeforeOffset = listOf(4, 9, 14).count { offset > it }
                    return (offset - spacesBeforeOffset).coerceIn(0, text.text.length)
                }
            }
        )
    }
}

private object ExpirationDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val value = text.text
        val formatted = if (value.length > 2) {
            value.take(2) + "/" + value.drop(2)
        } else {
            value
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (offset <= 2) offset else (offset + 1).coerceAtMost(formatted.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset <= 2) offset else (offset - 1).coerceIn(0, value.length)
                }
            }
        )
    }
}

@Preview(
    name = "Tela Método de Pagamento",
    showBackground = true,
    showSystemUi = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
fun MetodoPagamentoPreview() {
    MaterialTheme {
        MetodoPagamentoScreen()
    }
}
