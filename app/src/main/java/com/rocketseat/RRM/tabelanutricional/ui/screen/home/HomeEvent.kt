package com.rocketseat.RRM.tabelanutricional.ui.screen.home

sealed interface HomeEvent {
    data object OnInit: HomeEvent
}
