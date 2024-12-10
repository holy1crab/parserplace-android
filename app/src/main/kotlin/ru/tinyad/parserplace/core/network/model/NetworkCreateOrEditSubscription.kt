package ru.tinyad.parserplace.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface NetworkSubscriptionParameterAnswer {
    @Serializable
    data class IntValue(val key: String, val value: Int) : NetworkSubscriptionParameterAnswer

    @Serializable
    data class StringValue(val key: String, val value: String) : NetworkSubscriptionParameterAnswer
}

@Serializable
data class NetworkUserSubscriptionItem(
    val url: String,
    val parameters: List<NetworkSubscriptionParameterAnswer>
)

@Serializable
data class NetworkCreateOrEditSubscription(
    @SerialName("subscription_id")
    val subscriptionId: Int? = null,
    val items: List<NetworkUserSubscriptionItem>,
)
