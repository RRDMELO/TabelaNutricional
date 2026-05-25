package com.rocketseat.bernardoslailati.tabelanutricional.data.model.mock

import com.rocketseat.bernardoslailati.tabelanutricional.R
import com.rocketseat.bernardoslailati.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.bernardoslailati.tabelanutricional.data.model.HealthyRecipeNutrient
import com.rocketseat.bernardoslailati.tabelanutricional.data.model.NutrientUnit
import com.rocketseat.bernardoslailati.tabelanutricional.data.model.WellnessNews
import com.rocketseat.bernardoslailati.tabelanutricional.data.model.WellnessNewsTag

val mockWellnessNews = listOf(
    WellnessNews(
        imageRes = R.drawable.img_nutritional_news_1,
        tags = listOf(
            WellnessNewsTag.WELLNESS,
            WellnessNewsTag.NUTRITION
        ),
        title = "A importância da tabela nutricional na alimentação consciente",
        readTimeInMinutes = 5
    ),
    WellnessNews(
        imageRes = R.drawable.img_nutritional_news_2,
        tags = listOf(
            WellnessNewsTag.FOOD_EDUCATION
        ),
        title = "Tabelas nutricionais: desvendando os segredos por trás dos rótulos",
        readTimeInMinutes = 4
    ),
    WellnessNews(
        imageRes = R.drawable.img_nutritional_news_3,
        tags = listOf(
            WellnessNewsTag.FOOD_EDUCATION
        ),
        title = "Como ler corretamente uma tabela nutricional",
        readTimeInMinutes = 6
    )
)

private val referenceRecipe = HealthyRecipe(
    name = "Salada variada",
    imageRes = R.drawable.img_assorted_salad,
    calories = HealthyRecipeNutrient(
        value = 221.15f,
        unit = NutrientUnit.KCAL,
        name = R.string.energia
    ),
    proteins = HealthyRecipeNutrient(
        value = 15.13f,
        unit = NutrientUnit.GRAM,
        name = R.string.proteinas
    ),
    carbohydrates = HealthyRecipeNutrient(
        value = 18.40f,
        unit = NutrientUnit.GRAM,
        name = R.string.carboidratos
    ),
    sugar = HealthyRecipeNutrient(
        value = 4.88f,
        unit = NutrientUnit.GRAM,
        name = R.string.acucar
    ),
    fat = HealthyRecipeNutrient(
        value = 5.18f,
        unit = NutrientUnit.GRAM,
        name = R.string.gorduras
    ),
    fiber = HealthyRecipeNutrient(
        value = 5.83f,
        unit = NutrientUnit.GRAM,
        name = R.string.fibra_alimentar
    ),
    saturatedFat = HealthyRecipeNutrient(
        value = 0.86f,
        unit = NutrientUnit.GRAM,
        name = R.string.gorduras_saturadas
    ),
    transFat = HealthyRecipeNutrient(
        value = 0.00f,
        unit = NutrientUnit.GRAM,
        name = R.string.gorduras_trans
    ),
    cholesterol = HealthyRecipeNutrient(
        value = 0.00f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.colesterol
    ),
    sodium = HealthyRecipeNutrient(
        value = 243.03f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.sodio
    ),
    potassium = HealthyRecipeNutrient(
        value = 291.85f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.potassio
    ),
    calcium = HealthyRecipeNutrient(
        value = 49.02f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.calcio
    ),
    iron = HealthyRecipeNutrient(
        value = 2.91f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.ferro
    ),
    magnesium = HealthyRecipeNutrient(
        value = 48.23f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.magnesio
    ),
    vitaminC = HealthyRecipeNutrient(
        value = 29.49f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.vitamina_c
    ),
    vitaminD = HealthyRecipeNutrient(
        value = 24.88f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.vitamina_d
    ),
    vitaminB6 = HealthyRecipeNutrient(
        value = 32.40f,
        unit = NutrientUnit.MILLIGRAM,
        name = R.string.vitamina_b6
    ),
    totalPortionInGrams = 240
)

val mockHealthyRecipes = listOf(
    HealthyRecipe(
        name = "Salada variada",
        imageRes = R.drawable.img_assorted_salad,
        calories = HealthyRecipeNutrient(
            value = 221.15f,
            unit = NutrientUnit.GRAM,
            name = R.string.energia
        ),
        proteins = HealthyRecipeNutrient(
            value = 15.13f,
            unit = NutrientUnit.GRAM,
            name = R.string.proteinas
        ),
        carbohydrates = HealthyRecipeNutrient(
            value = 18.40f,
            unit = NutrientUnit.GRAM,
            name = R.string.carboidratos
        ),
        sugar = HealthyRecipeNutrient(
            value = 4.88f,
            unit = NutrientUnit.GRAM,
            name = R.string.acucar
        ),
        fat = HealthyRecipeNutrient(
            value = 5.18f,
            unit = NutrientUnit.GRAM,
            name = R.string.gorduras
        ),
        fiber = HealthyRecipeNutrient(
            value = 5.83f,
            unit = NutrientUnit.GRAM,
            name = R.string.fibra_alimentar
        ),
        saturatedFat = HealthyRecipeNutrient(
            value = 0.86f,
            unit = NutrientUnit.GRAM,
            name = R.string.gorduras_saturadas
        ),
        transFat = HealthyRecipeNutrient(
            value = 0.00f,
            unit = NutrientUnit.GRAM,
            name = R.string.gorduras_trans
        ),
        cholesterol = HealthyRecipeNutrient(
            value = 0.00f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.colesterol
        ),
        sodium = HealthyRecipeNutrient(
            value = 243.03f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.sodio
        ),
        potassium = HealthyRecipeNutrient(
            value = 291.85f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.potassio
        ),
        calcium = HealthyRecipeNutrient(
            value = 49.02f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.calcio
        ),
        iron = HealthyRecipeNutrient(
            value = 2.91f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.ferro
        ),
        magnesium = HealthyRecipeNutrient(
            value = 48.23f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.magnesio
        ),
        vitaminC = HealthyRecipeNutrient(
            value = 29.49f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.vitamina_c
        ),
        vitaminD = HealthyRecipeNutrient(
            value = 24.88f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.vitamina_d
        ),
        vitaminB6 = HealthyRecipeNutrient(
            value = 32.40f,
            unit = NutrientUnit.MILLIGRAM,
            name = R.string.vitamina_b6
        ),
        totalPortionInGrams = 240
    ),
    HealthyRecipe(
        name = "Omelete de queijo e espinafre",
        imageRes = R.drawable.img_cheese_and_spinach_omelette,
        calories = HealthyRecipeNutrient(
            value = 280.10f,
            unit = NutrientUnit.KCAL,
            name = R.string.energia
        ),
        proteins = HealthyRecipeNutrient(
            value = 20.50f,
            unit = NutrientUnit.GRAM,
            name = R.string.proteinas
        ),
        carbohydrates = HealthyRecipeNutrient(
            value = 10.30f,
            unit = NutrientUnit.GRAM,
            name = R.string.carboidratos
        ),
        sugar = HealthyRecipeNutrient(
            value = 4.88f,
            unit = NutrientUnit.GRAM,
            name = R.string.acucar
        ),
        fat = HealthyRecipeNutrient(
            value = 5.18f,
            unit = NutrientUnit.GRAM,
            name = R.string.gorduras
        ),
        fiber = referenceRecipe.fiber,
        saturatedFat = referenceRecipe.saturatedFat,
        transFat = referenceRecipe.transFat,
        cholesterol = referenceRecipe.cholesterol,
        sodium = referenceRecipe.sodium,
        potassium = referenceRecipe.potassium,
        calcium = referenceRecipe.calcium,
        iron = referenceRecipe.iron,
        magnesium = referenceRecipe.magnesium,
        vitaminC = referenceRecipe.vitaminC,
        vitaminD = referenceRecipe.vitaminD,
        vitaminB6 = referenceRecipe.vitaminB6,
        totalPortionInGrams = 240
    ),
    HealthyRecipe(
        name = "Panqueca de aveia e banana",
        imageRes = R.drawable.img_oatmeal_and_banana_pancakes,
        calories = HealthyRecipeNutrient(
            value = 250.60f,
            unit = NutrientUnit.KCAL,
            name = R.string.energia
        ),
        proteins = HealthyRecipeNutrient(
            value = 8.75f,
            unit = NutrientUnit.GRAM,
            name = R.string.proteinas
        ),
        carbohydrates = HealthyRecipeNutrient(
            value = 4.20f,
            unit = NutrientUnit.GRAM,
            name = R.string.carboidratos
        ),
        sugar = HealthyRecipeNutrient(
            value = 4.88f,
            unit = NutrientUnit.GRAM,
            name = R.string.acucar
        ),
        fat = HealthyRecipeNutrient(
            value = 5.18f,
            unit = NutrientUnit.GRAM,
            name = R.string.gorduras
        ),
        fiber = referenceRecipe.fiber,
        saturatedFat = referenceRecipe.saturatedFat,
        transFat = referenceRecipe.transFat,
        cholesterol = referenceRecipe.cholesterol,
        sodium = referenceRecipe.sodium,
        potassium = referenceRecipe.potassium,
        calcium = referenceRecipe.calcium,
        iron = referenceRecipe.iron,
        magnesium = referenceRecipe.magnesium,
        vitaminC = referenceRecipe.vitaminC,
        vitaminD = referenceRecipe.vitaminD,
        vitaminB6 = referenceRecipe.vitaminB6,
        totalPortionInGrams = 240
    ),
    HealthyRecipe(
        name = "Tofu grelhado",
        imageRes = R.drawable.img_grilled_tofu,
        calories = HealthyRecipeNutrient(
            value = 221.15f,
            unit = NutrientUnit.KCAL,
            name = R.string.energia
        ),
        proteins = HealthyRecipeNutrient(
            value = 15.13f,
            unit = NutrientUnit.GRAM,
            name = R.string.proteinas
        ),
        carbohydrates = HealthyRecipeNutrient(
            value = 18.40f,
            unit = NutrientUnit.GRAM,
            name = R.string.carboidratos
        ),
        sugar = HealthyRecipeNutrient(
            value = 4.88f,
            unit = NutrientUnit.GRAM,
            name = R.string.acucar
        ),
        fat = HealthyRecipeNutrient(
            value = 5.18f,
            unit = NutrientUnit.GRAM,
            name = R.string.gorduras
        ),
        fiber = referenceRecipe.fiber,
        saturatedFat = referenceRecipe.saturatedFat,
        transFat = referenceRecipe.transFat,
        cholesterol = referenceRecipe.cholesterol,
        sodium = referenceRecipe.sodium,
        potassium = referenceRecipe.potassium,
        calcium = referenceRecipe.calcium,
        iron = referenceRecipe.iron,
        magnesium = referenceRecipe.magnesium,
        vitaminC = referenceRecipe.vitaminC,
        vitaminD = referenceRecipe.vitaminD,
        vitaminB6 = referenceRecipe.vitaminB6,
        totalPortionInGrams = 240
    ),
    HealthyRecipe(
        name = "Iogurte natural com granola",
        imageRes = R.drawable.img_natural_yogurt_with_granola,
        calories = HealthyRecipeNutrient(
            value = 190.30f,
            unit = NutrientUnit.KCAL,
            name = R.string.energia
        ),
        proteins = HealthyRecipeNutrient(
            value = 12.10f,
            unit = NutrientUnit.GRAM,
            name = R.string.proteinas
        ),
        carbohydrates = HealthyRecipeNutrient(
            value = 30.15f,
            unit = NutrientUnit.GRAM,
            name = R.string.carboidratos
        ),
        sugar = HealthyRecipeNutrient(
            value = 4.88f,
            unit = NutrientUnit.GRAM,
            name = R.string.acucar
        ),
        fat = HealthyRecipeNutrient(
            value = 5.18f,
            unit = NutrientUnit.GRAM,
            name = R.string.gorduras
        ),
        fiber = referenceRecipe.fiber,
        saturatedFat = referenceRecipe.saturatedFat,
        transFat = referenceRecipe.transFat,
        cholesterol = referenceRecipe.cholesterol,
        sodium = referenceRecipe.sodium,
        potassium = referenceRecipe.potassium,
        calcium = referenceRecipe.calcium,
        iron = referenceRecipe.iron,
        magnesium = referenceRecipe.magnesium,
        vitaminC = referenceRecipe.vitaminC,
        vitaminD = referenceRecipe.vitaminD,
        vitaminB6 = referenceRecipe.vitaminB6,
        totalPortionInGrams = 240
    )
)