package com.ns.fakex.data.usecase

import com.ns.fakex.utils.AppDispatchers
import com.ns.fakex.utils.Dispatcher
import com.ns.fakex.utils.datastore.DataStoreManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthTokenUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val dataStoreManager: DataStoreManager
) {
    operator fun invoke(authToken: String?): Flow<Unit> {
        return flow {
            val result = dataStoreManager.saveAuthToken(authToken)
            emit(result)
        }.flowOn(ioDispatcher)
    }

    operator fun invoke(): Flow<String?> {
        return flow {
            val result = dataStoreManager.getAuthToken.firstOrNull()
            emit(result)
        }.flowOn(ioDispatcher)
    }
}
