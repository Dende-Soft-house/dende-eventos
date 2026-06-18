package br.com.dende.dendeeventos.feature.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.ui.theme.Orange

@Composable
fun ConfirmadoDesativarEvento(){
    Box(
        Modifier
            .width(342.dp)
            .height(216.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.ok),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Evento desativado com sucesso!",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .width(294.dp)
                    .height(52.dp)
                    .background(Orange, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
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
fun PreviewConfirmadoDesativar() {
    ConfirmadoDesativarEvento()
}
