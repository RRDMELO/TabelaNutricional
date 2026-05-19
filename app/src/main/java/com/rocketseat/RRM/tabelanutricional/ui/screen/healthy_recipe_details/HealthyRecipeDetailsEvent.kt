package com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details

sealed interface HealthyRecipeDetailsEvent {
    data class FindHealthyRecipeById(val id: String) : HealthyRecipeDetailsEvent
    data class UpdateIsFavorite(val id: String, val isFavorite: Boolean) : HealthyRecipeDetailsEvent
    data object OnBackPressed : HealthyRecipeDetailsEvent
}
