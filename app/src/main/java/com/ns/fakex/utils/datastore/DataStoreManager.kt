package com.ns.fakex.utils.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<DataStoreObject>,
) {
    suspend fun saveAuthToken(authToken: String?) {
        dataStore.updateData { mutablePreferences ->
            mutablePreferences.copy(
                authToken = authToken
            )
        }
    }

    val getAuthToken: Flow<String?> = dataStore.data.map { preferences -> preferences.authToken }
}