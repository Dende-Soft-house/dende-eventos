package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import br.com.dende.dendeeventos.ui.theme.BlackLinear
import br.com.dende.dendeeventos.ui.theme.ButtonLinear
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.SoftDarkish
import br.com.dende.dendeeventos.ui.theme.White
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import coil.compose.AsyncImage

@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    date: String = "Date",
    location: String = "Location",
    price: String = "$0.00 BRL",
    imageUrl: String = "",
    onJoinClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp), // Altura fixa para manter o padrão da imagem
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Imagem do Evento (Arredondada)
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagem do evento $title",
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background)
            )


            Spacer(modifier = Modifier.width(12.dp))

            // 2. Conteúdo Central (Título e Data/Local)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = ButtonLinear,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = date,
                        fontFamily = Inter,
                        fontSize = 13.sp,
                        color = SoftDarkish
                    )

                    // Ponto Laranja entre data e local
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(4.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(Orange)
                    )

                    Text(
                        text = location,
                        fontFamily = Inter,
                        fontSize = 13.sp,
                        color = SoftDarkish,
                        maxLines = 1
                    )
                }
            }

            // 3. Linha Vertical Divisora
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.6f) // Linha não ocupa a altura toda
                    .width(1.dp)
                    .background(Color(0xFFE0E0E0))
            )

            // 4. Seção da Direita (Preço e Join Now)
            Column(
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = price,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Orange
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(id=R.string.btn_join_now),
                    fontFamily = Inter,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = BlackLinear,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}