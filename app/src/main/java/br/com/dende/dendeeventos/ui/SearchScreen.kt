package br.com.dende.dendeeventos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import br.com.dende.dendeeventos.core.designsystem.components.BottomNavBarSearch

@Composable
fun SearchScreen() {


    Scaffold(
        containerColor = Color(0xFFF5F5F5),

        bottomBar = {
            BottomNavBarSearch()
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 14.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))


            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color(0xFFFF6B00)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            //Busca
            OutlinedTextField(
                value = "feira de santana",
                onValueChange = {
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Pesquisar")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEAEAEA),
                    unfocusedContainerColor = Color(0xFFEAEAEA),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "2 eventos encontrados",
                fontSize = 15.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Cards
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                    ) {

                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.padding(top = 4.dp)
                        ) {

                            Text(
                                text = "IntegraSI FSA",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Unex, Feira de Santana",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "21 Abr, 18:50",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF555555)
                            )
                        }
                    }
                }

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                    ) {

                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.padding(top = 4.dp)
                        ) {

                            Text(
                                text = "DEVOPSDAYS",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Feira de Santana",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "23 Mai, 14:00",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF555555)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
