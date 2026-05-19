package com.rocketseat.RRM.tabelanutricional.data.datasource.local

import com.rocketseat.RRM.tabelanutricional.data.model.mock.mockWellnessNews

class MockWellnessNewsLocalDataSourceImpl: WellnessNewsLocalDataSource {
    override suspend fun  getAllWellnessNews() = mockWellnessNews
}
