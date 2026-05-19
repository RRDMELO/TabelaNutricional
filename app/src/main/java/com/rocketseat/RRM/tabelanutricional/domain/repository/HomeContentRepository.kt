package com.rocketseat.RRM.tabelanutricional.domain.repository

import com.rocketseat.RRM.tabelanutricional.domain.model.HomeContent

interface HomeContentRepository {
    suspend fun getHomeContent(): HomeContent
}
