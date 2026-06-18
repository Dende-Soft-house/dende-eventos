package br.com.dende.dendeeventos.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.feature.event.ModalAtivarEvento
import br.com.dende.dendeeventos.ui.theme.Black
import br.com.dende.dendeeventos.ui.theme.Grey
import br.com.dende.dendeeventos.ui.theme.Orange

@Composable
fun HomePage(

){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.evento1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(19.dp)
                .background(Orange)
        ){
            Text(text = "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.sp
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.evento2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Orange),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Todos os tipos de eventos, em um só lugar.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.sp)
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.logodende),
                contentDescription = null,
                modifier = Modifier.size(250.dp),

            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Grey),
            contentAlignment = Alignment.Center
        ){}
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            Modifier
                .width(265.dp)
                .height(55.dp)
                // Substitua o background antigo por este:
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF3E3F47), // Cor de 0%
                            Color(0xFF242730)  // Cor de 100%
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "PARTICIPE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHome() {
    HomePage()
}