package com.rocketseat.RRM.tabelanutricional.ui.screen.home.Healthy_recipe_details

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rocketseat.RRM.tabelanutricional.data.model.mock.mockHealthyRecipes
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsScreen
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsUIState
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HealthyRecipeDetailsScreenTest {

        @get:Rule
        val composeTestRule = createComposeRule()

    @Test
    fun givenLoadedContentState_whenClickFavoriteButton_thenFavoriteButtonIsSelected() {
        // GIVEN
        val expectedHealthyRecipe = mockHealthyRecipes.first()
        composeTestRule.setContent {
            TabelaNutricionalTheme {
                HealthyRecipeDetailsScreen(
                    id = expectedHealthyRecipe.id.toString(),
                    uiState = HealthyRecipeDetailsUIState(
                        isLoading = false,
                        healthyRecipe = expectedHealthyRecipe,
                        isFavorite = false
                    ),
                    onEvent = {},
                    onNavigateBack = {}
                )
            }
        }

        // WHEN
        composeTestRule.onNodeWithContentDescription("Botão coração").performClick()

        // THEN
        composeTestRule.onNodeWithContentDescription("Botão coração").assertIsSelected()

    }
}