package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.Inter

@Composable
fun TicketCard(
    modifier: Modifier = Modifier,
    titulo: String = "IntegraSI FSA",
    data: String = "21 Abr, 18:50",
    status: String = "ATIVO",
    statusColor: Color = Color(0xFF169B16),
    backgroundStatus: Color = Color(0xFFDFF5D8),
    buttonColor: Color = Color(0xFF1F2230),
    buttonTextColor: Color = Color.White,
    onVerClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.banner_card),
                contentDescription = "Banner do evento",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = titulo,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF20222C)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = data,
                        fontFamily = Inter,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Surface(
                    color = backgroundStatus,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = status,
                        color = statusColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontFamily = Inter
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = onVerClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = buttonTextColor
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Ingresso",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Inter
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketCardPreview() {
    TicketCard()
}