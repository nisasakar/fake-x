package com.ns.fakex.utils.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class DataStoreObject(
    val authToken: String? = null,
)

@Singleton
class DataStoreSerializer @Inject constructor(private val ioDispatcher: CoroutineDispatcher) :
    Serializer<DataStoreObject> {
    override val defaultValue: DataStoreObject = DataStoreObject()

    override suspend fun readFrom(input: InputStream): DataStoreObject = withContext(ioDispatcher) {
        try {
            Json.decodeFromString(DataStoreObject.serializer(), input.readBytes().decodeToString())
        } catch (serialization: SerializationException) {
            throw CorruptionException(
                "Failed to read Settings data: ${serialization.localizedMessage}",
                serialization
            )
        }
    }

    override suspend fun writeTo(t: DataStoreObject, output: OutputStream) =
        withContext(ioDispatcher) {
            output.write(Json.encodeToString(DataStoreObject.serializer(), t).encodeToByteArray())
        }
}

