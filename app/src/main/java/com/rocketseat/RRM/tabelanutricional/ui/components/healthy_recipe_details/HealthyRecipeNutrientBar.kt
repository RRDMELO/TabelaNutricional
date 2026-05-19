package com.rocketseat.RRM.tabelanutricional.ui.components.healthy_recipe_details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.rocketseat.RRM.tabelanutricional.R
import com.rocketseat.RRM.tabelanutricional.ui.theme.Primary
import com.rocketseat.RRM.tabelanutricional.ui.theme.SurfaceElement
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme.sizing
import com.rocketseat.RRM.tabelanutricional.ui.theme.Typography
import kotlin.math.roundToInt

private const val DEFAULT_MAX_NUTRIENT_VALUE = 100f
private const val DEFAULT_TOTAL_NUTRIENT_BARS = 6

@Composable
fun HealthyRecipeNutrientBar(
    modifier: Modifier = Modifier,
    name: String,
    value: Float,
    maxValue: Float = DEFAULT_MAX_NUTRIENT_VALUE
) {

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = name,
                style = Typography.headlineMedium
            )

            Text(text = stringResource(R.string.valor_g, value))
        }
        Spacer(
            modifier = Modifier.height(sizing.sm)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(sizing.sm)
        ) {
            repeat(times = DEFAULT_TOTAL_NUTRIENT_BARS) { barPosition ->
                val shouldFillNutrientBar = shouldFillNutrientBar(
                    barPosition = barPosition,
                    value = value,
                    maxValue = maxValue
                )
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(sizing.sm)
                ) {
                    drawRoundRect(
                        color = if(shouldFillNutrientBar) Primary else SurfaceElement,
                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                    )
                }
            }
        }
    }
}

private fun shouldFillNutrientBar(barPosition: Int, value: Float, maxValue: Float): Boolean =
    barPosition <= (value * DEFAULT_TOTAL_NUTRIENT_BARS / maxValue).roundToInt() - 1

/*
* Exemplo de cálculo:
*
* value = 15.13f
* maxValue = 100
* barPosition = 0
*
* 0 <= (15.13f * 6 / 100).roundToInt() - 1 = 0.9 -1 = 1 - 1 = 0
*
* */

class NutrientValueProvider: PreviewParameterProvider<Float> {
    override val values: Sequence<Float>
        get() = sequenceOf(0f, 15.13f, 25f, 50f, 85f, 100f, 200f)
}

@Preview(showBackground = true)
@Composable
private fun HealthyRecipeNutrientBarPreview(
    @PreviewParameter(NutrientValueProvider::class) value: Float
) {
    TabelaNutricionalTheme {
        HealthyRecipeNutrientBar(
            modifier = Modifier.padding(16.dp),
            name = "Proteínas",
            value = value,
        )
    }
}
