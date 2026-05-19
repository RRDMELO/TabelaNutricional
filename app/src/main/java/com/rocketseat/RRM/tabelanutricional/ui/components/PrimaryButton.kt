package com.rocketseat.RRM.tabelanutricional.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.rocketseat.RRM.tabelanutricional.ui.theme.Primary
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme
import com.rocketseat.RRM.tabelanutricional.ui.theme.Typography

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    ElevatedButton(
        modifier = modifier,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Primary,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = TabelaNutricionalTheme.sizing.xs
        ),
        shape = RoundedCornerShape(size = TabelaNutricionalTheme.sizing.md),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@PreviewLightDark
@Composable
private fun PrimaryButtonPreview() {
    TabelaNutricionalTheme {
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Mais detalhes"
        ) { }
    }
}
