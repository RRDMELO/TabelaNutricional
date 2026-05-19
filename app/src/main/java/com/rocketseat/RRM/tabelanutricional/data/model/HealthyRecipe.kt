package com.rocketseat.RRM.tabelanutricional.data.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class HealthyRecipe(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @DrawableRes val imageRes: Int,
    val calories: HealthyRecipeNutrient,
    val proteins: HealthyRecipeNutrient,
    val carbohydrates: HealthyRecipeNutrient,
    val fiber: HealthyRecipeNutrient,
    val sugar: HealthyRecipeNutrient,
    val fat: HealthyRecipeNutrient,
    val totalFat: HealthyRecipeNutrient,
    val saturatedFat: HealthyRecipeNutrient,
    val transFat: HealthyRecipeNutrient,
    val cholesterol: HealthyRecipeNutrient,
    val sodium: HealthyRecipeNutrient,
    val potassium: HealthyRecipeNutrient,
    val calcium: HealthyRecipeNutrient,
    val iron: HealthyRecipeNutrient,
    val magnesium: HealthyRecipeNutrient,
    val vitaminC: HealthyRecipeNutrient,
    val vitaminD: HealthyRecipeNutrient,
    val vitaminB6: HealthyRecipeNutrient,
    val totalPortionInGrams: Int
)
