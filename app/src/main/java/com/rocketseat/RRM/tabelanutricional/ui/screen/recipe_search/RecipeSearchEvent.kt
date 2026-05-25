package com.rocketseat.RRM.tabelanutricional.ui.screen.recipe_search

sealed class RecipeSearchEvent {
    data class SearchQuery(val query: String) : RecipeSearchEvent()
    data class ToggleMealType(val mealType: String) : RecipeSearchEvent()
    data class UpdateCalorieRange(val min: Float, val max: Float) : RecipeSearchEvent()
    data class UpdateProteinRange(val min: Float, val max: Float) : RecipeSearchEvent()
    data class UpdateCarbRange(val min: Float, val max: Float) : RecipeSearchEvent()
    data class UpdateFiberRange(val min: Float, val max: Float) : RecipeSearchEvent()
    data class UpdateFatRange(val min: Float, val max: Float) : RecipeSearchEvent()
    data class UpdateSugarRange(val min: Float, val max: Float) : RecipeSearchEvent()
    object ToggleFilters : RecipeSearchEvent()
    object ApplyFilters : RecipeSearchEvent()
    object ClearFilters : RecipeSearchEvent()
}

