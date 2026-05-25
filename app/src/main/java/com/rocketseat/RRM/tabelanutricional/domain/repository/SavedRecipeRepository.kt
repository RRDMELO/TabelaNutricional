package com.rocketseat.RRM.tabelanutricional.domain.repository

import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import kotlinx.coroutines.flow.Flow

interface SavedRecipeRepository {
    suspend fun saveRecipe(recipe: SavedRecipe)
    suspend fun updateRecipe(recipe: SavedRecipe)
    suspend fun deleteRecipe(recipe: SavedRecipe)
    suspend fun getRecipeById(id: String): SavedRecipe?
    fun getAllRecipes(): Flow<List<SavedRecipe>>
    fun searchRecipesByName(query: String): Flow<List<SavedRecipe>>
    fun getRecipesByMealType(mealType: String): Flow<List<SavedRecipe>>
    fun getRecipesByMealTypes(mealTypes: List<String>): Flow<List<SavedRecipe>>
    fun getFavoriteRecipes(): Flow<List<SavedRecipe>>
    suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean)
    suspend fun clearAllRecipes()
}

