package com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details

import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipeNutrient
import com.rocketseat.RRM.tabelanutricional.data.model.NutrientUnit
import com.rocketseat.RRM.tabelanutricional.domain.repository.HealthyRecipeRepository
import com.rocketseat.RRM.tabelanutricional.domain.usecase.GetHealthyRecipeByIdUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.IsHealthyRecipeFavoriteUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.UpdateHealthyRecipeIsFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
@Ignore("Executa em androidTest para evitar diferenças de ambiente no JVM")
class HealthyRecipeDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = com.rocketseat.RRM.tabelanutricional.MainDispatcherRule()

    private class FakeHealthyRecipeRepository : HealthyRecipeRepository {
        var getByIdCalls = 0
        var checkFavoriteCalls = 0
        var updateFavoriteCalls = 0
        var lastId: String? = null
        var lastIsFavorite: Boolean? = null
        var recipeResult: HealthyRecipe? = null
        var favoriteResult: Boolean = false

        override suspend fun getHealthyRecipeById(id: String): HealthyRecipe? {
            getByIdCalls++
            lastId = id
            return recipeResult
        }

        override suspend fun checkIsFavorite(id: String): Boolean {
            checkFavoriteCalls++
            lastId = id
            return favoriteResult
        }

        override suspend fun updateIsFavorite(id: String, isFavorite: Boolean) {
            updateFavoriteCalls++
            lastId = id
            lastIsFavorite = isFavorite
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

    @Test
    fun givenFindHealthyRecipeEvent_whenInvoked_thenUpdatesStateWithRecipeAndFavoriteStatus() = runTest {
        val expectedRecipe = healthyRecipe()
        val repository = FakeHealthyRecipeRepository().apply {
            recipeResult = expectedRecipe
            favoriteResult = true
        }
        val viewModel = HealthyRecipeDetailsViewModel(
            updateHealthyRecipeIsFavoriteUseCase = UpdateHealthyRecipeIsFavoriteUseCase(repository),
            getHealthyRecipeByIdUseCase = GetHealthyRecipeByIdUseCase(repository),
            isHealthyRecipeFavoriteUseCase = IsHealthyRecipeFavoriteUseCase(repository)
        )

        viewModel.onEvent(HealthyRecipeDetailsEvent.FindHealthyRecipeById(expectedRecipe.id.toString()))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(expectedRecipe, state.healthyRecipe)
        assertTrue(state.isFavorite)
        assertEquals(1, repository.getByIdCalls)
        assertEquals(1, repository.checkFavoriteCalls)
        assertEquals(expectedRecipe.id.toString(), repository.lastId)
    }

    @Test
    fun givenUpdateIsFavoriteEvent_whenInvoked_thenDelegatesToUpdateUseCase() = runTest {
        val repository = FakeHealthyRecipeRepository()
        val viewModel = HealthyRecipeDetailsViewModel(
            updateHealthyRecipeIsFavoriteUseCase = UpdateHealthyRecipeIsFavoriteUseCase(repository),
            getHealthyRecipeByIdUseCase = GetHealthyRecipeByIdUseCase(repository),
            isHealthyRecipeFavoriteUseCase = IsHealthyRecipeFavoriteUseCase(repository)
        )

        viewModel.onEvent(HealthyRecipeDetailsEvent.UpdateIsFavorite("abc", true))
        advanceUntilIdle()

        assertEquals(1, repository.updateFavoriteCalls)
        assertEquals("abc", repository.lastId)
        assertEquals(true, repository.lastIsFavorite)
    }

    @Test
    fun givenBackPressed_whenInvoked_thenResetsState() = runTest {
        val expectedRecipe = healthyRecipe()
        val repository = FakeHealthyRecipeRepository().apply {
            recipeResult = expectedRecipe
            favoriteResult = true
        }
        val viewModel = HealthyRecipeDetailsViewModel(
            updateHealthyRecipeIsFavoriteUseCase = UpdateHealthyRecipeIsFavoriteUseCase(repository),
            getHealthyRecipeByIdUseCase = GetHealthyRecipeByIdUseCase(repository),
            isHealthyRecipeFavoriteUseCase = IsHealthyRecipeFavoriteUseCase(repository)
        )

        viewModel.onEvent(HealthyRecipeDetailsEvent.FindHealthyRecipeById(expectedRecipe.id.toString()))
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.healthyRecipe != null)

        viewModel.onEvent(HealthyRecipeDetailsEvent.OnBackPressed)

        assertEquals(HealthyRecipeDetailsUIState(), viewModel.uiState.value)
    }
}



