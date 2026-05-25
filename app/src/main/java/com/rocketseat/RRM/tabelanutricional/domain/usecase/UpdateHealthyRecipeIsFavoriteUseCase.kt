package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.domain.repository.HealthyRecipeRepository

class UpdateHealthyRecipeIsFavoriteUseCase(
    private val repository: HealthyRecipeRepository
) {

    suspend operator fun invoke(id: String, isFavorite: Boolean) {
        repository.updateIsFavorite(id = id, isFavorite = isFavorite)
    }
}
