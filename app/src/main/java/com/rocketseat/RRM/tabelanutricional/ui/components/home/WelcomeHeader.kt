package com.rocketseat.RRM.tabelanutricional.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rocketseat.RRM.tabelanutricional.R
import com.rocketseat.RRM.tabelanutricional.ui.theme.Secondary
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme
import com.rocketseat.RRM.tabelanutricional.ui.theme.Typography

private const val NOTIFICATION_BADGE_CIRCLE_RADIUS_RATIO = 6
private const val NOTIFICATION_BADGE_CIRCLE_RADIUS_DIVIDER = 1.5f
private const val NOTIFICATION_BADGE_OFFSET_X_RATIO = 0.7f
private const val NOTIFICATION_BADGE_OFFSET_Y_RATIO = 0.2f

@Composable
fun WelcomeHeader(
    modifier: Modifier = Modifier,
    userName: String,
    hasNewNotification: Boolean = true,
    onNotificationBellClick: () -> Unit = { }
) {
    var hasNewNotification by remember { mutableStateOf(hasNewNotification) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(TabelaNutricionalTheme.sizing.x2l),
            painter = painterResource(R.drawable.img_male_profile),
            contentDescription = stringResource(R.string.imagem_perfil_usuario)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(TabelaNutricionalTheme.sizing.sm)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.ola_usuario, userName),
                style = Typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(TabelaNutricionalTheme.sizing.x2))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.seja_bem_vindo_de_volta),
                style = Typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSecondary)
            )
        }
        IconButton(
            onClick = {
                hasNewNotification = false
                onNotificationBellClick()
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(TabelaNutricionalTheme.sizing.lg)
                    .drawWithContent {
                        drawContent()

                        if (hasNewNotification) {
                            val circleRadius =
                                size.minDimension / NOTIFICATION_BADGE_CIRCLE_RADIUS_RATIO
                            drawCircle(
                                color = Color.White,
                                radius = circleRadius,
                                center = Offset(
                                    x = size.width * NOTIFICATION_BADGE_OFFSET_X_RATIO,
                                    y = size.height * NOTIFICATION_BADGE_OFFSET_Y_RATIO
                                )
                            )
                            drawCircle(
                                color = Secondary,
                                radius = circleRadius / NOTIFICATION_BADGE_CIRCLE_RADIUS_DIVIDER,
                                center = Offset(
                                    x = size.width * NOTIFICATION_BADGE_OFFSET_X_RATIO,
                                    y = size.height * NOTIFICATION_BADGE_OFFSET_Y_RATIO
                                )
                            )
                        }
                    },
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = stringResource(id = R.string.botao_notificacoes)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomeHeaderWithNewNotificationPreview() {
    TabelaNutricionalTheme {
        WelcomeHeader(
            modifier = Modifier.padding(TabelaNutricionalTheme.sizing.md), userName = "Marcos"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomeHeaderWithoutNewNotificationPreview() {
    TabelaNutricionalTheme {
        WelcomeHeader(
            modifier = Modifier.padding(TabelaNutricionalTheme.sizing.md), userName = "Karina", hasNewNotification = false
        )
    }
}
