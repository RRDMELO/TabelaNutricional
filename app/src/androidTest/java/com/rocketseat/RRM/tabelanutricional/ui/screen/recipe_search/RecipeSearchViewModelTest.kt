package com.rocketseat.RRM.tabelanutricional.ui.screen.recipe_search

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rocketseat.RRM.tabelanutricional.MainDispatcherRule
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import com.rocketseat.RRM.tabelanutricional.domain.repository.SavedRecipeRepository
import com.rocketseat.RRM.tabelanutricional.domain.usecase.SearchAndFilterRecipesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RecipeSearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeSavedRecipeRepository(
        private val recipes: List<SavedRecipe>
    ) : SavedRecipeRepository {
        var getAllRecipesCalls = 0

        override suspend fun saveRecipe(recipe: SavedRecipe) = Unit
        override suspend fun updateRecipe(recipe: SavedRecipe) = Unit
        override suspend fun deleteRecipe(recipe: SavedRecipe) = Unit
        override suspend fun getRecipeById(id: String): SavedRecipe? = null

        override fun getAllRecipes(): Flow<List<SavedRecipe>> {
            getAllRecipesCalls++
            return flowOf(recipes)
        }

        override fun searchRecipesByName(query: String): Flow<List<SavedRecipe>> = flowOf(recipes)
        override fun getRecipesByMealType(mealType: String): Flow<List<SavedRecipe>> = flowOf(recipes)
        override fun getRecipesByMealTypes(mealTypes: List<String>): Flow<List<SavedRecipe>> = flowOf(recipes)
        override fun getFavoriteRecipes(): Flow<List<SavedRecipe>> = flowOf(recipes)
        override suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean) = Unit
        override suspend fun clearAllRecipes() = Unit
    }

    private fun recipe(
        id: String,
        name: String,
        mealType: String,
        calories: Float = 100f,
        proteins: Float = 10f,
        carbohydrates: Float = 20f,
        fiber: Float = 5f
    ) = SavedRecipe(
        id = id,
        name = name,
        calories = calories,
        proteins = proteins,
        carbohydrates = carbohydrates,
        fiber = fiber,
        mealType = mealType
    )

    @Test
    fun givenSearchQuery_whenEventFires_thenStateUpdatesAndSearchRuns() = runTest {
        val expectedRecipe = recipe("1", "Salada Fit", "Almoço")
        val repository = FakeSavedRecipeRepository(listOf(expectedRecipe))
        val useCase = SearchAndFilterRecipesUseCase(repository)

        val viewModel = RecipeSearchViewModel(useCase)
        advanceUntilIdle()
        val initialGetAllRecipesCalls = repository.getAllRecipesCalls

        viewModel.onEvent(RecipeSearchEvent.SearchQuery("sal"))
        assertEquals("sal", viewModel.state.value.searchQuery)
        assertEquals(initialGetAllRecipesCalls, repository.getAllRecipesCalls)

        advanceTimeBy(300.milliseconds)
        advanceUntilIdle()

        assertEquals(initialGetAllRecipesCalls + 1, repository.getAllRecipesCalls)
        assertEquals(listOf(expectedRecipe), viewModel.state.value.recipes)
    }

    @Test
    fun givenToggleMealType_whenEventFires_thenMealTypeIsAddedAndSearchUsesFilter() = runTest {
        val repository = FakeSavedRecipeRepository(emptyList())
        val useCase = SearchAndFilterRecipesUseCase(repository)
        val viewModel = RecipeSearchViewModel(useCase)
        advanceUntilIdle()
        val initialGetAllRecipesCalls = repository.getAllRecipesCalls

        viewModel.onEvent(RecipeSearchEvent.ToggleMealType("Almoço"))
        advanceUntilIdle()

        assertTrue(viewModel.state.value.selectedMealTypes.contains("Almoço"))
        assertEquals(initialGetAllRecipesCalls + 1, repository.getAllRecipesCalls)
    }

    @Test
    fun givenClearFilters_whenEventFires_thenStateResetsAndSearchUsesDefaults() = runTest {
        val repository = FakeSavedRecipeRepository(emptyList())
        val useCase = SearchAndFilterRecipesUseCase(repository)
        val viewModel = RecipeSearchViewModel(useCase)
        advanceUntilIdle()

        viewModel.onEvent(RecipeSearchEvent.SearchQuery("sal"))
        advanceTimeBy(300.milliseconds)
        advanceUntilIdle()

        viewModel.onEvent(RecipeSearchEvent.ToggleMealType("Almoço"))
        advanceUntilIdle()

        viewModel.onEvent(RecipeSearchEvent.ClearFilters)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("", state.searchQuery)
        assertTrue(state.selectedMealTypes.isEmpty())
        assertEquals(0f to 500f, state.calorieRange)
        assertEquals(0f to 100f, state.proteinRange)
        assertEquals(0f to 200f, state.carbRange)
        assertEquals(0f to 50f, state.fiberRange)
        assertFalse(state.showFilters)
        assertTrue(repository.getAllRecipesCalls > 0)
    }
}


