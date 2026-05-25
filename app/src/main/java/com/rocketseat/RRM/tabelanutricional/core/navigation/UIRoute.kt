package com.rocketseat.RRM.tabelanutricional.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface UIRoute {

    @Serializable
    data object Auth : UIRoute

    @Serializable
    data object Home : UIRoute

    @Serializable
    data object RecipeSearch : UIRoute

    @Serializable
    data class HealthRecipeDetails(val healthyRecipeId: String): UIRoute

}
