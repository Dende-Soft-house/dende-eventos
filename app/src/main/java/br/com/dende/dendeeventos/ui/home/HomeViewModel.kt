package br.com.dende.dendeeventos.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState(
        bannerText = "Todos os tipos de eventos em um só lugar."
    ))
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()
}
