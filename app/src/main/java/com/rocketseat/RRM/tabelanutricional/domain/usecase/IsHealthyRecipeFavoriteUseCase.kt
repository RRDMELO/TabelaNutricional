package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.domain.repository.HealthyRecipeRepository

class IsHealthyRecipeFavoriteUseCase(
    private val repository: HealthyRecipeRepository
) {
    suspend operator fun invoke(id: String): Boolean = repository.checkIsFavorite(id)
}
