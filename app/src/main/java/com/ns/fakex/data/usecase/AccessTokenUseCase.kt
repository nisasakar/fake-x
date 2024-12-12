package com.ns.fakex.data.usecase

import com.ns.fakex.data.repository.ApiRepository
import com.ns.fakex.utils.AppDispatchers
import com.ns.fakex.utils.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccessTokenUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val apiRepository: ApiRepository
) {
    /*
    operator fun invoke(
        authHeader: String,
        code: String,
        redirectUri: String,
        codeVerifier: String
    ): Flow<TokenResponse> {
        return flow {
            val result =
                apiRepository.getAccessToken(
                    authHeader = authHeader,
                    code = code,
                    redirectUri = redirectUri,
                    codeVerifier = codeVerifier
                )
            (result.getOrNull() ?: result.getOrThrow()).also {
                emit(it)
            }
        }.flowOn(ioDispatcher)
    }

     */
}
