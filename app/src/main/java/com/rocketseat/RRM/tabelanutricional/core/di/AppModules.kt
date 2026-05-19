package com.rocketseat.RRM.tabelanutricional.core.di

import com.rocketseat.RRM.tabelanutricional.data.datasource.local.HealthyRecipeLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.MockHealthyRecipeLocalDataSourceImpl
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.MockWellnessNewsLocalDataSourceImpl
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.WellnessNewsLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.repository.HealthyRecipeRepositoryImpl
import com.rocketseat.RRM.tabelanutricional.data.repository.HomeContentRepositoryImpl
import com.rocketseat.RRM.tabelanutricional.domain.repository.HealthyRecipeRepository
import com.rocketseat.RRM.tabelanutricional.domain.repository.HomeContentRepository
import com.rocketseat.RRM.tabelanutricional.domain.usecase.GetHealthyRecipeByIdUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.GetHomeContentUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.IsHealthyRecipeFavoriteUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.UpdateHealthyRecipeIsFavoriteUseCase
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.home.HomeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {

    val uiModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::HealthyRecipeDetailsViewModel)
    }

    val domainModule = module {
        factory { GetHomeContentUseCase(get()) }
        factory { GetHealthyRecipeByIdUseCase(get()) }
        factory { IsHealthyRecipeFavoriteUseCase(get()) }
        factory { UpdateHealthyRecipeIsFavoriteUseCase(get()) }
    }

    val dataModule = module {
        singleOf(::MockWellnessNewsLocalDataSourceImpl) {
            bind<WellnessNewsLocalDataSource>()
        }

        singleOf(::MockHealthyRecipeLocalDataSourceImpl) {
            bind<HealthyRecipeLocalDataSource>()
        }

        singleOf(::HomeContentRepositoryImpl) {
            bind<HomeContentRepository>()
        }

        singleOf(::HealthyRecipeRepositoryImpl) {
            bind<HealthyRecipeRepository>()
        }
    }
}
