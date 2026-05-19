package com.rocketseat.RRM.tabelanutricional.data.repository

import com.rocketseat.RRM.tabelanutricional.data.datasource.local.HealthyRecipeLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.WellnessNewsLocalDataSource
import com.rocketseat.RRM.tabelanutricional.domain.model.HomeContent
import com.rocketseat.RRM.tabelanutricional.domain.repository.HomeContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class HomeContentRepositoryImpl(
    private val healthyRecipeLocalDataSource: HealthyRecipeLocalDataSource,
    private val wellnessNewsLocalDataSource: WellnessNewsLocalDataSource
): HomeContentRepository {
    override suspend fun getHomeContent(): HomeContent = withContext(Dispatchers.Default) {
        val wellnessNewsList = wellnessNewsLocalDataSource.getAllWellnessNews()
        val healthyRecipeList = healthyRecipeLocalDataSource.getAllHealthyRecipes()

        delay(1_000)

        return@withContext HomeContent(
            wellnessNewsList = wellnessNewsList,
            healthyRecipeList = healthyRecipeList
        )
    }
}
