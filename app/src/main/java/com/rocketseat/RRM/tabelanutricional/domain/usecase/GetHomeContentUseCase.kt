package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.domain.model.HomeContent
import com.rocketseat.RRM.tabelanutricional.domain.repository.HomeContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHomeContentUseCase(
    private val homeContentRepository: HomeContentRepository
) {
    operator fun invoke(): Flow<HomeContent> = flow {
        emit(homeContentRepository.getHomeContent())
    }
}
