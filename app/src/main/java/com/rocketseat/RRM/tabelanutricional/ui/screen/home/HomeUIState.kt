package com.rocketseat.RRM.tabelanutricional.ui.screen.home

import com.rocketseat.RRM.tabelanutricional.domain.model.HomeContent

data class HomeUIState(
    val isLoading: Boolean = false,
    val homeContent: HomeContent? = null,
    val userName: String? = null,
)
