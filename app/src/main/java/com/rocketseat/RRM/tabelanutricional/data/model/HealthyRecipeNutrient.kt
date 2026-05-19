package com.rocketseat.RRM.tabelanutricional.data.model

import androidx.annotation.StringRes

data class HealthyRecipeNutrient(
    val value: Float,
    @StringRes val nameRes: Int,
    val unit: NutrientUnit
)
