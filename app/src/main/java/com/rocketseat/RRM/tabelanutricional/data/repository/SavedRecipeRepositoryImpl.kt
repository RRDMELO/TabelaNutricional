package com.rocketseat.RRM.tabelanutricional.data.repository

import com.rocketseat.RRM.tabelanutricional.data.datasource.local.SavedRecipeDao
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import com.rocketseat.RRM.tabelanutricional.domain.repository.SavedRecipeRepository
import kotlinx.coroutines.flow.Flow

class SavedRecipeRepositoryImpl(
    private val savedRecipeDao: SavedRecipeDao
) : SavedRecipeRepository {
    override suspend fun saveRecipe(recipe: SavedRecipe) {
        savedRecipeDao.insertRecipe(recipe)
    }

    override suspend fun updateRecipe(recipe: SavedRecipe) {
        savedRecipeDao.updateRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: SavedRecipe) {
        savedRecipeDao.deleteRecipe(recipe)
    }

    override suspend fun getRecipeById(id: String): SavedRecipe? {
        return savedRecipeDao.getRecipeById(id)
    }

    override fun getAllRecipes(): Flow<List<SavedRecipe>> {
        return savedRecipeDao.getAllRecipes()
    }

    override fun searchRecipesByName(query: String): Flow<List<SavedRecipe>> {
        return savedRecipeDao.searchRecipesByName(query)
    }

    override fun getRecipesByMealType(mealType: String): Flow<List<SavedRecipe>> {
        return savedRecipeDao.getRecipesByMealType(mealType)
    }

    override fun getRecipesByMealTypes(mealTypes: List<String>): Flow<List<SavedRecipe>> {
        return if (mealTypes.isEmpty()) {
            getAllRecipes()
        } else {
            savedRecipeDao.getRecipesByMealTypes(mealTypes)
        }
    }

    override fun getFavoriteRecipes(): Flow<List<SavedRecipe>> {
        return savedRecipeDao.getFavoriteRecipes()
    }

    override suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean) {
        savedRecipeDao.updateFavoriteStatus(recipeId, isFavorite)
    }

    override suspend fun clearAllRecipes() {
        savedRecipeDao.clearAllRecipes()
    }
}


