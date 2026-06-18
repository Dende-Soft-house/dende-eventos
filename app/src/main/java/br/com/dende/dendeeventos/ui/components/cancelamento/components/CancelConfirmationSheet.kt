package br.com.dende.dendeeventos.ui.cancelamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.theme.OrangePrimary
import br.com.dende.dendeeventos.ui.theme.TextPrimary
import br.com.dende.dendeeventos.ui.theme.TextSecondary

/**
 * Bottom sheet de confirmação de cancelamento.
 * Para prosseguir, o usuário precisa digitar a palavra "CONFIRMAR".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelConfirmationSheet(
    refundAmount: String,
    onConfirm: () -> Unit,
    onKeepTicket: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var inputText by remember { mutableStateOf("") }
    val isConfirmEnabled = inputText.trim().equals("CONFIRMAR", ignoreCase = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Cancelar este ingresso?",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Esta ação não pode ser desfeita. Seu reembolso de $refundAmount será enviado em 5 a 7 dias úteis.",
                color = TextSecondary,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "DIGITE \"CONFIRMAR\" PARA PROSSEGUIR:",
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = "CONFIRMAR",
                        color = Color(0xFFBDBDBD),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(
                    color = TextPrimary,
                    fontSize = 14.sp
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = OrangePrimary,
                    focusedBorderColor = OrangePrimary,
                    unfocusedBorderColor = OrangePrimary,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryOutlineButton(
                text = "CONTINUAR",
                onClick = {
                    if (isConfirmEnabled) onConfirm()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryGradientButton(
                text = "NÃO, MANTER MEU INGRESSO",
                onClick = onKeepTicket
            )
        }
    }
}
