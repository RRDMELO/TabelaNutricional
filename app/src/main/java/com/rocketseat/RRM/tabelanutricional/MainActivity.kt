package com.rocketseat.RRM.tabelanutricional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rocketseat.RRM.tabelanutricional.ui.screen.nav_host.MainNavHost
import com.rocketseat.RRM.tabelanutricional.ui.theme.TabelaNutricionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TabelaNutricionalTheme {
                    MainNavHost()
            }
        }
    }
}
