package com.ns.fakex.data.usecase

import com.ns.fakex.data.database.AppDatabase
import com.ns.fakex.data.model.TweetEntity
import com.ns.fakex.utils.AppDispatchers
import com.ns.fakex.utils.Constants.DB_MAX_ITEM_COUNT
import com.ns.fakex.utils.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DatabaseOperationsUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val database: AppDatabase
) {
    operator fun invoke(list: List<TweetEntity>): Flow<Unit> {
        return flow {
            val currentItemCount = database.tweetsDao().getTweetCount()
            if (currentItemCount + list.size > DB_MAX_ITEM_COUNT) {
                val overflowCount = (currentItemCount + list.size) - DB_MAX_ITEM_COUNT
                database.tweetsDao().deleteOldestTweets(overflowCount)
            }
            val result = database.tweetsDao().insertTweets(list)
            emit(result)
        }.flowOn(ioDispatcher)
    }

    operator fun invoke(): Flow<List<TweetEntity>> {
        return flow {
            val result = database.tweetsDao().getAllTweets()
            emit(result)
        }.flowOn(ioDispatcher)
    }
}