package com.rocketseat.RRM.tabelanutricional.core.di

import android.content.Context
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.AppDatabase
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.AuthDataStore
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.HealthyRecipeLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.MockHealthyRecipeLocalDataSourceImpl
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.MockWellnessNewsLocalDataSourceImpl
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.WellnessNewsLocalDataSource
import com.rocketseat.RRM.tabelanutricional.data.repository.HealthyRecipeRepositoryImpl
import com.rocketseat.RRM.tabelanutricional.data.repository.HomeContentRepositoryImpl
import com.rocketseat.RRM.tabelanutricional.data.repository.SavedRecipeRepositoryImpl
import com.rocketseat.RRM.tabelanutricional.data.repository.UserRepositoryImpl
import com.rocketseat.RRM.tabelanutricional.domain.repository.HealthyRecipeRepository
import com.rocketseat.RRM.tabelanutricional.domain.repository.HomeContentRepository
import com.rocketseat.RRM.tabelanutricional.domain.repository.SavedRecipeRepository
import com.rocketseat.RRM.tabelanutricional.domain.repository.UserRepository
import com.rocketseat.RRM.tabelanutricional.domain.usecase.GetHealthyRecipeByIdUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.GetHomeContentUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.IsHealthyRecipeFavoriteUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.LoginUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.RegisterUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.SearchAndFilterRecipesUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.UpdateHealthyRecipeIsFavoriteUseCase
import com.rocketseat.RRM.tabelanutricional.ui.screen.auth.AuthViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.healthy_recipe_details.HealthyRecipeDetailsViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.home.HomeViewModel
import com.rocketseat.RRM.tabelanutricional.ui.screen.recipe_search.RecipeSearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {

    val uiModule = module {
        factory { HomeViewModel(get(), get()) }
        viewModelOf(::HealthyRecipeDetailsViewModel)
        viewModelOf(::AuthViewModel)
        viewModelOf(::RecipeSearchViewModel)
    }

    val domainModule = module {
        factory { GetHomeContentUseCase(get()) }
        factory { GetHealthyRecipeByIdUseCase(get()) }
        factory { IsHealthyRecipeFavoriteUseCase(get()) }
        factory { UpdateHealthyRecipeIsFavoriteUseCase(get()) }
        factory { LoginUseCase(get()) }
        factory { RegisterUseCase(get()) }
        factory { SearchAndFilterRecipesUseCase(get()) }
    }

    val dataModule = module {
        // Database
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().userDao() }
        single { get<AppDatabase>().savedRecipeDao() }

        // DataStore
        single { AuthDataStore(androidContext()) }

        // Mock Data Sources
        singleOf(::MockWellnessNewsLocalDataSourceImpl) {
            bind<WellnessNewsLocalDataSource>()
        }

        singleOf(::MockHealthyRecipeLocalDataSourceImpl) {
            bind<HealthyRecipeLocalDataSource>()
        }

        // Repositories
        singleOf(::HomeContentRepositoryImpl) {
            bind<HomeContentRepository>()
        }

        singleOf(::HealthyRecipeRepositoryImpl) {
            bind<HealthyRecipeRepository>()
        }

        single<UserRepository> {
            UserRepositoryImpl(get())
        }

        single<SavedRecipeRepository> {
            SavedRecipeRepositoryImpl(get())
        }
    }
}
