package ru.tinyad.parserplace.core.data

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tinyad.parserplace.core.model.data.ItemsCollection
import ru.tinyad.parserplace.core.model.data.ProductSubscription
import ru.tinyad.parserplace.core.network.ProductApi
import ru.tinyad.parserplace.core.network.model.asExternalModel

class DefaultProductSubscriptionRepository @Inject constructor(
    val productApi: ProductApi
) : ProductSubscriptionRepository {

    override suspend fun getSubscriptions(): Flow<ItemsCollection<ProductSubscription>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPreviewAndParameters(urls: Array<String>): Flow<List<PreviewAndParameters>> =
        productApi.getPreviewAndParameters(urls)
            .map { res -> res.response.map { it.asExternalModel() } }
}