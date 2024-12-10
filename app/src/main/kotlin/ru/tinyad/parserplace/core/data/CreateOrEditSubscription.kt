package ru.tinyad.parserplace.core.data

import kotlinx.serialization.Serializable

sealed interface SubscriptionParameterAnswer {
    @Serializable
    data class IntValue(val key: String, val value: Int) : SubscriptionParameterAnswer

    @Serializable
    data class StringValue(val key: String, val value: String) : SubscriptionParameterAnswer
}

data class UserSubscriptionItem(
    val url: String,
    val parameters: List<SubscriptionParameterAnswer>
)

data class CreateOrEditSubscription(
    val subscriptionId: Int? = null,
    val items: List<UserSubscriptionItem>,
)
