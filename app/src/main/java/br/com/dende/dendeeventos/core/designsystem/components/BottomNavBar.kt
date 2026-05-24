package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import br.com.dende.dendeeventos.R

private val DendeOrange = Color(0xFFF97316)

@Composable
fun BottomNavBar(
    selectedIndex: Int = 0
) {

    NavigationBar(

        modifier = Modifier
            .fillMaxWidth()

            .shadow(
                elevation = 24.dp,

                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                ),

                spotColor = Color.Black,

                ambientColor = Color.Black
            )

            .height(70.dp)

            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                )
            ),

        containerColor = Color.White,

        tonalElevation = 0.dp
    ) {

        // HOME
        BottomNavItem(
            selected = selectedIndex == 0,

            icon = R.drawable.ic_home
        )

        // SEARCH
        BottomNavItem(
            selected = selectedIndex == 1,

            icon = R.drawable.ic_search
        )

        // TICKET
        BottomNavItem(
            selected = selectedIndex == 2,

            icon = R.drawable.ic_ticket
        )

        // PROFILE
        BottomNavItem(
            selected = selectedIndex == 3,

            icon = R.drawable.ic_profile
        )
    }
}

@Composable
fun BottomNavItem(
    selected: Boolean,
    icon: Int
) {

    NavigationBarItem(

        selected = selected,

        onClick = {},

        icon = {

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Icon(
                    painter = painterResource(id = icon),

                    contentDescription = null,

                    tint =
                        if (selected)
                            DendeOrange
                        else
                            Color.Black
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(
                            if (selected)
                                DendeOrange
                            else
                                Color.Transparent
                        )
                )
            }
        },

        label = {}
    )
}
@Composable
fun NavigationBarItem(selected: Boolean, onClick: () -> Unit, icon: @Composable () -> Unit, label: () -> Unit) {
    TODO("Not yet implemented")
}