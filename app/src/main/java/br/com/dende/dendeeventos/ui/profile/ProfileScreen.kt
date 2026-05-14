package br.com.dende.dendeeventos.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.annotation.DrawableRes
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.components.DendeButton
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.theme.DendeeventosTheme
import br.com.dende.dendeeventos.ui.theme.Grey
import br.com.dende.dendeeventos.ui.theme.Grey2
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.SoftDarkish
import br.com.dende.dendeeventos.ui.theme.White

private enum class ProfileTab {
    Personal,
    Business
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeactivateClick: () -> Unit = {}
) {
    ProfileContent(
        modifier = modifier,
        showTabs = false,
        selectedTab = ProfileTab.Personal,
        onBackClick = onBackClick,
        onEditClick = onEditClick,
        onDeactivateClick = onDeactivateClick
    ) {
        PersonalProfileFields()
    }
}

@Composable
fun OrganizerProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeactivateClick: () -> Unit = {},
    onBusinessTabClick: () -> Unit = {}
) {
    ProfileContent(
        modifier = modifier,
        showTabs = true,
        selectedTab = ProfileTab.Personal,
        onTabSelected = { selectedTab ->
            if (selectedTab == ProfileTab.Business) {
                onBusinessTabClick()
            }
        },
        onBackClick = onBackClick,
        onEditClick = onEditClick,
        onDeactivateClick = onDeactivateClick
    ) {
        PersonalProfileFields()
    }
}

@Composable
fun OrganizerCompanyProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeactivateClick: () -> Unit = {},
    onPersonalTabClick: () -> Unit = {}
) {
    ProfileContent(
        modifier = modifier,
        showTabs = true,
        selectedTab = ProfileTab.Business,
        onTabSelected = { selectedTab ->
            if (selectedTab == ProfileTab.Personal) {
                onPersonalTabClick()
            }
        },
        onBackClick = onBackClick,
        onEditClick = onEditClick,
        onDeactivateClick = onDeactivateClick
    ) {
        BusinessProfileFields()
    }
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    showTabs: Boolean,
    selectedTab: ProfileTab,
    onTabSelected: (ProfileTab) -> Unit = {},
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeactivateClick: () -> Unit,
    fieldsContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 96.dp)
        ) {
            ProfileHeader(onBackClick = onBackClick)

            if (showTabs) {
                ProfileTabs(
                    selectedTab = selectedTab,
                    onTabSelected = onTabSelected
                )

                Spacer(modifier = Modifier.height(20.dp))
            } else {
                Spacer(modifier = Modifier.height(28.dp))
            }

            fieldsContent()

            Spacer(modifier = Modifier.height(34.dp))

            DendeButton(
                text = "Editar Perfil",
                onClick = onEditClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                containerColor = SoftDarkish,
                contentColor = White
            )

            Spacer(modifier = Modifier.height(22.dp))

            DendeButton(
                text = "Inativar Perfil",
                onClick = onDeactivateClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                containerColor = Orange,
                contentColor = White
            )
        }

        ProfileBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun ProfileHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(318.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(226.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
                .background(Orange)
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp)
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back_24),
                contentDescription = "Voltar",
                modifier = Modifier.size(32.dp),
                tint = SoftDarkish
            )
        }

        Card(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(226.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
private fun ProfileTabs(
    selectedTab: ProfileTab,
    onTabSelected: (ProfileTab) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 34.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Dados Pessoais",
                fontFamily = Inter,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(ProfileTab.Personal) }
            )

            Text(
                text = "Dados Empresariais",
                fontFamily = Inter,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(ProfileTab.Business) }
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(if (selectedTab == ProfileTab.Personal) Orange else Grey2)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(if (selectedTab == ProfileTab.Business) Orange else Grey2)
            )
        }
    }
}

@Composable
private fun PersonalProfileFields() {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileInfoField(
            label = "Nome Completo",
            value = "Rafael Jesus B. Cerqueira",
            iconRes = R.drawable.ic_field_person_24
        )

        ProfileInfoField(
            label = "E-mail",
            value = "rafaeljbc2003@gmail.com",
            iconRes = R.drawable.ic_field_email_24
        )

        ProfileInfoField(
            label = "Gênero",
            value = "Masculino",
            iconRes = R.drawable.ic_field_gender_24
        )

        ProfileInfoField(
            label = "Data de Nascimento",
            value = "17 de junho, 2003 (22 anos)",
            iconRes = R.drawable.ic_calendar_24
        )
    }
}

@Composable
private fun BusinessProfileFields() {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileInfoField(
            label = "CNPJ",
            value = "00.000.000/0001-00",
            iconRes = R.drawable.ic_field_business_24
        )

        ProfileInfoField(
            label = "Razão Social",
            value = "Integra SI LTDA",
            iconRes = R.drawable.ic_field_shield_24
        )

        ProfileInfoField(
            label = "Nome Fantasia",
            value = "Dende Eventos",
            iconRes = R.drawable.ic_field_store_24
        )
    }
}

@Composable
private fun ProfileInfoField(
    label: String,
    value: String,
    @DrawableRes
    iconRes: Int
) {
    Column {
        Text(
            text = label,
            fontFamily = Inter,
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Grey)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(18.dp),
                    tint = SoftDarkish
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = value,
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF20232C),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ProfileBottomBar(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp),
        color = White,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileBottomItem(
                text = "Inicio",
                iconRes = R.drawable.ic_home_24,
                selected = false
            )
            ProfileBottomItem(
                text = "Agenda",
                iconRes = R.drawable.ic_calendar_24,
                selected = false
            )
            ProfileBottomItem(
                text = "Mapa",
                iconRes = R.drawable.ic_location_24,
                selected = false
            )
            ProfileBottomItem(
                text = "Perfil",
                iconRes = R.drawable.ic_person_24,
                selected = true
            )
        }
    }
}

@Composable
private fun ProfileBottomItem(
    text: String,
    @DrawableRes
    iconRes: Int,
    selected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier
                .size(30.dp),
            tint = if (selected) Orange else SoftDarkish
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = text,
            color = if (selected) Orange else SoftDarkish,
            fontFamily = Inter,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(
    name = "Perfil Usuario Comum",
    showBackground = true,
    widthDp = 412,
    heightDp = 917
)
@Composable
private fun ProfileScreenPreview() {
    DendeeventosTheme {
        ProfileScreen()
    }
}

@Preview(
    name = "Perfil Organizador - Dados Pessoais",
    showBackground = true,
    widthDp = 412,
    heightDp = 917
)
@Composable
private fun OrganizerProfileScreenPreview() {
    DendeeventosTheme {
        OrganizerProfileScreen()
    }
}

@Preview(
    name = "Perfil Organizador - Dados Empresariais",
    showBackground = true,
    widthDp = 412,
    heightDp = 917
)
@Composable
private fun OrganizerCompanyProfileScreenPreview() {
    DendeeventosTheme {
        OrganizerCompanyProfileScreen()
    }
}
