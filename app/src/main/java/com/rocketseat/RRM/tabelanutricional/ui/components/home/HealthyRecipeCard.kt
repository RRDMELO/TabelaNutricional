package com.rocketseat.RRM.tabelanutricional.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.rocketseat.RRM.tabelanutricional.R
import com.rocketseat.RRM.tabelanutricional.data.model.HealthyRecipe
import com.rocketseat.RRM.tabelanutricional.data.model.mock.mockHealthyRecipes
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme.sizing
import com.rocketseat.RRM.tabelanutricional.ui.theme.Typography

@Composable
fun HealthyRecipeCard(
    modifier: Modifier = Modifier,
    healthyRecipe: HealthyRecipe,
    onClick: (selectedHealthyRecipeId: String) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable {
            onClick(healthyRecipe.id.toString())
        }
    ) {
        Image(
            modifier = Modifier
                .size(sizing.x3l)
                .clip(shape = RoundedCornerShape(sizing.md)),
            painter = painterResource(id = healthyRecipe.imageRes),
            contentDescription = stringResource(id = R.string.imagem_item_tabela_nutricional)
        )
        Spacer(modifier = Modifier.width(sizing.md))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(sizing.sm)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = healthyRecipe.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = Typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.valor_kcal, healthyRecipe.calories.value),
                    style = Typography.bodyLarge
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.g_proteinas_g_carboidratos_da_receita,
                    healthyRecipe.proteins.value,
                    healthyRecipe.carbohydrates.value
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = Typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSecondary)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
private fun HealthyRecipeCardPreview() {
    TabelaNutricionalTheme {
        Column(
            modifier = Modifier.padding(sizing.md),
            verticalArrangement = Arrangement.spacedBy(sizing.md)
        ) {
            repeat(5) {
                HealthyRecipeCard(
                    healthyRecipe = mockHealthyRecipes.first(),
                    onClick = {}
                )
            }
        }
    }
}
