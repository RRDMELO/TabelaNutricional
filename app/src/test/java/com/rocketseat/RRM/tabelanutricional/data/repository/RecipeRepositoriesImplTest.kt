package com.rocketseat.RRM.tabelanutricional.data.repository

import com.rocketseat.RRM.tabelanutricional.data.datasource.local.HealthyRecipeLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.SavedRecipeDao
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipeNutrient
import com.rocketseat.RRM.tabelanutricional.data.model.NutrientUnit
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class RecipeRepositoriesImplTest {

    private class FakeSavedRecipeDao(
        initialRecipes: List<SavedRecipe>
    ) : SavedRecipeDao {
        val recipes = initialRecipes.toMutableList()
        var insertCalls = 0
        var updateCalls = 0
        var deleteCalls = 0
        var clearAllCalls = 0
        var updateFavoriteStatusCalls = 0
        var lastFavoriteRecipeId: String? = null
        var lastFavoriteValue: Boolean? = null

        override suspend fun insertRecipe(recipe: SavedRecipe) {
            insertCalls++
            recipes.add(recipe)
        }

        override suspend fun updateRecipe(recipe: SavedRecipe) {
            updateCalls++
        }

        override suspend fun deleteRecipe(recipe: SavedRecipe) {
            deleteCalls++
            recipes.remove(recipe)
        }

        override suspend fun getRecipeById(id: String): SavedRecipe? = recipes.firstOrNull { it.id == id }

        override fun getAllRecipes(): Flow<List<SavedRecipe>> = flowOf(recipes)

        override fun searchRecipesByName(query: String): Flow<List<SavedRecipe>> =
            flowOf(recipes.filter { it.name.contains(query, ignoreCase = true) })

        override fun getRecipesByMealType(mealType: String): Flow<List<SavedRecipe>> =
            flowOf(recipes.filter { it.mealType == mealType })

        override fun getRecipesByMealTypes(mealTypes: List<String>): Flow<List<SavedRecipe>> =
            flowOf(recipes.filter { it.mealType in mealTypes })

        override fun getFavoriteRecipes(): Flow<List<SavedRecipe>> =
            flowOf(recipes.filter { it.isFavorite })

        override suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean) {
            updateFavoriteStatusCalls++
            lastFavoriteRecipeId = recipeId
            lastFavoriteValue = isFavorite
        }

        override suspend fun clearAllRecipes() {
            clearAllCalls++
            recipes.clear()
        }
    }

    private class FakeHealthyRecipeLocalDataSource(
        private val resultById: HealthyRecipe?,
        private val favoriteResult: Boolean,
        private val allRecipes: List<HealthyRecipe>
    ) : HealthyRecipeLocalDataSource {
        var getByIdCalls = 0
        var checkFavoriteCalls = 0
        var updateFavoriteCalls = 0
        var lastUpdatedId: String? = null
        var lastUpdatedFavorite: Boolean? = null

        override suspend fun getAllHealthyRecipes(): List<HealthyRecipe> = allRecipes

        override suspend fun getHealthyRecipeById(id: String): HealthyRecipe? {
            getByIdCalls++
            return resultById
        }

        override suspend fun checkIsFavorite(id: String): Boolean {
            checkFavoriteCalls++
            return favoriteResult
        }

        override suspend fun updateIsFavorite(id: String, isFavorite: Boolean) {
            updateFavoriteCalls++
            lastUpdatedId = id
            lastUpdatedFavorite = isFavorite
        }
    }

    private fun nutrient(value: Float) = HealthyRecipeNutrient(value, 1, NutrientUnit.GRAM)

    private fun healthyRecipe(): HealthyRecipe = HealthyRecipe(
        id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
        name = "Receita Saudável",
        imageRes = 1,
        calories = nutrient(100f),
        proteins = nutrient(10f),
        carbohydrates = nutrient(20f),
        fiber = nutrient(5f),
        sugar = nutrient(3f),
        fat = nutrient(4f),
        totalFat = nutrient(4f),
        saturatedFat = nutrient(1f),
        transFat = nutrient(0f),
        cholesterol = nutrient(0f),
        sodium = nutrient(50f),
        potassium = nutrient(200f),
        calcium = nutrient(80f),
        iron = nutrient(2f),
        magnesium = nutrient(30f),
        vitaminC = nutrient(12f),
        vitaminD = nutrient(1f),
        vitaminB6 = nutrient(0.5f),
        totalPortionInGrams = 100,
    )

    private fun recipe(id: String, name: String, mealType: String, isFavorite: Boolean = false) =
        SavedRecipe(
            id = id,
            name = name,
            calories = 100f,
            proteins = 10f,
            carbohydrates = 20f,
            fiber = 5f,
            isFavorite = isFavorite,
            mealType = mealType,
        )

    @Test
    fun givenRecipe_whenRepositorySavesThenDelegatesToDao() = runBlocking {
        val dao = FakeSavedRecipeDao(emptyList())
        val repository = SavedRecipeRepositoryImpl(dao)
        val newRecipe = recipe("1", "Salada Fit", "Almoço")

        repository.saveRecipe(newRecipe)

        assertEquals(1, dao.insertCalls)
        assertEquals(listOf(newRecipe), dao.recipes)
    }

    @Test
    fun givenMealTypesEmpty_whenGettingRecipes_thenUsesGetAllRecipes() = runBlocking {
        val recipes = listOf(
            recipe("1", "Salada Fit", "Almoço"),
            recipe("2", "Omelete", "Café da manhã")
        )
        val dao = FakeSavedRecipeDao(recipes)
        val repository = SavedRecipeRepositoryImpl(dao)

        val result = repository.getRecipesByMealTypes(emptyList()).first()

        assertEquals(recipes, result)
    }

    @Test
    fun givenMealTypes_whenGettingRecipes_thenFiltersByProvidedTypes() = runBlocking {
        val recipes = listOf(
            recipe("1", "Salada Fit", "Almoço"),
            recipe("2", "Omelete", "Café da manhã"),
            recipe("3", "Sopa", "Jantar")
        )
        val dao = FakeSavedRecipeDao(recipes)
        val repository = SavedRecipeRepositoryImpl(dao)

        val result = repository.getRecipesByMealTypes(listOf("Almoço", "Jantar")).first()

        assertEquals(listOf(recipes[0], recipes[2]), result)
    }

    @Test
    fun givenFavoriteStatus_whenUpdatingThenDelegatesToDao() = runBlocking {
        val dao = FakeSavedRecipeDao(emptyList())
        val repository = SavedRecipeRepositoryImpl(dao)

        repository.updateFavoriteStatus("abc", true)

        assertEquals(1, dao.updateFavoriteStatusCalls)
        assertEquals("abc", dao.lastFavoriteRecipeId)
        assertEquals(true, dao.lastFavoriteValue)
    }

    @Test
    fun givenHealthyRecipeRepository_whenAccessingMethods_thenDelegatesToDataSource() = runBlocking {
        val expectedRecipe = healthyRecipe()
        val dataSource = FakeHealthyRecipeLocalDataSource(
            resultById = expectedRecipe,
            favoriteResult = true,
            allRecipes = listOf(expectedRecipe)
        )
        val repository = HealthyRecipeRepositoryImpl(dataSource)

        val recipeById = repository.getHealthyRecipeById(expectedRecipe.id.toString())
        val isFavorite = repository.checkIsFavorite(expectedRecipe.id.toString())
        repository.updateIsFavorite(expectedRecipe.id.toString(), false)

        assertEquals(expectedRecipe, recipeById)
        assertEquals(true, isFavorite)
        assertEquals(1, dataSource.getByIdCalls)
        assertEquals(1, dataSource.checkFavoriteCalls)
        assertEquals(1, dataSource.updateFavoriteCalls)
        assertEquals(expectedRecipe.id.toString(), dataSource.lastUpdatedId)
        assertEquals(false, dataSource.lastUpdatedFavorite)
    }
}

