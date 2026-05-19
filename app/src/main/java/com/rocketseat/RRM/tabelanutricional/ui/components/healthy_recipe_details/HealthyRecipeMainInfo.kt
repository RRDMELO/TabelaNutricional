package com.rocketseat.RRM.tabelanutricional.ui.components.healthy_recipe_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.rocketseat.RRM.tabelanutricional.R
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme.sizing
import com.rocketseat.RRM.tabelanutricional.ui.theme.Typography

@Composable
fun HealthyRecipeMainInfo(
    modifier: Modifier = Modifier,
    recipeName: String,
    calories: Float,
    totalPortionInGrams: Int
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = recipeName,
            textAlign = TextAlign.Center,
            style = Typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(sizing.lg))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(sizing.sm),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.energia),
                    style = Typography.headlineSmall
                )
                Text(
                    text = stringResource(R.string.valor_kcal, calories),
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(sizing.sm),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.porcao_total),
                    style = Typography.headlineSmall
                )
                Text(
                    text = stringResource(R.string.valor_g, totalPortionInGrams),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HealthyRecipeMainInfoPreview() {
    TabelaNutricionalTheme {
        HealthyRecipeMainInfo(
            modifier = Modifier.padding(vertical = sizing.md),
            recipeName = "Salada variada",
            calories = 221.15f,
            totalPortionInGrams = 200
        )
    }
}
