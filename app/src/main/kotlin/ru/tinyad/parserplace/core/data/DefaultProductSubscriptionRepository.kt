package ru.tinyad.parserplace.core.data

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tinyad.parserplace.core.model.data.ItemsCollection
import ru.tinyad.parserplace.core.model.data.ProductSubscription
import ru.tinyad.parserplace.core.network.ProductApi
import ru.tinyad.parserplace.core.network.model.NetworkCreateOrEditSubscription
import ru.tinyad.parserplace.core.network.model.NetworkSubscriptionParameterAnswer
import ru.tinyad.parserplace.core.network.model.NetworkUserSubscriptionItem
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

    override suspend fun saveSubscription(obj: CreateOrEditSubscription) {

        productApi.saveSubscription(obj.asNetwork())
    }
}

private fun SubscriptionParameterAnswer.IntValue.asNetwork() =
    NetworkSubscriptionParameterAnswer.IntValue(
        key = key,
        value = value,
    )

private fun SubscriptionParameterAnswer.StringValue.asNetwork() =
    NetworkSubscriptionParameterAnswer.StringValue(
        key = key,
        value = value,
    )

private fun SubscriptionParameterAnswer.asNetwork(): NetworkSubscriptionParameterAnswer {
    return this.asNetwork()
//    return when (this) {
//        is SubscriptionParameterAnswer.IntValue -> NetworkSubscriptionParameterAnswer.IntValue(
//            key,
//            value
//        )
//
//        is SubscriptionParameterAnswer.StringValue -> NetworkSubscriptionParameterAnswer.StringValue(
//            key,
//            value
//        )
//    }
}

private fun UserSubscriptionItem.asNetwork() = NetworkUserSubscriptionItem(
    url = url,
    parameters = parameters.map { it.asNetwork() }
)

private fun CreateOrEditSubscription.asNetwork() = NetworkCreateOrEditSubscription(
    subscriptionId = subscriptionId,
    items = items.map { it.asNetwork() }
)
