package br.com.dende.dendeeventos.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.ui.theme.BlackLinear

@Composable
fun DendeBackButton (
    onClick: () -> Unit,
) {
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .border(BorderStroke(1.dp, BlackLinear), shape = RoundedCornerShape(15.dp)),
        colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.Transparent),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back_butto_svg),
            contentDescription = "Voltar",
            modifier = Modifier.size(24.dp),
        )
    }
}
@Preview(showBackground = true, name = "back button preview")
@Composable
fun backButtonPreview(){
    MaterialTheme{
        DendeBackButton(
    onClick = {

    }
        )
    }

}