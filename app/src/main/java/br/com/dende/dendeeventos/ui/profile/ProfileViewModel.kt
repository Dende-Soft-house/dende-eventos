package br.com.dende.dendeeventos.ui.profile

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import br.com.dende.dendeeventos.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ProfileScreenMode {
    Common,
    Organizer
}

enum class ProfileTab {
    Personal,
    Business
}

data class ProfileFieldUiState(
    val label: String,
    val value: String,
    @get:DrawableRes val iconRes: Int
)

data class ProfileUiState(
    val screenMode: ProfileScreenMode = ProfileScreenMode.Common,
    val selectedTab: ProfileTab = ProfileTab.Personal,
    val personalFields: List<ProfileFieldUiState> = emptyList(),
    val businessFields: List<ProfileFieldUiState> = emptyList(),
    @get:DrawableRes val profileImageRes: Int = R.drawable.profile_placeholder
) {
    val showTabs: Boolean
        get() = screenMode == ProfileScreenMode.Organizer

    val visibleFields: List<ProfileFieldUiState>
        get() = if (showTabs && selectedTab == ProfileTab.Business) {
            businessFields
        } else {
            personalFields
        }
}

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileMockData.commonProfile())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadCommonProfile() {
        _uiState.value = ProfileMockData.commonProfile()
    }

    fun loadOrganizerProfile(selectedTab: ProfileTab = ProfileTab.Personal) {
        _uiState.value = ProfileMockData.organizerProfile(selectedTab)
    }

    fun onTabSelected(selectedTab: ProfileTab) {
        _uiState.update { currentState ->
            if (currentState.showTabs) {
                currentState.copy(selectedTab = selectedTab)
            } else {
                currentState
            }
        }
    }
}

internal object ProfileMockData {
    fun commonProfile(): ProfileUiState {
        return ProfileUiState(
            screenMode = ProfileScreenMode.Common,
            selectedTab = ProfileTab.Personal,
            personalFields = personalFields(),
            businessFields = businessFields()
        )
    }

    fun organizerProfile(selectedTab: ProfileTab): ProfileUiState {
        return ProfileUiState(
            screenMode = ProfileScreenMode.Organizer,
            selectedTab = selectedTab,
            personalFields = personalFields(),
            businessFields = businessFields()
        )
    }

    private fun personalFields(): List<ProfileFieldUiState> {
        return listOf(
            ProfileFieldUiState(
                label = "Nome Completo",
                value = "Rafael Jesus B. Cerqueira",
                iconRes = R.drawable.ic_field_person_24
            ),
            ProfileFieldUiState(
                label = "E-mail",
                value = "rafaeljbc2003@gmail.com",
                iconRes = R.drawable.ic_field_email_24
            ),
            ProfileFieldUiState(
                label = "Gênero",
                value = "Masculino",
                iconRes = R.drawable.ic_field_gender_24
            ),
            ProfileFieldUiState(
                label = "Data de Nascimento",
                value = "17 de junho, 2003 (22 anos, 10 meses e 5 dias)",
                iconRes = R.drawable.ic_calendar_24
            )
        )
    }

    private fun businessFields(): List<ProfileFieldUiState> {
        return listOf(
            ProfileFieldUiState(
                label = "CNPJ",
                value = "00.000.000/0001-00",
                iconRes = R.drawable.ic_field_business_24
            ),
            ProfileFieldUiState(
                label = "Razão Social",
                value = "Integra SI LTDA",
                iconRes = R.drawable.ic_field_shield_24
            ),
            ProfileFieldUiState(
                label = "Nome Fantasia",
                value = "Dende Eventos",
                iconRes = R.drawable.ic_field_store_24
            )
        )
    }
}
