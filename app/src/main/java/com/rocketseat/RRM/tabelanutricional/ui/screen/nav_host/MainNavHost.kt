package com.rocketseat.RRM.tabelanutricional.ui.screen.nav_host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rocketseat.RRM.tabelanutricional.core.navigation.UIArgument
import com.rocketseat.RRM.tabelanutricional.core.navigation.UIRoute
import com.rocketseat.RRM.tabelanutricional.data.model.mock.mockHealthyRecipes
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsScreen
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.home.HomeScreen
import com.rocketseat.RRM.tabelanutricional.ui.screen.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val homeViewModel = koinViewModel<HomeViewModel>()
    val homeUIState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val healthyRecipeDetailsViewModel = koinViewModel<HealthyRecipeDetailsViewModel>()
    val healthyRecipeDetailsUIState by healthyRecipeDetailsViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(modifier = modifier, navController = navController, startDestination = UIRoute.Home) {
        composable<UIRoute.Home> {
            HomeScreen(
                uiState = homeUIState,
                onEvent = homeViewModel::onEvent,
                onNavigateToDetails = { selectedHealthyRecipeId ->
                    navController.navigate(
                        UIRoute.HealthRecipeDetails(
                            healthyRecipeId = selectedHealthyRecipeId
                        )
                    )
                },
            )
        }

        composable<UIRoute.HealthRecipeDetails> { navBackStackEntry ->
            val healthyRecipeId = navBackStackEntry.arguments?.getString(UIArgument.HEALTHY_RECIPE_ID.key)
            healthyRecipeId?.let {
                val healthyRecipe =
                    mockHealthyRecipes.find { healthyRecipe -> healthyRecipeId == healthyRecipe.id.toString() }
                if(healthyRecipe == null)  return@composable

                HealthyRecipeDetailsScreen(
                    id = healthyRecipeId,
                    uiState = healthyRecipeDetailsUIState,
                    onEvent = healthyRecipeDetailsViewModel::onEvent,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
        }
    }
}
