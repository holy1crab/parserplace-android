package ru.tinyad.parserplace.core.network

import kotlinx.coroutines.flow.Flow
import ru.tinyad.parserplace.core.network.model.NetworkCreateOrEditSubscription
import ru.tinyad.parserplace.core.network.model.NetworkResponse
import ru.tinyad.parserplace.core.network.model.NetworkPreviewAndParameters

interface ProductApi {

    suspend fun getPreviewAndParameters(urls: Array<String>): Flow<NetworkResponse<List<NetworkPreviewAndParameters>>>

    suspend fun saveSubscription(body: NetworkCreateOrEditSubscription)
}
