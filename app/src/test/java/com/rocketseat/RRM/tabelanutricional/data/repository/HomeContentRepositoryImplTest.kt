 package com.rocketseat.RRM.tabelanutricional.data.repository

import com.rocketseat.RRM.tabelanutricional.data.datasource.local.HealthyRecipeLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.WellnessNewsLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipeNutrient
import com.rocketseat.RRM.tabelanutricional.data.model.NutrientUnit
import com.rocketseat.RRM.tabelanutricional.data.model.WellnessNews
import com.rocketseat.RRM.tabelanutricional.data.model.WellnessNewsTag
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class HomeContentRepositoryImplTest {

    private class FakeHealthyRecipeLocalDataSource(
        private val recipes: List<HealthyRecipe>
    ) : HealthyRecipeLocalDataSource {
        override suspend fun getAllHealthyRecipes(): List<HealthyRecipe> = recipes
        override suspend fun getHealthyRecipeById(id: String): HealthyRecipe? = recipes.firstOrNull { it.id.toString() == id }
        override suspend fun checkIsFavorite(id: String): Boolean = false
        override suspend fun updateIsFavorite(id: String, isFavorite: Boolean) = Unit
    }

    private class FakeWellnessNewsLocalDataSource(
        private val news: List<WellnessNews>
    ) : WellnessNewsLocalDataSource {
        override suspend fun getAllWellnessNews(): List<WellnessNews> = news
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

    private fun wellnessNews(): WellnessNews = WellnessNews(
        id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
        title = "Bem-estar no prato",
        imageRes = 1,
        readTimeInMinutes = 5,
        tags = listOf(WellnessNewsTag.WELLNESS),
    )

    @Test
    fun givenLocalDataSources_whenGettingHomeContent_thenCombinesBothLists() = runBlocking {
        val healthyRecipes = listOf(healthyRecipe())
        val wellnessNewsList = listOf(wellnessNews())
        val repository = HomeContentRepositoryImpl(
            healthyRecipeLocalDataSource = FakeHealthyRecipeLocalDataSource(healthyRecipes),
            wellnessNewsLocalDataSource = FakeWellnessNewsLocalDataSource(wellnessNewsList)
        )

        val result = repository.getHomeContent()

        assertEquals(wellnessNewsList, result.wellnessNewsList)
        assertEquals(healthyRecipes, result.healthyRecipeList)
    }
}

