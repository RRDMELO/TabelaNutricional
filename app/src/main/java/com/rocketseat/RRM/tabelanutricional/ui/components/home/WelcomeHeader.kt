package com.rocketseat.RRM.tabelanutricional.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.PhotoCamera
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
    userProfileImageUri: String? = null,
    hasNewNotification: Boolean = true,
    onNotificationBellClick: () -> Unit = { },
    onSearchClick: () -> Unit = { },
    onProfileImageClick: () -> Unit = { },
    onLogoutClick: () -> Unit = { }
) {
    var hasNewNotification by remember { mutableStateOf(hasNewNotification) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem de perfil clicável
        Box(
            modifier = Modifier
                .size(TabelaNutricionalTheme.sizing.x2l)
                .border(2.dp, Secondary, CircleShape)
                .background(Color.White, shape = CircleShape)
                .clickable(indication = null, interactionSource = null) {
                    onProfileImageClick()
                },
            contentAlignment = Alignment.BottomEnd
        ) {
            if (userProfileImageUri != null && userProfileImageUri.isNotEmpty()) {
                // Carrega imagem do usuário
                AsyncImage(
                    model = userProfileImageUri,
                    contentDescription = stringResource(R.string.imagem_perfil_usuario),
                    modifier = Modifier
                        .size(TabelaNutricionalTheme.sizing.x2l)
                        .background(Color.White, shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Usa imagem padrão enquanto não há upload
                Image(
                    modifier = Modifier.size(TabelaNutricionalTheme.sizing.x2l),
                    painter = painterResource(R.drawable.img_male_profile),
                    contentDescription = stringResource(R.string.imagem_perfil_usuario),
                    contentScale = ContentScale.Crop
                )
            }
            
            // Ícone de câmera para mudar foto
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Secondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Mudar foto",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        
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
                                size.minDimension / NOTIFICATION_BADGE_CIRCLE_RADIUS_DIVIDER
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
        IconButton(
            onClick = onSearchClick
        ) {
            Icon(
                modifier = Modifier.size(TabelaNutricionalTheme.sizing.lg),
                imageVector = Icons.Filled.Search,
                contentDescription = "Buscar receitas"
            )
        }
        IconButton(
            onClick = onLogoutClick
        ) {
            Icon(
                modifier = Modifier.size(TabelaNutricionalTheme.sizing.lg),
                imageVector = Icons.Filled.Logout,
                contentDescription = "Logout"
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
