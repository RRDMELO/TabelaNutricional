package com.rocketseat.RRM.tabelanutricional

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.rocketseat.RRM.tabelanutricional.core.di.AppModules.dataModule
import com.rocketseat.RRM.tabelanutricional.core.di.AppModules.domainModule
import com.rocketseat.RRM.tabelanutricional.core.di.AppModules.uiModule
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.AppDatabase
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MainApplication: Application(), ImageLoaderFactory {

    // ...existing code...

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
            }
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MainApplication", "onCreate called")

        // Inicializar a base de dados
        AppDatabase.getInstance(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(
                uiModule,
                domainModule,
                dataModule
            )
        }

        // Inicializar dados de teste
        initializeTestData()
    }

    private fun initializeTestData() {
        val database = AppDatabase.getInstance(this)
        val dao = database.savedRecipeDao()
        Log.d("MainApplication", "initializeTestData called")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("MainApplication", "Starting database initialization...")

                // Receitas de teste
                val testRecipes = listOf(
                    SavedRecipe(
                        name = "Salada Verde",
                        calories = 150f,
                        proteins = 8f,
                        carbohydrates = 20f,
                        fiber = 5f,
                        mealType = "LUNCH",
                        imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Peito de Frango Grelhado",
                        calories = 250f,
                        proteins = 40f,
                        carbohydrates = 0f,
                        fiber = 0f,
                        mealType = "LUNCH",
                        imageUrl = "https://images.unsplash.com/photo-1598598588777-fc86f192c635?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Ovos Mexidos",
                        calories = 180f,
                        proteins = 15f,
                        carbohydrates = 2f,
                        fiber = 0f,
                        mealType = "BREAKFAST",
                        imageUrl = "https://images.unsplash.com/photo-1541519227354-08fa5d50c44d?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Salada de Frutas",
                        calories = 120f,
                        proteins = 2f,
                        carbohydrates = 30f,
                        fiber = 4f,
                        mealType = "SNACK",
                        imageUrl = "https://images.unsplash.com/photo-1584411694712-37a49eaba549?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Arroz Integral com Feijão",
                        calories = 280f,
                        proteins = 12f,
                        carbohydrates = 50f,
                        fiber = 6f,
                        mealType = "LUNCH",
                        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Yogurte Natural",
                        calories = 100f,
                        proteins = 10f,
                        carbohydrates = 8f,
                        fiber = 0f,
                        mealType = "BREAKFAST",
                        imageUrl = "https://images.unsplash.com/photo-1488477181946-6428a0291840?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Brócolis Cozido",
                        calories = 55f,
                        proteins = 6f,
                        carbohydrates = 10f,
                        fiber = 3f,
                        mealType = "DINNER",
                        imageUrl = "https://images.unsplash.com/photo-1584270565173-e12f52d1d4a7?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Salmão Assado",
                        calories = 320f,
                        proteins = 35f,
                        carbohydrates = 0f,
                        fiber = 0f,
                        mealType = "DINNER",
                        imageUrl = "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Smoothie de Banana",
                        calories = 180f,
                        proteins = 8f,
                        carbohydrates = 35f,
                        fiber = 3f,
                        mealType = "BREAKFAST",
                        imageUrl = "https://images.unsplash.com/photo-1505252585461-04db1267ae5b?w=400&h=400&fit=crop"
                    ),
                    SavedRecipe(
                        name = "Almêndoas",
                        calories = 200f,
                        proteins = 7f,
                        carbohydrates = 7f,
                        fiber = 4f,
                        mealType = "SNACK",
                        imageUrl = "https://images.unsplash.com/photo-1585518419759-fdb3e73990f4?w=400&h=400&fit=crop"
                    )
                )

                Log.d("MainApplication", "Clearing existing recipes...")
                dao.clearAllRecipes()

                Log.d("MainApplication", "Inserting ${testRecipes.size} test recipes...")
                testRecipes.forEach { recipe ->
                    dao.insertRecipe(recipe)
                    Log.d("MainApplication", "Inserted: ${recipe.name}")
                }

                Log.d("MainApplication", "Database initialization completed!")
            } catch (e: Exception) {
                Log.e("MainApplication", "Error initializing database", e)
                e.printStackTrace()
            }
        }
    }
}
