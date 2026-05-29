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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.cadastro.CadastroUiState
import br.com.dende.dendeeventos.ui.theme.BlackLinear
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.White

@Composable
fun DendeFooterButton(
    primaryButtonText: String = "Continuar",
    primaryButtonColor: Color = BlackLinear,
    onPrimaryClick: () -> Unit,
    secondaryButtonText: String = "Voltar",
    onSecondaryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(end = 10.dp, start = 10.dp).navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.Center,
    ) {
        // BOTÃO SECUNDÁRIO

            OutlinedButton (
                onClick = onSecondaryClick,
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(2.dp, BlackLinear),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_butto_svg),
                    contentDescription = "Voltar",
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = secondaryButtonText,
                    color = BlackLinear,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Inter
                )
            }
            Spacer(modifier = Modifier.weight(0.2f))
        // BOTÃO PRINMARIO

            Button(
                onClick = onPrimaryClick,
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryButtonColor, contentColor = White),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text(
                    text = primaryButtonText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    painter = painterResource(id = R.drawable.forward_butto_svg),
                    contentDescription = "Continuar",
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
            }
    }
}

@Preview(showBackground = true, name = "footer buttons preview")
@Composable
fun footerBuntonPreview(){
    MaterialTheme{
        DendeFooterButton(
            onPrimaryClick = {
            },
            onSecondaryClick = {

            },
            primaryButtonColor = BlackLinear
        )
    }

}