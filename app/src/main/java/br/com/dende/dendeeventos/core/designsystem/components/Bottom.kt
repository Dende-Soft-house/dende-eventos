package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

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
            .height(85.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp
                )
            )
            .background(Color(0xFFF3F3F3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            // HOME
            IconButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(
                            color = Color(0xFFEBDCF9),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.icon_home
                        ),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            // SEARCH
            IconButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.icon_search
                    ),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(26.dp)
                )
            }
            // TICKET
            IconButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = Color(0xFFFF6A00),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.icon_ticket
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            // USER
            IconButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.icon_user
                    ),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {

    BottomBar()
}