package ru.tinyad.parserplace.core.data

import kotlinx.coroutines.flow.Flow
import ru.tinyad.parserplace.core.model.data.ItemsCollection
import ru.tinyad.parserplace.core.model.data.ProductSubscription

interface ProductSubscriptionRepository {

    suspend fun getSubscriptions(): Flow<ItemsCollection<ProductSubscription>>

    suspend fun getPreviewAndParameters(urls: Array<String>): Flow<List<PreviewAndParameters>>

    suspend fun saveSubscription(obj: CreateOrEditSubscription)
}
