package com.rocketseat.RRM.tabelanutricional.ui.screen.nav_host

import android.util.Log
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
import com.rocketseat.RRM.tabelanutricional.ui.screen.auth.AuthEvent
import com.rocketseat.RRM.tabelanutricional.ui.screen.auth.AuthScreen
import com.rocketseat.RRM.tabelanutricional.ui.screen.auth.AuthViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsScreen
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.home.HomeScreen
import com.rocketseat.RRM.tabelanutricional.ui.screen.home.HomeViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.recipe_search.RecipeSearchScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val authViewModel = koinViewModel<AuthViewModel>()

    val homeViewModel = koinViewModel<HomeViewModel>()
    val homeUIState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val healthyRecipeDetailsViewModel = koinViewModel<HealthyRecipeDetailsViewModel>()
    val healthyRecipeDetailsUIState by healthyRecipeDetailsViewModel.uiState.collectAsStateWithLifecycle()

    Log.d("MainNavHost", "Iniciando NavHost com startDestination = Auth")

    NavHost(modifier = modifier, navController = navController, startDestination = UIRoute.Auth) {
        composable<UIRoute.Auth> {
            AuthScreen(
                onLoginSuccess = {
                    navController.navigate(UIRoute.Home) {
                        popUpTo(UIRoute.Auth) { inclusive = true }
                    }
                },
                viewModel = authViewModel
            )
        }
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
                onNavigateToSearch = {
                    navController.navigate(UIRoute.RecipeSearch)
                },
                onLogout = {
                    authViewModel.onEvent(AuthEvent.Logout)
                    navController.navigate(UIRoute.Auth) {
                        popUpTo(UIRoute.Home) { inclusive = true }
                    }
                }
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

        composable<UIRoute.RecipeSearch> {
            RecipeSearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onRecipeClick = { recipeId ->
                    Log.d("RecipeSearch", "Recipe clicked: $recipeId")
                    // TODO: Navegar para tela de detalhes da receita salva quando implementada
                }
            )
        }
    }
}
