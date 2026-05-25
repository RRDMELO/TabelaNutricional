package com.rocketseat.RRM.tabelanutricional.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "saved_recipes")
data class SavedRecipe(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val calories: Float,
    val proteins: Float,
    val carbohydrates: Float,
    val fiber: Float,
    val isFavorite: Boolean = false,
    val savedAt: Long = System.currentTimeMillis(),
    val mealType: String = "", // Café da manhã, Almoço, Jantar, Lanche
    val imageResId: Int? = null,
    val imageUrl: String? = null // URL da imagem da receita
)

