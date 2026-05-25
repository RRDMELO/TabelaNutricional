package com.rocketseat.RRM.tabelanutricional.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedRecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: SavedRecipe)

    @Update
    suspend fun updateRecipe(recipe: SavedRecipe)

    @Delete
    suspend fun deleteRecipe(recipe: SavedRecipe)

    @Query("SELECT * FROM saved_recipes WHERE id = :id LIMIT 1")
    suspend fun getRecipeById(id: String): SavedRecipe?

    @Query("SELECT * FROM saved_recipes ORDER BY savedAt DESC")
    fun getAllRecipes(): Flow<List<SavedRecipe>>

    @Query("SELECT * FROM saved_recipes WHERE name LIKE '%' || :query || '%' ORDER BY savedAt DESC")
    fun searchRecipesByName(query: String): Flow<List<SavedRecipe>>

    @Query("SELECT * FROM saved_recipes WHERE mealType = :mealType ORDER BY savedAt DESC")
    fun getRecipesByMealType(mealType: String): Flow<List<SavedRecipe>>

    @Query("SELECT * FROM saved_recipes WHERE mealType IN (:mealTypes) ORDER BY savedAt DESC")
    fun getRecipesByMealTypes(mealTypes: List<String>): Flow<List<SavedRecipe>>

    @Query("SELECT * FROM saved_recipes WHERE isFavorite = 1 ORDER BY savedAt DESC")
    fun getFavoriteRecipes(): Flow<List<SavedRecipe>>

    @Query("UPDATE saved_recipes SET isFavorite = :isFavorite WHERE id = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean)

    @Query("DELETE FROM saved_recipes")
    suspend fun clearAllRecipes()
}

