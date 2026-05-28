package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import br.com.dende.dendeeventos.R

@Composable
fun BottomBar() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(Color(0xFFF3F3F3))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),

            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomItem(
                icon = R.drawable.icon_home,
                selected = false
            )

            BottomItem(
                icon = R.drawable.icon_search,
                selected = false
            )

            BottomItem(
                icon = R.drawable.icon_ticket,
                selected = true
            )

            BottomItem(
                icon = R.drawable.icon_user,
                selected = false
            )
        }
    }
}

@Composable
fun BottomItem(
    icon: Int,
    selected: Boolean
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,

            tint =
                if (selected)
                    Color(0xFFFF6A00)
                else
                    Color.Black,

            modifier = Modifier.size(28.dp)
        )

        Box(
            modifier = Modifier
                .padding(top = 1.dp)
                .width(24.dp)
                .height(2.dp)
                .background(
                    if (selected)
                        Color(0xFFFF6A00)
                    else
                        Color.Transparent,
                    shape = RoundedCornerShape(50)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {

    BottomBar()
}