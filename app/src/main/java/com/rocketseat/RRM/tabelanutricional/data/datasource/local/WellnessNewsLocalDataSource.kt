package com.rocketseat.RRM.tabelanutricional.data.datasource.local

import com.rocketseat.RRM.tabelanutricional.data.model.WellnessNews

interface WellnessNewsLocalDataSource {
    suspend fun getAllWellnessNews(): List<WellnessNews>
}
