package com.rocketseat.bernardoslailati.tabelanutricional.data.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class HealthyRecipe(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @DrawableRes val imageRes: Int,
    val calories: Float,
    val proteins: Float,
    val carbohydrates: Float,
    val fiber: Float,
    val sugar: Float,
    val fat: Float,
    val saturatedFat: Float,
    val transFat: Float,
    val cholesterol: Float,
    val sodium: Float,
    val potassium: Float,
    val calcium: Float,
    val iron: Float,
    val magnesium: Float,
    val vitaminC: Float,
    val vitaminD: Float,
    val vitaminB6: Float,
    val totalPortionInGrams: Int
)