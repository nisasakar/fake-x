package com.ns.fakex.data.usecase

import com.ns.fakex.data.model.UserResponse
import com.ns.fakex.data.repository.ApiRepository
import com.ns.fakex.utils.AppDispatchers
import com.ns.fakex.utils.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val apiRepository: ApiRepository
) {
    operator fun invoke(): Flow<UserResponse> {
        return flow {
            val result = apiRepository.getCurrentUser()
            (result.getOrNull() ?: result.getOrThrow()).also {
                emit(it)
            }
        }.flowOn(ioDispatcher)
    }
}