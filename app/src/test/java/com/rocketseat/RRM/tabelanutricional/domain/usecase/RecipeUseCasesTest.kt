package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipeNutrient
import com.rocketseat.RRM.tabelanutricional.data.model.NutrientUnit
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import com.rocketseat.RRM.tabelanutricional.domain.repository.HealthyRecipeRepository
import com.rocketseat.RRM.tabelanutricional.domain.repository.SavedRecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class RecipeUseCasesTest {

    private class FakeSavedRecipeRepository(
        private val recipes: List<SavedRecipe>
    ) : SavedRecipeRepository {
        override suspend fun saveRecipe(recipe: SavedRecipe) = Unit
        override suspend fun updateRecipe(recipe: SavedRecipe) = Unit
        override suspend fun deleteRecipe(recipe: SavedRecipe) = Unit
        override suspend fun getRecipeById(id: String): SavedRecipe? = recipes.firstOrNull { it.id == id }
        override fun getAllRecipes(): Flow<List<SavedRecipe>> = flowOf(recipes)
        override fun searchRecipesByName(query: String): Flow<List<SavedRecipe>> = flowOf(recipes)
        override fun getRecipesByMealType(mealType: String): Flow<List<SavedRecipe>> = flowOf(recipes)
        override fun getRecipesByMealTypes(mealTypes: List<String>): Flow<List<SavedRecipe>> = flowOf(recipes)
        override fun getFavoriteRecipes(): Flow<List<SavedRecipe>> = flowOf(recipes.filter { it.isFavorite })
        override suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean) = Unit
        override suspend fun clearAllRecipes() = Unit
    }

    private class FakeHealthyRecipeRepository : HealthyRecipeRepository {
        var getHealthyRecipeByIdCalls = 0
        var checkIsFavoriteCalls = 0
        var updateIsFavoriteCalls = 0
        var lastUpdatedId: String? = null
        var lastUpdatedFavorite: Boolean? = null
        var recipeResult: HealthyRecipe? = null
        var favoriteResult: Boolean = false

        override suspend fun getHealthyRecipeById(id: String): HealthyRecipe? {
            getHealthyRecipeByIdCalls++
            return recipeResult
        }

        override suspend fun checkIsFavorite(id: String): Boolean {
            checkIsFavoriteCalls++
            return favoriteResult
        }

        override suspend fun updateIsFavorite(id: String, isFavorite: Boolean) {
            updateIsFavoriteCalls++
            lastUpdatedId = id
            lastUpdatedFavorite = isFavorite
        }
    }

    private fun nutrient(value: Float) = HealthyRecipeNutrient(value, 1, NutrientUnit.GRAM)

    private fun healthyRecipe(
        id: String = "11111111-1111-1111-1111-111111111111",
        name: String = "Receita Saudável",
    ): HealthyRecipe = HealthyRecipe(
        id = UUID.fromString(id),
        name = name,
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

    private fun recipe(
        id: String,
        name: String,
        mealType: String,
        calories: Float,
        proteins: Float,
        carbohydrates: Float,
        fiber: Float,
        isFavorite: Boolean = false,
    ) = SavedRecipe(
        id = id,
        name = name,
        calories = calories,
        proteins = proteins,
        carbohydrates = carbohydrates,
        fiber = fiber,
        isFavorite = isFavorite,
        mealType = mealType,
    )

    @Test
    fun givenQueryBlankAndDefaultRanges_whenSearch_thenReturnsAllRecipes() = runBlocking {
        val recipes = listOf(
            recipe("1", "Omelete Proteico", "Café da manhã", 250f, 20f, 10f, 2f),
            recipe("2", "Salada Fit", "Almoço", 150f, 5f, 12f, 6f),
        )
        val useCase = SearchAndFilterRecipesUseCase(FakeSavedRecipeRepository(recipes))

        val result = useCase().first()

        assertEquals(recipes, result)
    }

    @Test
    fun givenQuery_whenSearch_thenReturnsMatchingRecipesIgnoringCase() = runBlocking {
        val recipes = listOf(
            recipe("1", "Omelete Proteico", "Café da manhã", 250f, 20f, 10f, 2f),
            recipe("2", "Salada Fit", "Almoço", 150f, 5f, 12f, 6f),
        )
        val useCase = SearchAndFilterRecipesUseCase(FakeSavedRecipeRepository(recipes))

        val result = useCase(query = "sal").first()

        assertEquals(listOf(recipes[1]), result)
    }

    @Test
    fun givenFilters_whenSearch_thenReturnsOnlyMatchingRecipes() = runBlocking {
        val recipes = listOf(
            recipe("1", "Omelete Proteico", "Café da manhã", 250f, 20f, 10f, 2f),
            recipe("2", "Salada Fit", "Almoço", 150f, 5f, 12f, 6f),
            recipe("3", "Pão Doce", "Lanche", 500f, 8f, 70f, 1f),
        )
        val useCase = SearchAndFilterRecipesUseCase(FakeSavedRecipeRepository(recipes))

        val result = useCase(
            query = "sal",
            selectedMealTypes = listOf("Almoço"),
            calorieRange = 100f to 200f,
            proteinRange = 0f to 10f,
            carbRange = 0f to 20f,
            fiberRange = 0f to 10f,
        ).first()

        assertEquals(listOf(recipes[1]), result)
    }

    @Test
    fun givenHealthyRecipeId_whenGettingRecipeById_thenDelegatesToRepository() = runBlocking {
        val repository = FakeHealthyRecipeRepository().apply {
            recipeResult = healthyRecipe()
        }
        val useCase = GetHealthyRecipeByIdUseCase(repository)

        val result = useCase("abc")

        assertEquals(1, repository.getHealthyRecipeByIdCalls)
        assertEquals(repository.recipeResult, result)
    }

    @Test
    fun givenFavoriteStatus_whenCheckingFavorite_thenReturnsRepositoryValue() = runBlocking {
        val repository = FakeHealthyRecipeRepository().apply {
            favoriteResult = true
        }
        val useCase = IsHealthyRecipeFavoriteUseCase(repository)

        val result = useCase("abc")

        assertTrue(result)
        assertEquals(1, repository.checkIsFavoriteCalls)
    }

    @Test
    fun givenFavoriteUpdate_whenInvoked_thenDelegatesToRepository() = runBlocking {
        val repository = FakeHealthyRecipeRepository()
        val useCase = UpdateHealthyRecipeIsFavoriteUseCase(repository)

        useCase("abc", true)

        assertEquals(1, repository.updateIsFavoriteCalls)
        assertEquals("abc", repository.lastUpdatedId)
        assertEquals(true, repository.lastUpdatedFavorite)
    }

    @Test
    fun givenEmailAndPassword_whenLogin_thenDelegatesToRepository() = runBlocking {
        val expectedUser = com.rocketseat.RRM.tabelanutricional.data.model.User(
            id = 1,
            email = "user@email.com",
            username = "Bernardo",
            password = "123456",
        )
        val repository = object : com.rocketseat.RRM.tabelanutricional.domain.repository.UserRepository {
            override suspend fun registerUser(username: String, email: String, password: String): Boolean = true
            override suspend fun loginUser(email: String, password: String) = expectedUser
            override suspend fun getUserByEmail(email: String) = expectedUser
            override suspend fun userExists(email: String): Boolean = false
        }
        val useCase = LoginUseCase(repository)

        val result = useCase("user@email.com", "123456")

        assertEquals(expectedUser, result)
    }
}

