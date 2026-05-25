package com.rocketseat.RRM.tabelanutricional.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rocketseat.RRM.tabelanutricional.data.model.SavedRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.User

@Database(
    entities = [User::class, SavedRecipe::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun savedRecipeDao(): SavedRecipeDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tabela_nutricional.db"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}

