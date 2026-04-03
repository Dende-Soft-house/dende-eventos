package br.com.dende.dendeeventos.core.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import br.com.dende.dendeeventos.R

val Inter = FontFamily(
    // Estilos Normais
    Font(R.font.inter_24pt_thin, FontWeight.Thin),              // 100
    Font(R.font.inter_24pt_extralight, FontWeight.ExtraLight),  // 200
    Font(R.font.inter_24pt_light, FontWeight.Light),            // 300
    Font(R.font.inter_24pt_regular, FontWeight.Normal),         // 400
    Font(R.font.inter_24pt_medium, FontWeight.Medium),          // 500
    Font(R.font.inter_24pt_semibold, FontWeight.SemiBold),      // 600
    Font(R.font.inter_24pt_bold, FontWeight.Bold),              // 700
    Font(R.font.inter_24pt_black, FontWeight.Black),            // 900

    // Estilos Itálicos (Onde FontWeight + FontStyle.Italic)
    Font(R.font.inter_24pt_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.inter_24pt_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.inter_24pt_blackitalic, FontWeight.Black, FontStyle.Italic)
)