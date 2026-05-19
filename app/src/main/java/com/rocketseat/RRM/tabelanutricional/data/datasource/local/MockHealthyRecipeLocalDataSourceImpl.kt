package com.rocketseat.RRM.tabelanutricional.data.datasource.local

import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.mock.mockHealthyRecipes
import java.util.UUID

class MockHealthyRecipeLocalDataSourceImpl : HealthyRecipeLocalDataSource {
    private val healthyRecipeFavoriteIdList = mutableListOf<String>()

    override suspend fun getAllHealthyRecipes() = mockHealthyRecipes

    override suspend fun getHealthyRecipeById(id: String): HealthyRecipe? =
        mockHealthyRecipes.find { healthyRecipe -> healthyRecipe.id == UUID.fromString(id) }

    override suspend fun checkIsFavorite(id: String): Boolean =
        healthyRecipeFavoriteIdList.contains(id)

    override suspend fun updateIsFavorite(id: String, isFavorite: Boolean) {
        if (isFavorite) healthyRecipeFavoriteIdList.add(id) else healthyRecipeFavoriteIdList.remove(
            id
        )
    }
}
