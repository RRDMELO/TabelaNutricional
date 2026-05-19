package com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.RRM.tabelanutricional.domain.usecase.GetHealthyRecipeByIdUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.IsHealthyRecipeFavoriteUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.UpdateHealthyRecipeIsFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HealthyRecipeDetailsViewModel(
    private val updateHealthyRecipeIsFavoriteUseCase: UpdateHealthyRecipeIsFavoriteUseCase,
    private val getHealthyRecipeByIdUseCase: GetHealthyRecipeByIdUseCase,
    private val isHealthyRecipeFavoriteUseCase: IsHealthyRecipeFavoriteUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HealthyRecipeDetailsUIState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HealthyRecipeDetailsEvent) {
        when (event) {
            is HealthyRecipeDetailsEvent.FindHealthyRecipeById -> findHealthyRecipe(event.id)
            HealthyRecipeDetailsEvent.OnBackPressed -> resetState()
            is HealthyRecipeDetailsEvent.UpdateIsFavorite -> updateIsFavorite(event.id, event.isFavorite)
        }
    }

    private fun updateIsFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            updateHealthyRecipeIsFavoriteUseCase(id = id, isFavorite = isFavorite)
        }
    }

    private fun resetState() {
        _uiState.update { HealthyRecipeDetailsUIState() }
    }

    private fun findHealthyRecipe(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val healthyRecipe = getHealthyRecipeByIdUseCase(id = id)
            val isFavorite = isHealthyRecipeFavoriteUseCase(id = id)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    healthyRecipe = healthyRecipe,
                    isFavorite = isFavorite
                )
            }
        }
    }
}
