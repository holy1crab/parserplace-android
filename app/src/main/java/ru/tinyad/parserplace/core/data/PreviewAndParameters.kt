package ru.tinyad.parserplace.core.data

data class SubscriptionItemParameter(
    val key: String,
    val title: String,
    val multiple: Boolean,
//    val options
)

data class PreviewAndParameters(
    val url: String,
    val parameters: List<SubscriptionItemParameter>,
    val product: Product
)