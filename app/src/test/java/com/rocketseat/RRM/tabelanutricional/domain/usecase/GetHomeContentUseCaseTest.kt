package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipeNutrient
import com.rocketseat.RRM.tabelanutricional.data.model.NutrientUnit
import com.rocketseat.RRM.tabelanutricional.data.model.WellnessNews
import com.rocketseat.RRM.tabelanutricional.data.model.WellnessNewsTag
import com.rocketseat.RRM.tabelanutricional.domain.model.HomeContent
import com.rocketseat.RRM.tabelanutricional.domain.repository.HomeContentRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class GetHomeContentUseCaseTest {

    private class FakeHomeContentRepository(
        private val result: HomeContent,
    ) : HomeContentRepository {
        var calls = 0

        override suspend fun getHomeContent(): HomeContent {
            calls++
            return result
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

    private fun wellnessNews(): WellnessNews = WellnessNews(
        id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
        title = "Bem-estar no prato",
        imageRes = 1,
        readTimeInMinutes = 5,
        tags = listOf(WellnessNewsTag.WELLNESS),
    )

    @Test
    fun givenHomeContent_whenInvokingUseCase_thenEmitsRepositoryContent() = runBlocking {
        val expected = HomeContent(
            wellnessNewsList = listOf(wellnessNews()),
            healthyRecipeList = listOf(healthyRecipe()),
        )
        val repository = FakeHomeContentRepository(expected)
        val useCase = GetHomeContentUseCase(repository)

        val result = useCase().first()

        assertEquals(1, repository.calls)
        assertEquals(expected, result)
    }
}

