package br.com.dende.dendeeventos.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R

private val DendeOrange = Color(0xFFF25027)

@Composable
fun EventosDetalhesScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onBuyClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {

            // Botão voltar
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Voltar",
                    tint = DendeOrange
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                // Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1F2232))
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Título
                Text(
                    text = "IntegraSI FSA",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "Um encontro de tecnologia, inovação e conexão",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Informações
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F0F0)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.ic_date),
                                contentDescription = null,
                                tint = DendeOrange,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "21 de Abril, 18:50",
                                fontSize = 16.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.ic_location),
                                contentDescription = null,
                                tint = DendeOrange,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "UNEX, Feira de Santana - BA",
                                fontSize = 16.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.ic_time),
                                contentDescription = null,
                                tint = DendeOrange,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "3h 30m de Duração",
                                fontSize = 16.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.ic_ticket),
                                contentDescription = null,
                                tint = DendeOrange,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Entrada Gratuita",
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sobre
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F0F0)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Sobre o evento",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "O IntegraSI FSA é um evento para estudantes, profissionais e entusiastas da área de Tecnologia da Informação que buscam aprender, se atualizar e se conectar com outras pessoas do meio.",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Ver mais...",
                            color = DendeOrange,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Participantes
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F0F0)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Participantes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF232323)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "+120 Pessoas já confirmaram",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Botões bottom
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedButton(
                        onClick = onShareClick,
                        modifier = Modifier.weight(0.8f),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(0.dp, Color.Transparent),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFE8E8E8)
                        )
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_share),
                            contentDescription = null,
                            tint = Color.Gray
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Compartilhar",
                            color = Color.Gray
                        )
                    }


                    Button(
                        onClick = onBuyClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1F2333)
                        )
                    ) {

                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.ic_ticket),
                            contentDescription = null,
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Comprar ingresso",
                            color = Color.White,
                            fontSize = 13.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventosDetalhesScreenPreview() {
    EventosDetalhesScreen()
}