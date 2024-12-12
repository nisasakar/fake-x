package com.ns.fakex.data.usecase

import com.ns.fakex.data.model.TimelineResponse
import com.ns.fakex.data.repository.ApiRepository
import com.ns.fakex.utils.AppDispatchers
import com.ns.fakex.utils.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTimelineUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val apiRepository: ApiRepository
) {
    operator fun invoke(userId: String): Flow<TimelineResponse> {
        return flow {
            val result = apiRepository.getUserTimeline(userId = userId)
            (result.getOrNull() ?: result.getOrThrow()).also {
                emit(it)
            }
        }.flowOn(ioDispatcher)
    }
}