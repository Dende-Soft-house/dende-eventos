package br.com.dende.dendeeventos.feature.event

import androidx.compose.foundation.Image
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
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.DendeeventosTheme

@Composable
fun EventoIniciadoErroDialog(onEntendiClick: () -> Unit) {
    Dialog(onDismissRequest = onEntendiClick) {
        EventoIniciadoErroContent(onEntendiClick = onEntendiClick)
    }
}

@Composable
fun EventoIniciadoErroContent(onEntendiClick: () -> Unit) {
    // Usando Surface para melhor suporte a elevação e consistência com Material 3
    Surface(
        modifier = Modifier
            .width(342.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.aviso),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ação não permitida",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Não é possível realizar esta ação em um evento que já foi iniciado.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onEntendiClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange)
            ) {
                Text(
                    text = "ENTENDI",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventoIniciadoErroContent() {
    DendeeventosTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            EventoIniciadoErroContent(onEntendiClick = {})
        }
    }
}
