package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import com.rocketseat.RRM.tabelanutricional.domain.repository.SavedRecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchAndFilterRecipesUseCase(
    private val savedRecipeRepository: SavedRecipeRepository
) {
    operator fun invoke(
        query: String = "",
        selectedMealTypes: List<String> = emptyList(),
        calorieRange: Pair<Float, Float> = 0f to Float.MAX_VALUE,
        proteinRange: Pair<Float, Float> = 0f to Float.MAX_VALUE,
        carbRange: Pair<Float, Float> = 0f to Float.MAX_VALUE,
        fiberRange: Pair<Float, Float> = 0f to Float.MAX_VALUE
    ): Flow<List<SavedRecipe>> {
        return savedRecipeRepository.getAllRecipes().map { recipes ->
            recipes.filter { recipe ->
                // Filtro de texto
                val matchesQuery = if (query.isBlank()) {
                    true
                } else {
                    recipe.name.contains(query, ignoreCase = true)
                }

                // Filtro de tipo de refeição
                val matchesMealType = if (selectedMealTypes.isEmpty()) {
                    true
                } else {
                    selectedMealTypes.contains(recipe.mealType)
                }

                // Filtro de calorias
                val matchesCalories = recipe.calories in calorieRange.first..calorieRange.second

                // Filtro de proteínas
                val matchesProteins = recipe.proteins in proteinRange.first..proteinRange.second

                // Filtro de carboidratos
                val matchesCarbs = recipe.carbohydrates in carbRange.first..carbRange.second

                // Filtro de fibras
                val matchesFiber = recipe.fiber in fiberRange.first..fiberRange.second

                matchesQuery && matchesMealType && matchesCalories && matchesProteins && matchesCarbs && matchesFiber
            }
        }
    }
}

