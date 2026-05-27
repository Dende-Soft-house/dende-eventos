package br.com.dende.dendeeventos.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.theme.BlackLinear
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.White

@Composable
fun DendeNotificationDialog(
    title: String,
    description: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    dismissText: String? = null,
    iconRes: Int? = null,
    confirmButtonColor: Color = BlackLinear
) {
    Dialog(
        onDismissRequest = { onDismiss?.invoke() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (iconRes != null) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        tint = Orange,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // DESCRIÇÃO
                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontFamily = Inter,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // BOTÃO PRINCIPAL
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = confirmButtonColor),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    ) {
                        Text(
                            text = confirmText,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Inter,
                            color = White,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    // BOTÃO SECUNDÁRIO
                    if (dismissText != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton (
                            onClick = { onDismiss?.invoke() },
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange),
                            border = BorderStroke(2.dp, Orange),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                        ){
                            Text(
                                text = dismissText,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Inter,
                                color = Orange,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "footer buttons preview")
@Composable
fun popUpPreview(){
    MaterialTheme{
        DendeNotificationDialog(
            title = "Atenção",
            description = "Email Já Cadastrado! \nPor favor tente outro email ou realize o Login",
            iconRes = R.drawable.error_ico,
            confirmText = "Tentar Novamente",
            onConfirm = {},
            onDismiss = {},
            dismissText = "Login"
        )
    }
}