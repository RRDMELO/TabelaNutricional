package com.rocketseat.RRM.tabelanutricional.ui.screen.recipe_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.RRM.tabelanutricional.domain.usecase.SearchAndFilterRecipesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeSearchViewModel(
    private val searchAndFilterRecipesUseCase: SearchAndFilterRecipesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeSearchUIState())
    val state: StateFlow<RecipeSearchUIState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        Log.d("RecipeSearchViewModel", "Init called")
        viewModelScope.launch {
            // Aguarda um pouco para garantir que os dados de teste foram inseridos
            delay(1000)
            loadRecipes()
        }
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300) // Espera 300ms após o usuário parar de digitar
                .collect { query ->
                    Log.d("RecipeSearchViewModel", "Search query changed: $query")
                    performSearch(query)
                }
        }
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            Log.d("RecipeSearchViewModel", "Loading recipes...")
            _state.update { it.copy(isLoading = true) }
            try {
                performSearch(_state.value.searchQuery)
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun performSearch(query: String) {
        Log.d("RecipeSearchViewModel", "Performing search with query: '$query'")
        val currentState = _state.value
        val mealTypes = if (currentState.selectedMealTypes.isEmpty()) {
            emptyList()
        } else {
            currentState.selectedMealTypes
        }

        try {
            val recipes = searchAndFilterRecipesUseCase(
                query = query,
                selectedMealTypes = mealTypes,
                calorieRange = currentState.calorieRange,
                proteinRange = currentState.proteinRange,
                carbRange = currentState.carbRange,
                fiberRange = currentState.fiberRange
            ).first()

            Log.d("RecipeSearchViewModel", "Received ${recipes.size} recipes")
            recipes.forEach { recipe ->
                Log.d("RecipeSearchViewModel", "Recipe: ${recipe.name}")
            }
            _state.update { state ->
                state.copy(recipes = recipes)
            }
        } catch (e: Exception) {
            Log.e("RecipeSearchViewModel", "Error performing search", e)
        }
    }

    fun onEvent(event: RecipeSearchEvent) {
        when (event) {
            is RecipeSearchEvent.SearchQuery -> {
                _state.update { it.copy(searchQuery = event.query) }
                _searchQuery.value = event.query
            }

            is RecipeSearchEvent.ToggleMealType -> {
                _state.update { state ->
                    val newMealTypes = if (state.selectedMealTypes.contains(event.mealType)) {
                        state.selectedMealTypes - event.mealType
                    } else {
                        state.selectedMealTypes + event.mealType
                    }
                    state.copy(selectedMealTypes = newMealTypes)
                }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.UpdateCalorieRange -> {
                _state.update { 
                    it.copy(calorieRange = event.min to event.max)
                }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.UpdateProteinRange -> {
                _state.update { 
                    it.copy(proteinRange = event.min to event.max)
                }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.UpdateCarbRange -> {
                _state.update { 
                    it.copy(carbRange = event.min to event.max)
                }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.UpdateFiberRange -> {
                _state.update { 
                    it.copy(fiberRange = event.min to event.max)
                }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.UpdateFatRange -> {
                _state.update {
                    it.copy(fatRange = event.min to event.max)
                }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.UpdateSugarRange -> {
                _state.update {
                    it.copy(sugarRange = event.min to event.max)
                }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.ToggleFilters -> {
                _state.update { it.copy(showFilters = !it.showFilters) }
            }

            is RecipeSearchEvent.ApplyFilters -> {
                _state.update { it.copy(showFilters = false) }
                viewModelScope.launch {
                    performSearch(_state.value.searchQuery)
                }
            }

            is RecipeSearchEvent.ClearFilters -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        selectedMealTypes = emptyList(),
                        calorieRange = 0f to 500f,
                        proteinRange = 0f to 100f,
                        carbRange = 0f to 200f,
                        fiberRange = 0f to 50f
                    )
                }
                _searchQuery.value = ""
                viewModelScope.launch {
                    performSearch("")
                }
            }
        }
    }
}

