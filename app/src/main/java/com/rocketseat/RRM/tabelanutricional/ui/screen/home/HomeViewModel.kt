package com.rocketseat.RRM.tabelanutricional.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.RRM.tabelanutricional.domain.usecase.GetHomeContentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeContentUseCase: GetHomeContentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnInit -> getHomeContent()
        }
    }

    private fun getHomeContent() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            getHomeContentUseCase().collect { homeContent ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        homeContent = homeContent,
                        userName = "Carlos"
                    )
                }
            }
        }
    }
}
