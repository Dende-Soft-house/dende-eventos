package br.com.dende.dendeeventos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import java.util.Locale

@Composable
fun QuantidadeIngressoScreen(onContinue: () -> Unit = {}) {
    var quantity by remember { mutableIntStateOf(2) }

    val unitPrice = 1562.50
    val total = unitPrice * quantity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        QuantidadeHeaderSection()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 96.dp)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .width(390.dp)
                    .height(690.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = 24.dp, y = 16.dp)
                        .width(342.dp)
                        .height(644.dp)
                ) {
                    QuantidadeEventHeaderSection()

                    QuantidadeSeatSelectionSection(
                        quantity = quantity,
                        onMinus = {
                            if (quantity > 1) quantity--
                        },
                        onPlus = {
                            quantity++
                        }
                    )

                    QuantidadePricingSection(
                        quantity = quantity,
                        unitPrice = unitPrice,
                        total = total
                    )
                }
            }
        }

        QuantidadeBottomButtonSection(
            modifier = Modifier.align(Alignment.BottomCenter),
            onContinue = onContinue
        )
    }
}

@Composable
fun QuantidadeHeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(elevation = 2.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE2E8F0))
        )

        Box(
            modifier = Modifier
                .offset(x = 16.dp, y = 11.5f.dp)
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "←",
                fontFamily = Inter,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFEA580C),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "CHECKOUT",
            modifier = Modifier.align(Alignment.Center),
            fontFamily = Inter,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.4.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun QuantidadeEventHeaderSection() {
    Box(
        modifier = Modifier
            .offset(x = 0.dp, y = 0.dp)
            .width(342.dp)
            .height(325.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false
            )
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFF1F5F9),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner_evento),
            contentDescription = "Imagem do evento",
            modifier = Modifier
                .offset(x = 0.dp, y = 0.dp)
                .width(342.dp)
                .height(253.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Integra SI - Realizado Pela\nUnex",
            modifier = Modifier
                .offset(x = 24.dp, y = 262.dp)
                .width(292.dp)
                .height(56.dp),
            fontFamily = Inter,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F1F1F),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun QuantidadeSeatSelectionSection(
    quantity: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Box(
        modifier = Modifier
            .offset(x = 0.dp, y = 349.dp)
            .width(342.dp)
            .height(100.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            text = "Quantidade de\nIngressos",
            modifier = Modifier
                .offset(x = 24.dp, y = 26.dp)
                .width(136.dp)
                .height(48.dp),
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF251913),
            textAlign = TextAlign.Start
        )

        QuantidadeInputCounter(
            quantity = quantity,
            onMinus = onMinus,
            onPlus = onPlus
        )
    }
}

@Composable
fun QuantidadeInputCounter(
    quantity: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Box(
        modifier = Modifier
            .offset(x = 178.dp, y = 25.dp)
            .width(138.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(9999.dp))
            .background(Color(0xFFF8FAFC))
            .border(
                width = 1.dp,
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(9999.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .offset(x = 5.dp, y = 5.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE2E8F0),
                    shape = CircleShape
                )
                .clickable { onMinus() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                fontFamily = Inter,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF334155),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .offset(x = 45.dp, y = 13.dp)
                .width(48.dp)
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                fontFamily = Inter,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF251913),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .offset(x = 93.dp, y = 5.dp)
                .size(40.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                    clip = false
                )
                .clip(CircleShape)
                .background(Color(0xFFF97316))
                .clickable { onPlus() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontFamily = Inter,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun QuantidadePricingSection(
    quantity: Int,
    unitPrice: Double,
    total: Double
) {
    Box(
        modifier = Modifier
            .offset(x = 0.dp, y = 473.dp)
            .width(342.dp)
            .height(171.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            text = "Preço",
            modifier = Modifier
                .offset(x = 24.dp, y = 24.dp)
                .width(100.dp)
                .height(24.dp),
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF251913),
            textAlign = TextAlign.Start
        )

        Text(
            text = quantidadeFormatCurrency(unitPrice),
            modifier = Modifier
                .offset(x = 190.dp, y = 24.dp)
                .width(104.dp)
                .height(24.dp),
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF251913),
            textAlign = TextAlign.End
        )

        Text(
            text = "$quantity x ${quantidadeFormatNumberOnly(unitPrice)}",
            modifier = Modifier
                .offset(x = 24.dp, y = 56.dp)
                .width(150.dp)
                .height(24.dp),
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF584237),
            textAlign = TextAlign.Start
        )

        Box(
            modifier = Modifier
                .offset(x = 24.dp, y = 97.dp)
                .width(292.dp)
                .height(1.dp)
                .background(Color(0xFFF1F5F9))
        )

        Text(
            text = "Total",
            modifier = Modifier
                .offset(x = 24.dp, y = 119.dp)
                .width(100.dp)
                .height(24.dp),
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF251913),
            textAlign = TextAlign.Start
        )

        Text(
            text = quantidadeFormatTotal(total),
            modifier = Modifier
                .offset(x = 190.dp, y = 119.dp)
                .width(104.dp)
                .height(24.dp),
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF251913),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun QuantidadeBottomButtonSection(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE0E0E0))
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 19.dp)
                .width(342.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF20222C))
                .clickable { onContinue() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CONTINUAR",
                fontFamily = Inter,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun quantidadeFormatCurrency(value: Double): String {
    return "R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%,.2f", value)
        .replace(",", "X")
        .replace(".", ",")
        .replace("X", ".")
}

fun quantidadeFormatNumberOnly(value: Double): String {
    return String.format(Locale.forLanguageTag("pt-BR"), "%,.2f", value)
        .replace(",", "X")
        .replace(".", ",")
        .replace("X", ".")
}

fun quantidadeFormatTotal(value: Double): String {
    return "R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%,.0f", value)
        .replace(",", ".")
}

@Preview(
    name = "Tela Quantidade Ingresso",
    showBackground = true,
    showSystemUi = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
fun QuantidadeIngressoPreview() {
    MaterialTheme {
        QuantidadeIngressoScreen()
    }
}