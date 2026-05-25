package com.rocketseat.RRM.tabelanutricional.ui.screen.recipe_search

import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe

data class RecipeSearchUIState(
    val isLoading: Boolean = false,
    val recipes: List<SavedRecipe> = emptyList(),
    val searchQuery: String = "",
    val selectedMealTypes: List<String> = emptyList(),
    val showFilters: Boolean = false,
    val calorieRange: Pair<Float, Float> = 0f to 500f,
    val proteinRange: Pair<Float, Float> = 0f to 100f,
    val carbRange: Pair<Float, Float> = 0f to 200f,
    val fiberRange: Pair<Float, Float> = 0f to 50f,
    val fatRange: Pair<Float, Float> = 0f to 100f,
    val sugarRange: Pair<Float, Float> = 0f to 50f
)

enum class MealType(val displayName: String) {
    BREAKFAST("Café da manhã"),
    LUNCH("Almoço"),
    DINNER("Jantar"),
    SNACK("Lanche")
}

