package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.domain.repository.HealthyRecipeRepository

class GetHealthyRecipeByIdUseCase(
    private val repository: HealthyRecipeRepository
) {

    suspend operator fun invoke(id: String): HealthyRecipe? = repository.getHealthyRecipeById(id)
}
