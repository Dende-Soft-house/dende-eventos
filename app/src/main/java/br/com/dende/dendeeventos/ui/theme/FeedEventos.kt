package br.com.dende.dendeeventos.ui.theme

import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.components.BottomNavBar
import br.com.dende.dendeeventos.ui.viewmodel.FeedEventosViewModel
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel




@Composable
fun EventoCard(modifier: Modifier = Modifier, titulo: String, local: String, data: String) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                spotColor = Color.Black,
                ambientColor = Color.Black
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //Imagem do evento
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1F2232)),
                contentAlignment = Alignment.Center
            ) {
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Nome do Evento
            Text(
                text = titulo,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Local e data
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        tint = Orange
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = local,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_date),
                        contentDescription = null,
                        tint = Orange
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = data,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            //Parte inferior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Row separada apenas para os círculos
                Row(
                    horizontalArrangement = Arrangement.spacedBy((-12).dp) // Valor negativo cria a sobreposição
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .border(width = 1.dp, color = Color.White, shape = CircleShape)
                        )
                    }
                }

                // Botão
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F2232)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Ver detalhes", color = Color.White)
                }
            }
        }
    }
}

    @Composable
    fun FeedEventosScreen(
        viewModel: FeedEventosViewModel = viewModel()
    ) {

        val eventos = viewModel.eventos.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ){

            //Box do laranja atrás
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        color = Orange,
                        shape = RoundedCornerShape(
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                //Header
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_placeholder),
                        contentDescription = "Foto de Perfil",
                        contentScale = ContentScale.Crop, // Faz a imagem preencher o espaço cortando as bordas
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape) // Arredonda a imagem perfeitamente
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Olá, usuário!",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Próximos eventos:",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(20.dp))

                val formatadorHorario =
                    DateTimeFormatter.ofPattern(
                        "dd MMM, HH:mm",
                        Locale("pt", "BR")

                    )

                //Lista de eventos
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ){

                    items(eventos.value) { evento ->

                        EventoCard(
                            titulo = evento.evento,
                            local = "${evento.local.nome}, ${evento.local.cidade}",
                            data = evento.dataInicio.format(formatadorHorario)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }


            }

            BottomNavBar(modifier = Modifier.align(BottomCenter))
            }
        }



    @Preview
    @Composable
    fun FeedEventosScreenPreview() {
        FeedEventosScreen()
    }

