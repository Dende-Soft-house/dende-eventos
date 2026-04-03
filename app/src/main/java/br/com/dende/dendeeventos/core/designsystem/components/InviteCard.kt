package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.*

@Composable
fun InviteCard(
    modifier: Modifier = Modifier,
    userName: String = "David Silbia",
    eventName: String = "Dribbble Design meetup 2022",
    timeAgo: String = "1 min ago"
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Linha Superior: Avatar + Textos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF20222C),
                                    fontFamily = Inter
                                )) {
                                    append(userName)
                                }
                                withStyle(style = SpanStyle(
                                    color = Color.Gray,
                                    fontFamily = Inter
                                )) {
                                    append(" invite you on")
                                }
                            },
                            fontSize = 14.sp
                        )

                        Text(
                            text = timeAgo,
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontFamily = Inter
                        )
                    }
                    Text(
                        text = eventName,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontFamily = Inter,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botões de Ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botão Reject (Fundo claro, texto laranja)
                DendeButton(
                    text = "Reject",
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    containerColor = Color(0xFFFDEEE3), // Um laranja bem clarinho
                    contentColor = Color(0xFFE27D1F)   // Laranja forte
                )

                // Botão Accept (Fundo laranja, texto branco)
                DendeButton(
                    text = "Accept",
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    containerColor = Color(0xFFE27D1F), // Laranja do seu app
                    contentColor = Color.White
                )
            }
        }
    }
}