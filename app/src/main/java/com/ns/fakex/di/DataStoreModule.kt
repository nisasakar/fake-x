package com.ns.fakex.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ns.fakex.utils.AppDispatchers
import com.ns.fakex.utils.Dispatcher
import com.ns.fakex.utils.datastore.DataStoreManager
import com.ns.fakex.utils.datastore.DataStoreObject
import com.ns.fakex.utils.datastore.DataStoreSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val DATA_STORE_KEY = "data_store_key"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideDataStorePreferences(
        @ApplicationContext appContext: Context,
        @Dispatcher(AppDispatchers.IO)
        ioDispatcher: CoroutineDispatcher,
    ): DataStore<DataStoreObject> {
        return MultiProcessDataStoreFactory.create(
            serializer = DataStoreSerializer(ioDispatcher = ioDispatcher),
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(DATA_STORE_KEY) }
        )
    }

    @Singleton
    @Provides
    fun provideDataStoreManager(dataStore: DataStore<DataStoreObject>): DataStoreManager =
        DataStoreManager(dataStore)
}