package com.rocketseat.RRM.tabelanutricional.domain.model

import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.WellnessNews

data class HomeContent(
    val wellnessNewsList: List<WellnessNews>,
    val healthyRecipeList: List<HealthyRecipe>,
)
